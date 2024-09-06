package com.prac.githubrepo.main.detail

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.prac.githubrepo.databinding.ActivityDetailBinding
import com.prac.githubrepo.main.MainActivity.Companion.REPO_NAME
import com.prac.githubrepo.main.MainActivity.Companion.USER_NAME
import dagger.hilt.android.AndroidEntryPoint

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
    }

    private fun handleOnBackPressed() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }
}