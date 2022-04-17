package com.salman.gitsy.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.salman.gitsy.R
import com.salman.gitsy.view.search.SearchActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {


    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        timer = object : CountDownTimer(1500, 1000) {
            override fun onFinish() {
                val intent = Intent(this@SplashActivity, SearchActivity::class.java)
                intent.apply {
                    startActivity(this)
                    finish()
                }
            }

            override fun onTick(p0: Long) {
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this :: timer.isInitialized)
            timer.cancel()
    }
}