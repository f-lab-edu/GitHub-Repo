package com.prac.githubrepo.main.di

import com.prac.githubrepo.main.StarStateMediator
import com.prac.githubrepo.main.StarStateMediator.StarState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Module
@InstallIn(ActivityRetainedComponent::class)
class MediatorModule {
    @Provides
    @ActivityRetainedScoped
    fun provideStarStateMediator() : StarStateMediator {
        return object : StarStateMediator {
            private val _starStates = MutableStateFlow<List<StarState>>(emptyList())

            override val starStates: StateFlow<List<StarState>>
                get() = _starStates.asStateFlow()

            override fun addStarState(id: Int, isStarred: Boolean, stargazersCount: Int) {
                _starStates.update {
                    it + StarState(id, isStarred, stargazersCount)
                }
            }

            override fun updateStarState(id: Int, isStarred: Boolean, stargazersCount: Int) {
                _starStates.update {
                    it.map { starState ->
                        if (starState.id == id) starState.copy(isStarred = isStarred, stargazersCount = stargazersCount)
                        else starState
                    }
                }
            }
        }
    }
}