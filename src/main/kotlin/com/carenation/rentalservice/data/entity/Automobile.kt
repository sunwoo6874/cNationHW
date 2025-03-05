package com.carenation.rentalservice.data

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.annotation.*

@Entity
class Automobile(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
        var category: String?,
        var manufacturer: String?,
        var model: String?,
        var creationDate: String?
)
