package com.rullywjntk.mygithub.view.favorite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rullywjntk.mygithub.R
import com.rullywjntk.mygithub.adapter.UserAdapter
import com.rullywjntk.mygithub.data.User
import com.rullywjntk.mygithub.data.db.FavoriteUser
import com.rullywjntk.mygithub.databinding.ActivityFavoriteBinding
import com.rullywjntk.mygithub.model.FavoriteViewModel
import com.rullywjntk.mygithub.view.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteBinding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var favoriteModel: FavoriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(favoriteBinding.root)
        setSupportActionBar(favoriteBinding.toolbar)
        supportActionBar?.title = getString(R.string.favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })

        favoriteModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favoriteModel.getUser()?.observe(this,{
            if (it != null){
                val list = mapList(it)
                adapter.setData(list)
            }
        })
        favoriteBinding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }
    }

    private fun mapList(it: List<FavoriteUser>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in it) {
            val mapUser = User(user.id, user.login, user.avatar_url)
            listUser.add(mapUser)
        }
        return listUser
    }

    private fun showSelectedUser(data: User) {
        val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, data.login)
        intent.putExtra(DetailActivity.EXTRA_FAVORITE, data.id)
        intent.putExtra(DetailActivity.EXTRA_AVATAR, data.avatar_url)
        startActivity(intent)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}