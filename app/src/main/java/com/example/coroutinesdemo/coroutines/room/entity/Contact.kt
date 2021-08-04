package com.example.coroutinesdemo.coroutines.room.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = arrayOf("name"), unique = true)], tableName = "contact")
class Contact(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var name: String?,
    var phone: Int?
)

