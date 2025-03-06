package com.carenation.rentalservice.data.dto

import com.carenation.rentalservice.data.entity.Automobile
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class AutomobileDto(
        val id: Long? = null,
        val category: String? = null,
        val manufacturer: String? = null,
        val model: String? = null,
        val year: String? = null,
        val status: String? = null,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") val rentTime: LocalDateTime? = null
) {
        fun toEntity(): Automobile =
                Automobile(
                        id = this.id,
                        category = this.category,
                        manufacturer = this.manufacturer,
                        model = this.model,
                        year = this.year,
                        status = this.status,
                        rentTime = this.rentTime
                )
}
