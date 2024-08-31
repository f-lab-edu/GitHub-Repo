package com.prac.githubrepo.main.di

import com.prac.data.entity.RepoEntity
import com.prac.data.repository.RepoRepository
import com.prac.githubrepo.main.StarStateMediator
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
        repoRepository: RepoRepository,
        starStateMediator: StarStateMediator
    ) : StarStateFetcher {
        return object : StarStateFetcher {
            override suspend fun updateStarState(repoEntity: RepoEntity) {
                repoRepository.isStarred(repoEntity.name)
                    .onSuccess {
                        starStateMediator.addStarState(repoEntity.id, it)
                    }.onFailure {
                        starStateMediator.addStarState(repoEntity.id, false)
                    }
            }

        }
    }
}