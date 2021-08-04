package com.example.coroutinesdemo.coroutines.room.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.coroutinesdemo.coroutines.room.entity.Contact
import java.sql.RowId

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(contact: Contact)

    @Query("DELETE FROM contact")
    suspend fun deleteAll()

    @Query("SELECT * FROM contact")
     fun getContact(): LiveData<List<Contact>>

    @Query("DELETE FROM contact WHERE id =:id")
   suspend fun deleteById(id: Int)

    @Query("UPDATE contact SET name = :mName WHERE id =:id")
    suspend fun updateByID(mName: String, id: Int)

    @Query("DELETE FROM contact WHERE name = :name")
    suspend fun removeByName(name: String):Int


}