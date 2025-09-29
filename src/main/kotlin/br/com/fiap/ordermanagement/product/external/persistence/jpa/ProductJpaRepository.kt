package br.com.fiap.ordermanagement.product.external.persistence.jpa

import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProductJpaRepository : JpaRepository<JpaProduct, UUID> {
    fun findAllByCategory(category: CategoryEnum): List<JpaProduct>
}
