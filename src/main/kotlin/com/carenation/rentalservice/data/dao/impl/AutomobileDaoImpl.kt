package com.carenation.rentalservice.data.dao.impl

import com.carenation.rentalservice.data.dao.AutomobileDao
import com.carenation.rentalservice.data.entity.Automobile
import com.carenation.rentalservice.data.entity.Category
import com.carenation.rentalservice.repository.CategoryRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class AutomobileDaoImpl(
        private val categoryRepository: CategoryRepository,
        @PersistenceContext private val entityManager: EntityManager
) : AutomobileDao {
        private val log = LoggerFactory.getLogger(this::class.java)

        override fun findByStatus(status: String): List<Automobile> {
                val query =
                        entityManager
                                .createQuery(
                                        "SELECT a FROM Automobile a WHERE a.status = :status",
                                        Automobile::class.java
                                )
                                .setParameter("status", status)
                return query.resultList
        }

        override fun findByCategory(category: String): List<Automobile> {
                val query =
                        entityManager
                                .createQuery(
                                        "SELECT a FROM Automobile a JOIN a.categories c WHERE c.bodyType = :category",
                                        Automobile::class.java
                                )
                                .setParameter("category", category)
                return query.resultList
        }

        override fun findByManufacturer(manufacturer: String): List<Automobile> {
                val query =
                        entityManager
                                .createQuery(
                                        "SELECT a FROM Automobile a WHERE a.manufacturer = :manufacturer",
                                        Automobile::class.java
                                )
                                .setParameter("manufacturer", manufacturer)
                return query.resultList
        }

        override fun findByYear(year: String): List<Automobile> {
                val query =
                        entityManager
                                .createQuery(
                                        "SELECT a FROM Automobile a WHERE a.year = :year",
                                        Automobile::class.java
                                )
                                .setParameter("year", year)
                return query.resultList
        }

        override fun findByModel(model: String): List<Automobile> {
                val query =
                        entityManager
                                .createQuery(
                                        "SELECT a FROM Automobile a WHERE a.model = :model",
                                        Automobile::class.java
                                )
                                .setParameter("model", model)
                return query.resultList
        }

        override fun updateCategory(id: Long, newCategory: String): Automobile {
                val automobile =
                        entityManager.find(Automobile::class.java, id)
                                ?: throw IllegalArgumentException(
                                        "Automobile with id $id not found"
                                )

                val category =
                        categoryRepository.findByBodyType(newCategory)
                                ?: Category(bodyType = newCategory).also {
                                        categoryRepository.save(it) // 없으면 넣자
                                }

                automobile.categories = setOf(category)
                return automobile
        }

        override fun save(automobile: Automobile): Automobile {
                entityManager.persist(automobile)
                return automobile
        }

        override fun findById(id: Long): Automobile? {
                return entityManager.find(Automobile::class.java, id)
        }

        override fun deleteById(id: Long) {
                val automobile = findById(id)
                if (automobile != null) {
                        entityManager.remove(automobile)
                }
        }
}
