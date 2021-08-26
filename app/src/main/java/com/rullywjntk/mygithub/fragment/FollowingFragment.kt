package com.rullywjntk.mygithub.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rullywjntk.mygithub.adapter.FollowAdapter
import com.rullywjntk.mygithub.databinding.FragmentFollowingBinding
import com.rullywjntk.mygithub.model.FollowingModel
import com.rullywjntk.mygithub.view.detail.DetailActivity

class FollowingFragment : Fragment() {

    private var _binding : FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var followingModel: FollowingModel
    private lateinit var adapter: FollowAdapter
    private lateinit var username: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USER).toString()

        _binding = FragmentFollowingBinding.bind(view)
        adapter = FollowAdapter()
//        adapter.notifyDataSetChanged()
        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }
        showLoading(true)
        followingModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingModel::class.java)
        followingModel.setFollowing(username)
        followingModel.getFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setData(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}