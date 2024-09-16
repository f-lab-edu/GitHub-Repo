package com.prac.githubrepo.main.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.prac.data.entity.RepoDetailEntity
import com.prac.githubrepo.R
import com.prac.githubrepo.databinding.ActivityDetailBinding
import com.prac.githubrepo.main.MainActivity.Companion.REPO_NAME
import com.prac.githubrepo.main.MainActivity.Companion.USER_NAME
import com.prac.githubrepo.main.detail.DetailViewModel.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleOnBackPressed()

        viewModel.getRepository(
            intent.getStringExtra(USER_NAME),
            intent.getStringExtra(REPO_NAME)
        )

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    it.handleUiState()
                }
            }
        }
    }

    private fun handleOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    private fun UiState.handleUiState() {
        when (this) {
            is UiState.Idle -> {}
            is UiState.Loading -> {
                binding.includeProgressBar.root.isVisible = true
            }
            is UiState.ShowRepository -> {
                binding.includeProgressBar.root.isVisible = false

                bindRepositoryDetail(this.repository)
            }
            is UiState.Error -> {
                binding.includeProgressBar.root.isVisible = false

                AlertDialog.Builder(this@DetailActivity)
                    .setMessage(this.errorMessage)
                    .setPositiveButton(R.string.check) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setOnDismissListener {
                        finish()
                    }
                    .show()
            }
        }
    }

    private fun bindRepositoryDetail(repoDetailEntity: RepoDetailEntity) {
        Glide.with(binding.root)
            .load(repoDetailEntity.owner.avatarUrl)
            .error(R.drawable.img_glide_error)
            .placeholder(R.drawable.img_glide_profile)
            .into(binding.ivProfile)

        binding.tvName.text = repoDetailEntity.owner.login
        binding.tvTitle.text = repoDetailEntity.name
        binding.tvStarCount.text = getString(R.string.star_count, repoDetailEntity.stargazersCount)
        binding.tvForkCount.text = getString(R.string.fork_count, repoDetailEntity.forksCount)
    }

    companion object {
        fun createIntent(context: Context) : Intent {
            return Intent(context, DetailActivity::class.java)
        }
    }
}