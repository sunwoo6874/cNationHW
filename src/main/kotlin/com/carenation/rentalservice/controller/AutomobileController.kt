package com.carenation.rentalservice.controller

import com.carenation.rentalservice.service.AutomobileService
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rental/api")
class AutomobileController(private val automobileService: AutomobileService) {

    @PostMapping("/register")
    fun registerCar(@RequestBody carInfo: Map<String, Any>): ResponseEntity<Map<String, Any>> {
        // json 파싱
        lateinit var result: Map<String, Any>

        val category = carInfo["category"] as? String
        if (category.isNullOrEmpty() || category.isNullOrBlank()) {
            result =
                    mapOf(
                            "message" to "Category is required fields, try again",
                            "carInfo" to carInfo
                    )
            return ResponseEntity.badRequest().body(result)
        }
        val manufacturer = carInfo["manufacturer"] as? String
        val model = carInfo["model"] as? String
        val year = carInfo["year"] as? String
        val status = carInfo["status"] as? String

        automobileService.setCar(category, manufacturer, model, year, status)

        result = mapOf("message" to "Car registered successfully", "carInfo" to carInfo)
        return ResponseEntity.ok(result)
    }

    // category 및 status로 고를 수 있어야 한다.
    @GetMapping
    @RequestMapping("/lookup")
    fun lookupCar(): String {
        return "lookup Car"
    }

    @GetMapping
    @RequestMapping("/update")
    fun updateCar(): String {
        return "update Car"
    }
}
