package com.openclassrooms.arista.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.openclassrooms.arista.data.UserDto

@Entity(
    tableName = "exercise",
    foreignKeys = [
        ForeignKey(
            entity = UserDto::class,
            parentColumns = ["id"],
            childColumns = ["userId"]
        )
    ]
)
data class ExerciseDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,


    @ColumnInfo(name = "start_time")
    var startTime: Long,


    @ColumnInfo(name = "duration")
    var duration: Int,


    @ColumnInfo(name = "category")
    var category: String,


    @ColumnInfo(name = "intensity")
    var intensity: Int,

    @ColumnInfo(index = true)
    var userId: Long   // Clé étrangère

)