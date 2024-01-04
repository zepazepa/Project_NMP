package com.ubayaprojectnmp.cerbung

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ubayaprojectnmp.cerbung.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var fragment:ArrayList<Fragment> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedFile = "com.ubayaprojectnmp.cerbung"
        val sharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("user_id", 0)

        fragment.add(HomeFragment.newInstance(id))
        fragment.add(FollowFragment.newInstance(id))
        fragment.add(CreateFragment.newInstance(id))
        fragment.add(UserFragment.newInstance())
        fragment.add(PrefsFragment.newInstance(id))

        binding.viewPager2.adapter = MyAdapter(this,fragment)
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNav.selectedItemId = binding.bottomNav.menu.getItem(position).itemId
            }
        })
        binding.bottomNav.setOnItemSelectedListener{
            binding.viewPager2.currentItem = when(it.itemId){
                R.id.ItemHome -> 0
                R.id.ItemFollow ->1
                R.id.ItemCreate ->2
                R.id.ItemUsers ->3
                R.id.ItemPrefs ->4
                else->0
            }
            true
        }
    }
}