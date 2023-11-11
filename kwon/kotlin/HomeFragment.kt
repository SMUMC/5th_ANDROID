package com.example.umc_5th_1week

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.umc_5th_1week.databinding.FragmentHomeBinding
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()
    private lateinit var pageRunnable: PageRunnable
    private val handler = android.os.Handler()
    private lateinit var songDB: SongDatabase

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        /*binding.homeAlbumImgIv1.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,AlbumFragment()).commitAllowingStateLoss()
        }*/

        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums())
//        albumDatas.apply{
//            add(Album(0,"Butter","방탄소년단 (BTS)",R.drawable.img_album_exp))
//            add(Album(1,"Lilac","아이유 (IU)",R.drawable.img_album_exp2))
//            add(Album(2,"Next Level","에스파 (AESPA)",R.drawable.img_album_exp3))
//            add(Album(3,"Boy with Luv","방탄소년단 (BTS)",R.drawable.img_album_exp4))
//            add(Album(4,"BBoom BBoom","모모랜드 (MOMOLAND)",R.drawable.img_album_exp5))
//            add(Album(5,"Weekend","태연 (Tae Yeon)",R.drawable.img_album_exp6))
//        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onPlayButtonClick(album: Album) {
                (activity as MainActivity).updateMiniPlayer(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }
        })

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val pannelAdapter = PannelVPAdapter(this)
        binding.homePannelBackgroundIv.adapter = pannelAdapter
        binding.homePannelBackgroundIv.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.dotsIndicator.setViewPager2(binding.homePannelBackgroundIv)

        pageRunnable = PageRunnable(handler, binding.homePannelBackgroundIv)
        GlobalScope.launch {
            pageRunnable.start()
        }

        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        val gson = Gson()
        val albumJson = gson.toJson(album)

        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }

}