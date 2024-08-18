package com.prac.githubrepo.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prac.data.entity.RepoEntity
import com.prac.githubrepo.R
import com.prac.githubrepo.databinding.ItemMainBinding
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlin.properties.Delegates

class MainAdapter @AssistedInject constructor(
    private val viewStateTrackerBuilder: ViewStateTracker.Builder,
    @Assisted private val uiStateUpdater: UiStateUpdater
) : PagingDataAdapter<RepoEntity, MainAdapter.ViewHolder>(diffUtil) {

    @AssistedFactory
    interface Factory {
        fun create(uiStateUpdater: UiStateUpdater): MainAdapter
    }

    class ViewHolder(
        private val binding: ItemMainBinding,
        private val viewStateTrackerBuilder: ViewStateTracker.Builder
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repoEntity: RepoEntity) {
            with(repoEntity) {
                setProfile()
                setName()
                setTitle()
                setStarImage()
                setStarCount()
                setUpdatedDate()
            }
        }

        private fun RepoEntity.setProfile() {
            Glide.with(binding.root)
                .load(this.owner.avatarUrl)
                .error(R.drawable.img_glide_error)
                .placeholder(R.drawable.img_glide_profile)
                .into(binding.ivProfile)
        }

        private fun RepoEntity.setName() {
            binding.tvName.text = this.owner.login
        }

        private fun RepoEntity.setTitle() {
            binding.tvName.text = this.owner.login
        }

        private fun RepoEntity.setStarImage() {
            binding.ivStar.setImageResource(
                if (this.isStarred == true) R.drawable.img_star
                else R.drawable.img_unstar
            )
        }

        private fun RepoEntity.setStarCount() {
            binding.tvStar.text = this.stargazersCount.toString()
        }

        private fun RepoEntity.setUpdatedDate() {
            binding.tvLastUpdatedDate.text = this.updatedAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            viewStateTrackerBuilder.setUiStateUpdater(uiStateUpdater)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<RepoEntity>() {
            override fun areItemsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean =
                oldItem == newItem
        }
    }
}