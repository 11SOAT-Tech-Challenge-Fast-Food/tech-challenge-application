package br.com.fiap.ordermanagement.product.core.gateways

import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IProductGateway {
    fun save(productEntity: ProductEntity): ProductEntity
    fun findAllProducts(): List<ProductEntity>
    fun findProductById(id: UUID): ProductEntity?
    fun update(productEntity: ProductEntity): ProductEntity
    fun deleteById(id: UUID)
    fun findAllByCategory(category: CategoryEnum): List<ProductEntity>
}
