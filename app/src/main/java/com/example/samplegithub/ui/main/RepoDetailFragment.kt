package com.example.samplegithub.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.samplegithub.R
import com.example.samplegithub.databinding.RepoDetailFragmentBinding

class RepoDetailFragment : Fragment() {



    private lateinit var viewModel: SearchRepoViewModel

    private val args: RepoDetailFragmentArgs by navArgs()

    private lateinit var binding: RepoDetailFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = RepoDetailFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repoInfo  = args.repositoryInfo
        repoInfo.apply {
            binding.forkCount.text = forks_count.toString()
            binding.issueCount.text = open_issues_count.toString()
            binding.starredCount.text = stargazers_count.toString()
            binding.repoHeaderTV.text = full_name

            context?.let {
                Glide.with(it)
                    .load(owner.avatar_url)
                    .placeholder(R.drawable.default_repo_img)
                    .into(binding.repoImage)
            }

        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(GithubRepositoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}