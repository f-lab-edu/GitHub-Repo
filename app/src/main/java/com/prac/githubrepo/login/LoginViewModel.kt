package com.prac.githubrepo.login

import androidx.lifecycle.ViewModel
import com.prac.data.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
): ViewModel() {

}