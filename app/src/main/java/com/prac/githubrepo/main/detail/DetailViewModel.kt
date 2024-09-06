package com.prac.githubrepo.main.detail

import androidx.lifecycle.ViewModel
import com.prac.data.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repoRepository: RepoRepository
) : ViewModel() {
}