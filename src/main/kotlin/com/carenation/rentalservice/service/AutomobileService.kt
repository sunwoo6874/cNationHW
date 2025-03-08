package com.carenation.rentalservice.service

import com.carenation.rentalservice.data.dto.AutomobileDto
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

interface AutomobileService {
    fun setCar(
            category: String,
            manufacturer: String,
            model: String,
            year: String,
            status: String,
            rentTime: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
    ): AutomobileDto

    // category 및 status로 고를 수 있어야 한다.
    fun getCar(searchType: String): List<AutomobileDto>

    fun updateCar(id: Long, category: String): AutomobileDto
}
