package com.carenation.rentalservice.service.impl

import com.carenation.rentalservice.data.dao.AutomobileDao
import com.carenation.rentalservice.data.dto.AutomobileDto
import com.carenation.rentalservice.service.AutomobileService
import java.time.LocalDateTime
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.*

@Slf4j
@Service
class AutomobileServiceImpl(private val automobileDao: AutomobileDao) : AutomobileService {
        private val log = LoggerFactory.getLogger(this::class.java)

        @Transactional
        override fun setCar(
                category: String?,
                manufacturer: String?,
                model: String?,
                year: String?,
                status: String?,
                rentTime: LocalDateTime
        ): AutomobileDto {
                if (category.isNullOrEmpty() || category.isNullOrBlank()) {
                        throw IllegalArgumentException("Category is required fields.")
                }

                val newCarDto =
                        AutomobileDto(
                                id = null, // 새로운 차량이므로 id는 null
                                category = category,
                                manufacturer = manufacturer,
                                model = model,
                                year = year,
                                status = status,
                                rentTime = rentTime
                        )

                log.info("New car created (before saving to DB): {}", newCarDto)

                val savedCarDto = automobileDao.save(newCarDto.toEntity()).toDto()

                log.info("New car saved: {}", savedCarDto)
                return savedCarDto
        }

        // TODO: 실제로는 DB에서 데이터를 가져오는 로직 필요
        override fun getCar(): AutomobileDto {
                val carDto =
                        AutomobileDto(
                                id = 1L, // 예시로 하드코딩
                                category = "Sedan",
                                manufacturer = "Honda",
                                model = "Civic",
                                year = "2022",
                                status = "RENTED",
                                rentTime = LocalDateTime.now()
                        )
                log.info("Retrieved car: {}", carDto)
                return carDto
        }

        // TODO: 실제로는 기존 데이터를 업데이트하는 로직 필요
        override fun updateCar(): AutomobileDto {
                val updatedCarDto =
                        AutomobileDto(
                                id = 1L,
                                category = "Sedan",
                                manufacturer = "Honda",
                                model = "Civic",
                                year = "2023", // 업데이트된 연식 예시
                                status = "AVAILABLE",
                                rentTime = LocalDateTime.now()
                        )
                log.info("Updated car: {}", updatedCarDto)
                return updatedCarDto
        }
}
