package com.openclassrooms.arista.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.openclassrooms.arista.domain.model.Sleep
import java.time.LocalDateTime
import java.time.ZoneOffset

@Entity(
    tableName = "sleep",
    foreignKeys = [
        ForeignKey(
            entity = UserDto::class,
            parentColumns = ["id"],
            childColumns = ["userId"]
        )
    ]
)
data class SleepDto(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "start_time")
    var startTime: Long,

    @ColumnInfo(name = "duration")
    var duration: Int,

    @ColumnInfo(name = "quality")
    var quality: Int,

    @ColumnInfo(index = true)
    var userId: Long  // Clé étrangère

) {
    /**
     * Convert dto object to model object
     */
    fun toModelSleep(): Sleep {
        return Sleep(
            startTime= LocalDateTime.ofEpochSecond(this.startTime*1000, 0, ZoneOffset.UTC),
            duration=this.duration,
            quality= this.quality
        )
    }

}
