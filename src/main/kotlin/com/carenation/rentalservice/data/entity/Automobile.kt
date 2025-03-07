package com.carenation.rentalservice.data.entity

import com.carenation.rentalservice.data.dto.AutomobileDto
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "automobile")
class Automobile(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
        var manufacturer: String,
        var model: String,
        var year: String,
        var status: String,
        var rentTime: LocalDateTime,
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
                name = "automobile_category",
                joinColumns = [JoinColumn(name = "automobile_id")],
                inverseJoinColumns = [JoinColumn(name = "category_id")]
        )
        var categories: Set<Category> = emptySet()
) {
        constructor() : this(null, "", "", "", "", LocalDateTime.now(), emptySet())

        fun toDto(): AutomobileDto =
                AutomobileDto(
                        id = this.id,
                        manufacturer = this.manufacturer,
                        model = this.model,
                        year = this.year,
                        status = this.status,
                        rentTime = this.rentTime,
                        categories = this.categories.map { it.bodyType }.toSet()
                )
}
