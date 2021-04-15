package com.example.samplegithub.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.samplegithub.R
import com.example.samplegithub.databinding.RepoItemBinding
import com.example.samplegithub.network.model.GithubRepoItem

class RepoRVAdapter(
    var list:List<GithubRepoItem>,
    private val context: Context?,
    private  val itemClickListener: OnItemClicked?)
    :RecyclerView.Adapter<RepoRVAdapter.RepoViewHolder>() {

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
        val githubRepo = list[position]

        holder.repoHeader.text = githubRepo.full_name
        holder.repoDescription.text = githubRepo.description
        context?.let {
            Glide.with(it)
                .load(githubRepo.owner.avatar_url)
                .placeholder(R.drawable.default_repo_img)
                .into(holder.repoImage)
        }
        holder.bindClickListener(githubRepo,itemClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setRepoList(items: List<GithubRepoItem>?) {
        items?.let {
            list = it
            notifyDataSetChanged()
        }
    }
}