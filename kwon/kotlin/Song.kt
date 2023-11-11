package com.example.umc_5th_1week

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "SongTable")
data class Song(
    val title : String = "",
    val singer : String = "",
    var second : Int = 0,
    var playTime : Int = 0,
    var isPlaying : Boolean = false,
    var music: String = "",
    var coverImg: Int? = null,
    var isLike: Boolean = false,
    @ColumnInfo(name = "albumIdx") var albumIdx: Int = 0,
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}

