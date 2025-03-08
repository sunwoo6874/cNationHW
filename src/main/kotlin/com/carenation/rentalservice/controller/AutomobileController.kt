package com.carenation.rentalservice.controller

import com.carenation.rentalservice.service.AutomobileService
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
@RequestMapping("/rental/api")
class AutomobileController(private val automobileService: AutomobileService) {
    private val log = LoggerFactory.getLogger(this::class.java)

    private fun buildResponse(message: String, carInfo: Any): Map<String, Any> {
        return mapOf("message" to message, "carInfo" to carInfo)
    }

    @PostMapping("/register")
    fun registerCar(@RequestBody carInfo: Map<String, Any>): ResponseEntity<Map<String, Any>> {
        return try {
            val category =
                    carInfo["category"] as? String
                            ?: throw IllegalArgumentException("Category is required")
            val manufacturer = carInfo["manufacturer"] as? String ?: "Unknown"
            val model = carInfo["model"] as? String ?: "Unknown"
            val year = carInfo["year"] as? String ?: "Unknown"
            val status = (carInfo["status"] as? String)?.takeIf { it.isNotBlank() } ?: "AVAILABLE"
            val rentTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
            val registeredCar =
                    automobileService.setCar(category, manufacturer, model, year, status, rentTime)
            ResponseEntity.ok(buildResponse("Car registered successfully", registeredCar))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message as Any))
        }
    }

    // category(body_type) 및 status로 조회가 되어야한다.
    @GetMapping("/lookup")
    fun lookupCar(@RequestParam searchType: String): ResponseEntity<Any> {
        return try {
            val cars = automobileService.getCar(searchType)
            ResponseEntity.ok(cars)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        } catch (e: IllegalStateException) {
            ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(mapOf("error" to e.message))
        } catch (e: NoSuchElementException) {
            ResponseEntity.internalServerError().body(mapOf("error" to e.message))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            mapOf(
                                    "error" to
                                            "An unexpected error occurred while searching for info. cause{$e.message}"
                            )
                    )
        }
    }

    @PatchMapping("/update")
    fun updateCar(@RequestBody updateInfo: Map<String, Any>): ResponseEntity<Any> {

        if (updateInfo.isNullOrEmpty()) {
            return ResponseEntity.badRequest().body(mapOf("error" to "empty update info received"))
        }

        return try {
            val id =
                    updateInfo["id"] as? Int
                            ?: throw IllegalArgumentException("ID for the automobile is required")
            val newCategory =
                    updateInfo["category"] as? String
                            ?: throw IllegalArgumentException(
                                    "new category for the automobile is required"
                            )

            val updatedCar = automobileService.updateCar(id.toLong(), newCategory)
            ResponseEntity.ok(updatedCar)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            mapOf(
                                    "error" to
                                            "An unexpected error occurred while updating info. cause:{$e.message}"
                            )
                    )
        }
    }
}
