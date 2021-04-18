package com.example.samplegithub.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.samplegithub.R
import com.example.samplegithub.databinding.RepoDetailFragmentBinding
import com.example.samplegithub.network.model.Resource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@WithFragmentBindings
@AndroidEntryPoint
class RepoDetailFragment : Fragment() {



    private val viewModel: RepoDetailViewModel by viewModels()

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
            fetchReleaseInfo(releases_url.replace("{/id}","/latest"))
        }


    }

    private fun fetchReleaseInfo(releasesUrl: String) {

        viewModel.getLatestRelease(releasesUrl).observe(viewLifecycleOwner,{ resource->
            when (resource) {
                is Resource.Success -> {
                    binding.latestReleaseTV.text = resource.data?.tag_name
                }
            }
        })
    }
}