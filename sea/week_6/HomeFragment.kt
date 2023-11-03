package com.example.flo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentAlbumBinding
import com.example.flo.databinding.FragmentHomeBinding
import com.google.gson.Gson
import java.time.LocalTime
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.homePannelAlbumImg1.setOnClickListener {
//            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()
//        }

        albumDatas.apply {
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_first_album_default))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_second_album_default))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_third_album_default))
            add(Album("Weekend", "태연 (TAE Yeon)", R.drawable.img_4th_album_default))
        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayAlbumRv.adapter = albumRVAdapter
        binding.homeTodayAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener {
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }

            override fun onRemoveAlbum(position: Int) {
                albumRVAdapter.removeItem(position)
            }
        })



        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        //뷰페이저가 좌우로 스크롤 될 수 있게

        val backgroundAdapter = BackgroundVPAdapter(this)
        backgroundAdapter.addFragment(BackgroundFragment(R.drawable.img_first_album_default))
        backgroundAdapter.addFragment(BackgroundFragment(R.drawable.img_second_album_default))
        backgroundAdapter.addFragment(BackgroundFragment(R.drawable.img_third_album_default))
        backgroundAdapter.addFragment(BackgroundFragment(R.drawable.img_4th_album_default))
        backgroundAdapter.addFragment(BackgroundFragment(R.drawable.img_5th_album_default))
        binding.homePannelBackgroundVp.adapter = backgroundAdapter
        binding.homePannelBackgroundVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        //background 이미지 설정

        //circleindicator와 뷰페이저 연결
        binding.indicator.setViewPager(binding.homePannelBackgroundVp)


        //자동으로 이미지 전환 구현
        val timer = Timer()
        val delay = 2000L //2초뒤에 실행
        val period = 2000L
        val handler = Handler(Looper.getMainLooper()) //이거 추가하니까 오류가 발생하지 않음
        val timerTask = object : TimerTask() {
            override fun run() {
                handler.post {
                    binding.homePannelBackgroundVp.currentItem = (binding.homePannelBackgroundVp.currentItem + 1) % 5
                }
            }
        }
        timer.scheduleAtFixedRate(timerTask, delay, period)


        return binding.root
    }
    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }
}