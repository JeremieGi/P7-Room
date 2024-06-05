package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.ExerciseDto
import java.time.LocalDateTime
import java.time.ZoneOffset

data class Exercise(
    val id: Long? = null,
    var startTime: LocalDateTime,
    var duration: Int,
    var category: ExerciseCategory,
    var intensity: Int
) {

    /**
     * Convert model object to dto
     */
    fun toDto(userId : Long): ExerciseDto {

        return ExerciseDto(
            id = this.id?:0,
            startTime = this.startTime.toEpochSecond(ZoneOffset.UTC)*1000,
            duration = this.duration,
            category = this.category.toString(),
            intensity = this.intensity,
            userId = userId
        )
    }


    companion object {

        /**
         * Convert Dto to model object
         */
        fun fromDto(exerciceDto: ExerciseDto) : Exercise {

            return Exercise(

                id=exerciceDto.id,

                startTime= LocalDateTime.ofEpochSecond(exerciceDto.startTime*1000, 0, ZoneOffset.UTC),

                duration=exerciceDto.duration,

                category=enumValueOf(exerciceDto.category),

                intensity=exerciceDto.intensity
            )
        }
    }
}