package com.example.umc_5th_1week

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PannelVPAdapter (fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FirstPannelFragment()
            1 -> MusicFileFragment()
            else -> SavedAlbumFragment()
        }
    }


}