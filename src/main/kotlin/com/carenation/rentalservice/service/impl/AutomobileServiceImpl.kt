package com.carenation.rentalservice.service.impl

import com.carenation.rentalservice.data.dao.AutomobileDao
import com.carenation.rentalservice.data.dto.AutomobileDto
import com.carenation.rentalservice.data.entity.Automobile
import com.carenation.rentalservice.repository.CategoryRepository
import com.carenation.rentalservice.service.AutomobileService
import java.time.LocalDateTime
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AutomobileServiceImpl(
        private val automobileDao: AutomobileDao,
        private val categoryRepository: CategoryRepository,
        private var automobiles: List<Automobile>
) : AutomobileService {
        private val log = LoggerFactory.getLogger(this::class.java)
        companion object {
                val STATUS = listOf("available")
                val BODY_TYPE = listOf("미니SUV", "준중형SUV", "중형SUV", "경형RV", "대형RV", "중형트럭")
                val MANUFACTURERS = listOf("현대", "KIA", "쉐보레", "BMW", "벤츠", "토요타")
        }

        @Transactional
        override fun setCar(
                category: String,
                manufacturer: String,
                model: String,
                year: String,
                status: String,
                rentTime: LocalDateTime
        ): AutomobileDto {
                val newCarDto =
                        AutomobileDto(
                                id = null,
                                manufacturer = manufacturer,
                                model = model,
                                year = year,
                                status = status,
                                rentTime = rentTime,
                                categories = setOf(category)
                        )

                log.info("New car created (before saving to DB): {}", newCarDto)

                val savedCarDto = automobileDao.save(newCarDto.toEntity(categoryRepository)).toDto()

                log.info("New car saved: {}", savedCarDto)
                return savedCarDto
        }

        override fun getCar(searchType: String): List<AutomobileDto> {

                if (searchType.isNullOrBlank()) {
                        throw IllegalArgumentException("searchType is required.")
                }

                val automobiles: List<Automobile> =
                        when {
                                // 대여 상태
                                STATUS.contains(searchType.lowercase()) -> {
                                        if (searchType.equals("available", ignoreCase = true)) {
                                                automobileDao.findByStatus(searchType)
                                        } else {
                                                throw IllegalArgumentException(
                                                        "status for only available cars are allowed"
                                                )
                                        }
                                }
                                // 바디 타입
                                BODY_TYPE.contains(searchType.lowercase()) -> {
                                        val category = categoryRepository.findByBodyType(searchType)
                                        val bodyType =
                                                category?.bodyType
                                                        ?: throw NoSuchElementException(
                                                                "bodyType not found for: $searchType"
                                                        )
                                        automobileDao.findByCategory(bodyType)
                                }
                                // 제조사
                                MANUFACTURERS.any { it.equals(searchType, ignoreCase = true) } -> {
                                        automobileDao.findByManufacturer(searchType)
                                }
                                // 생산년도
                                searchType.all { it.isDigit() } -> {
                                        automobileDao.findByYear(searchType)
                                }
                                // 모델
                                else -> {
                                        automobileDao.findByModel(searchType).also {
                                                if (it.isEmpty()) {
                                                        throw IllegalStateException(
                                                                "unknown searchType requested: $searchType"
                                                        )
                                                }
                                        }
                                }
                        }

                if (automobiles.isEmpty()) {
                        throw NoSuchElementException(
                                "No automobiles found for searchType: $searchType"
                        )
                }

                val amDtoList = automobiles.map { it.toDto() }
                log.info("Retrieved car: {}", amDtoList)
                return amDtoList
        }

        override fun updateCar(): AutomobileDto {
                val updatedCarDto =
                        AutomobileDto(
                                id = 1L,
                                manufacturer = "Honda",
                                model = "Civic",
                                year = "2023",
                                status = "AVAILABLE",
                                rentTime = LocalDateTime.now(),
                                categories = setOf("Sedan") // 하드코딩 예시
                        )
                log.info("Updated car: {}", updatedCarDto)
                return updatedCarDto
        }
}
