package com.prac.githubrepo.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.prac.githubrepo.databinding.ItemRetryFooterBinding

class RetryFooterAdapter(
    private val retryFooterClickListener: RetryFooterClickListener
) : LoadStateAdapter<RetryFooterAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemRetryFooterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {

        }

        private fun LoadState.setErrorMessage() {
            if (this is LoadState.Error) {
                binding.tvErrorMessage.text = this.error.localizedMessage
            }

            binding.tvErrorMessage.isVisible = this is LoadState.Error
        }

        private fun LoadState.setProgressBar() {
            binding.progressBar.isVisible = this is LoadState.Loading
        }

        private fun LoadState.setRetryButton() {
            binding.btnRetry.isVisible = this is LoadState.Error

            binding.btnRetry.setOnClickListener {
                retryFooterClickListener.clickRetry()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(ItemRetryFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    fun interface RetryFooterClickListener {
        fun clickRetry()
    }
}