package com.example.mygithubuser.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {
    private var position = 0
    private var username: String = ""

    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel: DetailViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val ARG_USERNAME = "0"
        const val ARG_POSITION = "Yus"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: "Yus"
        }


        detailViewModel.getUserFollowings(username)
        detailViewModel.getUserFollowers(username)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFragment.layoutManager = layoutManager

        detailViewModel.loading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        if (position == 1) {
            detailViewModel.userFollower.observe(viewLifecycleOwner) {
                val adapter = DetailUserAdapter()
                adapter.submitList(it)
                binding.rvFragment.adapter = adapter
            }
        } else {
            detailViewModel.userFollowing.observe(viewLifecycleOwner) {
                val adapter = DetailUserAdapter()
                adapter.submitList(it)
                binding.rvFragment.adapter = adapter
            }
        }
    }

    private fun showLoading(state: Boolean) { binding.progressBarFollow.visibility = if (state) View.VISIBLE else View.GONE }
}