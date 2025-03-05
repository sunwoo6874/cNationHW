package com.carenation.rentalservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello")
class RentalController {

    @GetMapping
    fun registerCar(): String {
        return "Hello World"
    }
}
