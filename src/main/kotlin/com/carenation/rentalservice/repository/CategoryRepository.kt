package com.carenation.rentalservice.repository

import com.carenation.rentalservice.data.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByBodyType(bodyType: String): Category?
}
