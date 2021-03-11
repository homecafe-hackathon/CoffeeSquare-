package makeus6.hackathon.homecafe.src.main


import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kakao.sdk.common.util.Utility
import makeus6.hackathon.homecafe.src.main.home.HomeFragment

import makeus6.hackathon.homecafe.src.main.myPage.MyPageFragment
import makeus6.hackathon.homecafe.R
import makeus6.hackathon.homecafe.config.BaseActivity
import makeus6.hackathon.homecafe.databinding.ActivityMainBinding
import makeus6.hackathon.homecafe.src.main.feed.AddPhotoActivity
import makeus6.hackathon.homecafe.src.main.home.HomeFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_main_btm_nav_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, HomeFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_main_btm_nav_my_page -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, MyPageFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            })

        val keyHash = Utility.getKeyHash(this)
        Log.d("Hash", keyHash)
    }
}