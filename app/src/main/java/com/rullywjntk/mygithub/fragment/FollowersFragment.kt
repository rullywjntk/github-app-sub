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
import com.rullywjntk.mygithub.databinding.FragmentFollowersBinding
import com.rullywjntk.mygithub.model.FollowersModel
import com.rullywjntk.mygithub.view.detail.DetailActivity

class FollowersFragment : Fragment() {

    private var _binding : FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var followersModel: FollowersModel
    private lateinit var adapter: FollowAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USER).toString()

        _binding = FragmentFollowersBinding.bind(view)
        adapter = FollowAdapter()
        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }
        showLoading(true)
        followersModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersModel::class.java)
        followersModel.setFollowers(username)
        followersModel.getFollowers().observe(viewLifecycleOwner, {
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