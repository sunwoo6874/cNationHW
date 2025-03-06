package com.carenation.rentalservice.data.entity

import com.carenation.rentalservice.data.dto.AutomobileDto
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "automobile")
data class Automobile(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
        val category: String? = null,
        val manufacturer: String? = null,
        val model: String? = null,
        val year: String? = null,
        val status: String? = null,
        val rentTime: LocalDateTime? = null
) {
        fun toDto(): AutomobileDto =
                AutomobileDto(
                        id = this.id,
                        category = this.category,
                        manufacturer = this.manufacturer,
                        model = this.model,
                        year = this.year,
                        status = this.status,
                        rentTime = this.rentTime
                )
}
