package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerVPAdapter(fragment: Fragment) :FragmentStateAdapter(fragment){
    private val fragmentlist : ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int = fragmentlist.size
    //클래스에서 연결된 뷰페이저에게 데이터를 전달할 때 데이터를 몇개 전달할 것이냐 써주는 함수

    override fun createFragment(position: Int): Fragment = fragmentlist[position]
    //프래그먼트 리스트 안에 있는 아이템들을 생성해주는 함수

    fun addFragment(fragment: Fragment) {
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size-1)
    }


}