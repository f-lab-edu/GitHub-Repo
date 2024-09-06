package com.prac.githubrepo.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.prac.data.entity.RepoEntity
import com.prac.githubrepo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.prac.githubrepo.main.MainViewModel.UiState
import com.prac.githubrepo.main.detail.DetailActivity
import com.prac.githubrepo.main.request.StarStateRequestBuilder
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    @Inject lateinit var starStateRequestBuilderFactory: StarStateRequestBuilder.Factory
    private val mainAdapter: MainAdapter by lazy {
        MainAdapter(
            starStateRequestBuilderFactory.create(this.lifecycleScope),
            object : MainAdapter.OnRepositoryClickListener {
                override fun repository(repoEntity: RepoEntity) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)

                    intent.apply {
                        putExtra(USER_NAME, repoEntity.owner.login)
                        putExtra(REPO_NAME, repoEntity.name)
                    }

                    startActivity(intent)
                }

                override fun star(repoEntity: RepoEntity) {
                    viewModel.starRepository(repoEntity)
                }

                override fun unStar(repoEntity: RepoEntity) {
                    viewModel.unStarRepository(repoEntity)
                }
            }
        )
    }
    private val retryFooterAdapter: RetryFooterAdapter by lazy { RetryFooterAdapter { mainAdapter.retry() } }
    private val conCatAdapter: ConcatAdapter by lazy { ConcatAdapter(mainAdapter, retryFooterAdapter) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMain.apply {
            adapter = conCatAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainAdapter.loadStateFlow.collect {
                    it.handleLoadStates()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    it.handleUiState()
                }
            }
        }
    }

    private fun CombinedLoadStates.handleLoadStates() {
        if (source.refresh is LoadState.Error || source.refresh is LoadState.Loading) {
            viewModel.updateLoadState(source.refresh)
            return
        }

        viewModel.updateLoadState(source.append)
    }

    private suspend fun UiState.handleUiState() {
        when (this) {
            is UiState.Idle -> { }
            is UiState.ShowPagingData -> {
                this.loadState?.let { retryFooterAdapter.loadState = it }

                mainAdapter.submitData(this.repositories)
            }
        }
    }

    companion object {
        const val USER_NAME = "userName"
        const val REPO_NAME = "repoName"
    }
}