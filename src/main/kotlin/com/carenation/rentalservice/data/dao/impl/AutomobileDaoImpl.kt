package com.carenation.rentalservice.data.dao.impl

import com.carenation.rentalservice.data.dao.AutomobileDao
import com.carenation.rentalservice.data.entity.Automobile
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class AutomobileDaoImpl : AutomobileDao {

    @PersistenceContext private lateinit var entityManager: EntityManager

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
