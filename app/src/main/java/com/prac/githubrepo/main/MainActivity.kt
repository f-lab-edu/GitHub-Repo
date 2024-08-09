package com.prac.githubrepo.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import com.prac.githubrepo.R
import com.prac.githubrepo.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.prac.githubrepo.main.MainViewModel.UiState
import com.prac.githubrepo.main.MainViewModel.Event

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainAdapter.JobManager {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val mainAdapter: MainAdapter by lazy { MainAdapter(this) }
    private val retryFooterAdapter: RetryFooterAdapter by lazy { RetryFooterAdapter { mainAdapter.retry() } }
    private val conCatAdapter: ConcatAdapter by lazy { ConcatAdapter(mainAdapter, retryFooterAdapter) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMain.adapter = conCatAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainAdapter.loadStateFlow.collect {
                    it.handleLoadStates()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    it.handleUiState()
                }
            }
        }
    }

    private fun CombinedLoadStates.handleLoadStates() {
        if (source.refresh is LoadState.Error || source.refresh is LoadState.Loading) {
            retryFooterAdapter.loadState = source.refresh
            return
        }

        retryFooterAdapter.loadState = source.append
    }

    private suspend fun UiState.handleUiState() {
        when (this) {
            is UiState.Idle -> { }
            is UiState.Loading -> {
                // TODO Not yet implemented
//                binding.includeProgressBar.root.isVisible = true
            }
            is UiState.ShowPagingData -> {
                // TODO Not yet implemented
//                binding.includeProgressBar.root.isVisible = false

                mainAdapter.submitData(this.repositories)
            }
        }
    }

    private fun Event.handleEvent() {
        when (this) {
            is Event.StartIsStarredUpdate -> {
                if (isStarred) {
                    mainAdapter.snapshot().items[position].isStarred = true
                    mainAdapter.notifyItemChanged(position)
                } else {
                    mainAdapter.snapshot().items[position].isStarred = false
                }
            }
        }
    }

    override fun startJob(position: Int, repoName: String) {
        viewModel.putAndStartJob(position, repoName)
    }

    override fun cancelJob(position: Int) {
        viewModel.cancelJob(position)
    }
}