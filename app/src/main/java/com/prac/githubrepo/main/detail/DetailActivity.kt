package com.prac.githubrepo.main.detail

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
                setOnStarClickListener(this.repository)
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
        binding.ivStar.setImageResource(
            if (repoDetailEntity.isStarred == true) R.drawable.img_star
            else R.drawable.img_unstar
        )
        binding.tvStarCount.text = getString(R.string.star_count, repoDetailEntity.stargazersCount)
        binding.tvForkCount.text = getString(R.string.fork_count, repoDetailEntity.forksCount)
    }

    private fun setOnStarClickListener(repoDetailEntity: RepoDetailEntity) {
        binding.ivStar.setOnClickListener {
            if (repoDetailEntity.isStarred == true) viewModel.unStarRepository(repoDetailEntity)
            else viewModel.starRepository(repoDetailEntity)
        }
    }
}