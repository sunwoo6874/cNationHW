package com.carenation.rentalservice.data.repository

import com.carenation.rentalservice.data.Automobile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AutomobileRepository : JpaRepository<Automobile, Long> {
    fun findByCategory(category: String): List<Automobile>
    fun findByYear(date: String): List<Automobile>
}
