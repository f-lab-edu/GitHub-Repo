package com.prac.githubrepo.main.di

import com.prac.data.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.main.star.StarStateFetcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class StarModule {
    @Provides
    fun provideStarStateFetcher(
        repoRepository: RepoRepository
    ) : StarStateFetcher {
        return object : StarStateFetcher {
            override suspend fun fetchStarState(repoEntity: RepoEntity) {
                repoRepository.isStarred(repoEntity.name)
                    .onSuccess {
                        // TODO ("room")
                    }.onFailure {
                        // TODO ("room")
                    }
            }

        }
    }
}