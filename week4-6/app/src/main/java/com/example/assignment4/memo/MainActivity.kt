package com.example.assignment4.memo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment4.R
import com.example.assignment4.databinding.ActivityMainBinding
import com.example.assignment4.memo.fragment.CalendarFragment
import com.example.assignment4.memo.fragment.HomeFragment
import com.example.assignment4.memo.fragment.ProfileFragment

class MainActivity :AppCompatActivity(){
    private val binding:ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(binding.containerFragment.id, HomeFragment())
        binding.navBottom.run {
            setOnItemSelectedListener {
                when(it.itemId){
                    R.id.menu_home-> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(binding.containerFragment.id, HomeFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_profile->{
                        supportFragmentManager
                            .beginTransaction()
                            .replace(binding.containerFragment.id,ProfileFragment())
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_calendar-> {
                        supportFragmentManager
                            .beginTransaction()
                            .replace(binding.containerFragment.id, CalendarFragment())
                            .commitAllowingStateLoss()
                    }
                }
            true
            }
            selectedItemId=R.id.menu_home
        }
    }
}