package com.carenation.rentalservice.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "category")
class Category(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
        @Column(name = "bodyType") var bodyType: String
) {
        constructor() : this(null, "")
}
