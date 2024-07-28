package com.prac.githubrepo.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prac.data.entity.RepoEntity
import com.prac.githubrepo.R
import com.prac.githubrepo.databinding.ItemMainBinding

class MainAdapter : ListAdapter<RepoEntity, MainAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repoEntity: RepoEntity) {
            Glide.with(binding.root)
                .load(repoEntity.owner.avatarUrl)
                .error(R.drawable.img_glide_error)
                .placeholder(R.drawable.img_glide_profile)
                .into(binding.ivProfile)

            binding.apply {
                tvName.text = repoEntity.owner.login

                tvTitle.text = repoEntity.name

                tvStar.text = repoEntity.stargazersCount.toString()

                tvLastUpdatedDate.text = repoEntity.updatedAt
            }
        }

        fun clear() {
            Glide.with(binding.root)
                .clear(binding.ivProfile)
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

        private fun RepoEntity.setStarCount() {
            binding.tvStar.text = this.stargazersCount.toString()
        }

        private fun RepoEntity.setUpdatedDate() {
            binding.tvLastUpdatedDate.text = this.updatedAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.clear()
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