package com.carenation.rentalservice.controller

import com.carenation.rentalservice.data.dto.AutomobileDto
import com.carenation.rentalservice.service.AutomobileService
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(AutomobileController::class)
class RentalserviceApplicationTests {

        @Autowired private lateinit var mockMvc: MockMvc
        @Autowired private lateinit var objectMapper: ObjectMapper
        @MockBean private lateinit var automobileService: AutomobileService

        @Test
        fun `registerCar 성공 테스트`() {
                val carInfo =
                        mapOf(
                                "category" to "Sedan",
                                "manufacturer" to "현대",
                                "model" to "투싼",
                                "year" to "2023"
                        )
                val rentTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
                val registeredCar =
                        AutomobileDto(
                                categories = setOf("Sedan"),
                                manufacturer = "현대",
                                model = "투싼",
                                year = "2023",
                                status = "AVAILABLE",
                                rentTime = rentTime,
                                id = 1
                        )
                `when`(automobileService.setCar("Sedan", "현대", "투싼", "2023", "AVAILABLE", rentTime))
                        .thenReturn(registeredCar)
                mockMvc.perform(
                                post("/rental/api/register")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(carInfo))
                        )
                        .andExpect(status().isOk)
                        .andExpect(jsonPath("$.message").value("Car registered successfully"))
                        .andExpect(jsonPath("$.carInfo.categories[0]").value("Sedan"))
        }

        @Test
        fun `registerCar 실패 테스트 - Category 없음`() {
                val carInfo = mapOf("manufacturer" to "현대", "model" to "투싼", "year" to "2023")
                mockMvc.perform(
                                post("/rental/api/register")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(carInfo))
                        )
                        .andExpect(status().isBadRequest)
                        .andExpect(jsonPath("$.error").value("Category is required"))
        }

        @Test
        fun `lookupCar 성공 테스트`() {
                val searchType = "미니SUV"
                val cars =
                        listOf(
                                AutomobileDto(
                                        id = 1,
                                        categories = setOf("미니SUV"),
                                        manufacturer = "현대",
                                        model = "코나",
                                        year = "2023",
                                        status = "AVAILABLE",
                                        rentTime = LocalDateTime.now()
                                )
                        )
                `when`(automobileService.getCar(searchType)).thenReturn(cars)
                mockMvc.perform(get("/rental/api/lookup?searchType=$searchType"))
                        .andExpect(status().isOk)
                        .andExpect(jsonPath("$[0].categories[0]").value("미니SUV"))
        }

        @Test
        fun `lookupCar 실패 테스트 - 에러 발생`() {
                val searchType = "Unknown"
                `when`(automobileService.getCar(searchType))
                        .thenThrow(IllegalArgumentException("Invalid search type"))
                mockMvc.perform(get("/rental/api/lookup?searchType=$searchType"))
                        .andExpect(status().isBadRequest)
                        .andExpect(jsonPath("$.error").value("Invalid search type"))
        }

        @Test
        fun `updateCar 성공 테스트`() {
                val updateInfo = mapOf("id" to 1, "category" to "SUV")
                val updatedCar =
                        AutomobileDto(
                                id = 1,
                                categories = setOf("SUV"),
                                manufacturer = "현대",
                                model = "투싼",
                                year = "2023",
                                status = "AVAILABLE",
                                rentTime = LocalDateTime.now()
                        )
                `when`(automobileService.updateCar(1L, "SUV")).thenReturn(updatedCar)
                mockMvc.perform(
                                patch("/rental/api/update")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(updateInfo))
                        )
                        .andExpect(status().isOk)
                        .andExpect(jsonPath("$.categories[0]").value("SUV"))
        }

        @Test
        fun `updateCar 실패 테스트 - ID 없음`() {
                val carInfo = mapOf("category" to "SUV")
                mockMvc.perform(
                                patch("/rental/api/update")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(carInfo))
                        )
                        .andExpect(status().isBadRequest)
                        .andExpect(jsonPath("$.error").value("ID for the automobile is required"))
        }

        @Test
        fun `updateCar 실패 테스트 - Category 없음`() {
                val updateInfo = mapOf("id" to 1)
                mockMvc.perform(
                                patch("/rental/api/update")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(updateInfo))
                        )
                        .andExpect(status().isBadRequest)
                        .andExpect(
                                jsonPath("$.error")
                                        .value("new category for the automobile is required")
                        )
        }

        @Test
        fun `updateCar 실패 테스트 - Empty Update Info`() {
                val updateInfo = mapOf<String, Any>()
                mockMvc.perform(
                                patch("/rental/api/update")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(updateInfo))
                        )
                        .andExpect(status().isBadRequest)
                        .andExpect(jsonPath("$.error").value("empty update info received"))
        }
}
