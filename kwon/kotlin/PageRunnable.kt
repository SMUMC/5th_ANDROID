package com.example.umc_5th_1week

import android.os.Handler
import androidx.viewpager2.widget.ViewPager2

class PageRunnable(private val handler: Handler, private val viewPager2: ViewPager2) : Runnable {

    private var currentPage = 0

    fun start() {
        handler.postDelayed(this, 3000) // 3초마다 슬라이드 시작
    }

    override fun run() {
        if (currentPage == 2) {
            currentPage = 0
        } else {
            currentPage++
        }
        viewPager2.setCurrentItem(currentPage, true)
        handler.postDelayed(this, 3000) // 3초마다 슬라이드 재시작
    }
}
