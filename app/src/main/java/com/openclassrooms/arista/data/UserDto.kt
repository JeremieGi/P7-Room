package com.openclassrooms.arista.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDto(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "name")
    var sName : String,

    @ColumnInfo(name = "email")
    var sEmail : String,

    @ColumnInfo(name = "password")
    var sPassword : String

)