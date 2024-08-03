package com.prac.githubrepo.main

import android.view.LayoutInflater
import android.view.ViewGroup
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