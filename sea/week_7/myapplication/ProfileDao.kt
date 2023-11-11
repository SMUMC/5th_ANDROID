package org.example.myapplication

import androidx.room.*

@Dao
interface class ProfileDao {
    @Insert
    fun insert(profile: Profile) {
    }

    @Update
    fun update(profile: Profile) {
    }

    @Delete
    fun delete(profile: Profile) {
    }

    @Query("SELECT * FROM Profile")

    fun getAll(): List<Profile> {
    }
}