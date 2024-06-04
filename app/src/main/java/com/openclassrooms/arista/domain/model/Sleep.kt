package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.SleepDto
import java.time.LocalDateTime
import java.time.ZoneOffset

data class Sleep(
    @JvmField var startTime: LocalDateTime, // TODO voir cette annotation
    var duration: Int,
    var quality: Int) {

    companion object {
        fun fromDto(sleepDto: SleepDto) : Sleep {

            return Sleep(
                startTime= LocalDateTime.ofEpochSecond(sleepDto.startTime*1000, 0, ZoneOffset.UTC),
                duration=sleepDto.duration,
                quality= sleepDto.quality
            )
        }
    }
}
