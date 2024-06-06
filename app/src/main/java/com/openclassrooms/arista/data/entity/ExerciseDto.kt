package com.openclassrooms.arista.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.openclassrooms.arista.domain.model.Exercise
import java.time.LocalDateTime
import java.time.ZoneOffset

// DTA = Data Transfer Object (entre cet object et la table exercise de la base de données)

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

){
    /**
     * Convert to a Exercice model class
     */
    fun toModelExercice(): Exercise {

        return Exercise(

            id=this.id,

            startTime= LocalDateTime.ofEpochSecond(this.startTime*1000, 0, ZoneOffset.UTC),

            duration=this.duration,

            category=enumValueOf(this.category),

            intensity=this.intensity
        )
    }

}