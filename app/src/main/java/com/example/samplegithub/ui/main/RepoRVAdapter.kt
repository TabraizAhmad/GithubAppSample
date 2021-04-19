package com.example.samplegithub.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samplegithub.R
import com.example.samplegithub.databinding.RepoItemBinding
import com.example.samplegithub.network.model.GithubRepoItem

class RepoRVAdapter(
    private  val itemClickListener: OnItemClicked?)
    : PagingDataAdapter<GithubRepoItem,RepoRVAdapter.RepoViewHolder>(REPO_ITEM_COMPARATOR) {

    interface OnItemClicked{
        fun onItemClicked(view: View, repoItem: GithubRepoItem)

    }

    private lateinit var binding: RepoItemBinding

    class RepoViewHolder(private val viewBinding:RepoItemBinding): RecyclerView.ViewHolder(viewBinding.root) {

        fun bindClickListener(repoItem: GithubRepoItem, itemClickListener: OnItemClicked?) {
            viewBinding.root.setOnClickListener {
                itemClickListener?.onItemClicked(it, repoItem)
            }
        }


        val repoImage = viewBinding.repoIconIV
        val repoHeader = viewBinding.repoHeaderTV
        val repoDescription = viewBinding.repoDescriptionTV
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        this.binding = RepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val githubRepo = getItem(position)
        githubRepo?.apply {
            holder.repoHeader.text = full_name
            holder.repoDescription.text = description

            Glide.with(holder.itemView)
                .load(owner.avatar_url)
                .placeholder(R.drawable.default_repo_img)
                .into(holder.repoImage)

            holder.bindClickListener(this,itemClickListener)
        }

    }

    companion object {
        private val REPO_ITEM_COMPARATOR = object : DiffUtil.ItemCallback<GithubRepoItem>() {
            override fun areItemsTheSame(oldItem: GithubRepoItem, newItem: GithubRepoItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GithubRepoItem, newItem: GithubRepoItem) =
                oldItem == newItem
        }
    }

}