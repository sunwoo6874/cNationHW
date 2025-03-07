package com.carenation.rentalservice.data.dto

import com.carenation.rentalservice.data.entity.*
import com.carenation.rentalservice.repository.CategoryRepository
import java.time.LocalDateTime

data class AutomobileDto(
        val id: Long? = null,
        val manufacturer: String,
        val model: String,
        val year: String,
        val status: String,
        val rentTime: LocalDateTime,
        val categories: Set<String> = emptySet()
) {
        fun toEntity(categoryRepository: CategoryRepository): Automobile =
                Automobile(
                        id = this.id,
                        manufacturer = this.manufacturer,
                        model = this.model,
                        year = this.year,
                        status = this.status,
                        rentTime = this.rentTime,
                        categories =
                                this.categories
                                        .mapNotNull { categoryName ->
                                                categoryRepository.findByBodyType(categoryName)
                                                        ?: categoryRepository.save(
                                                                Category(bodyType = categoryName)
                                                        )
                                        }
                                        .toSet()
                )
}
