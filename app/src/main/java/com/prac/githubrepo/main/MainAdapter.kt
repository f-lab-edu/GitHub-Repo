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
import kotlin.properties.Delegates

class MainAdapter() : PagingDataAdapter<RepoEntity, MainAdapter.ViewHolder>(diffUtil) {
    private val starImageResID = R.drawable.img_star
    private val unStarImageResID = R.drawable.img_unstar

    inner class ViewHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
        private var mPosition by Delegates.notNull<Int>()

        fun bind(repoEntity: RepoEntity, position: Int) {
            mPosition = position

            with(repoEntity) {
                startJob(position)
                setProfile()
                setName()
                setTitle()
                setStarImage()
                setStarCount()
                setUpdatedDate()
            }
        }

        private fun RepoEntity.startJob(position: Int) {

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
                if (this.isStarred == true) starImageResID
                else unStarImageResID
            )
        }

        private fun RepoEntity.setStarCount() {
            binding.tvStar.text = this.stargazersCount.toString()
        }

        private fun RepoEntity.setUpdatedDate() {
            binding.tvLastUpdatedDate.text = this.updatedAt
        }

        fun cancelJob() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, position) }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)

        holder.cancelJob()
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