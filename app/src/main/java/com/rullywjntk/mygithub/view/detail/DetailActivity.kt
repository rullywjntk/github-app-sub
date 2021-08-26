package com.rullywjntk.mygithub.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.rullywjntk.mygithub.R
import com.rullywjntk.mygithub.adapter.SectionPagerAdapter
import com.rullywjntk.mygithub.databinding.ActivityDetailBinding
import com.rullywjntk.mygithub.model.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var detailModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        setSupportActionBar(detailBinding.toolbar)
        supportActionBar?.title = getString(R.string.detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = intent.getStringExtra(EXTRA_USER)
        val bundle = Bundle()
        bundle.putString(EXTRA_USER, user)

        detailModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        detailModel.setDetailUser(user.toString())
        detailModel.getDetailUser().observe(this, {
            if (it != null) {
                detailBinding.apply {
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(ivPhoto)
                    tvName.text = it.name
                    tvUser.text = it.login
                    tvCompany.text = it.company
                    tvLocation.text = it.location
                    tvRepository.text = getString(R.string.repository1, it.public_repos)
                    tvFollower.text = getString(R.string.follower1, it.followers)
                    tvFollowing.text = getString(R.string.following1, it.following)
                }
            }
        })
        val sectionPagerAdapter = SectionPagerAdapter(this,  bundle )
        val viewPager = detailBinding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs = detailBinding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        private val TAB_TITLES = intArrayOf(R.string.follower, R.string.following)
    }

}
