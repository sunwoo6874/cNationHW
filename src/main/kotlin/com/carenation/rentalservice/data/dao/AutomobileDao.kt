package com.carenation.rentalservice.data.dao

import com.carenation.rentalservice.data.entity.Automobile

interface AutomobileDao {
    fun findByStatus(status: String): List<Automobile>
    fun findByCategory(category: String): List<Automobile>
    fun save(automobile: Automobile): Automobile
    fun findById(id: Long): Automobile?
    fun deleteById(id: Long)
}
