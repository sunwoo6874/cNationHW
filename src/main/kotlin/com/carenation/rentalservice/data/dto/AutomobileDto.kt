package com.carenation.rentalservice.data.dto

data class AutomobileDto(
        val id: Long,
        val category: String?,
        val manufacturer: String?,
        val model: String?,
        val creationDate: String?
)
