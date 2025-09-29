package br.com.fiap.ordermanagement.product.core.gateways

import br.com.fiap.ordermanagement.product.common.interfaces.DataSource
import br.com.fiap.ordermanagement.product.common.mapper.ProductMapper.toDTO
import br.com.fiap.ordermanagement.product.common.mapper.ProductMapper.toEntity
import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import java.util.*

class ProductGateway(
    private val dataSource: DataSource
) : IProductGateway {
    override fun save(productEntity: ProductEntity): ProductEntity {
        return dataSource.saveProduct(productEntity.toDTO()).toEntity()
    }

    override fun findAllProducts(): List<ProductEntity> {
        val products = dataSource.getAllProducts()
        if (products.isEmpty()) return emptyList()
        return products.map { it.toEntity() }
    }

    override fun findProductById(id: UUID): ProductEntity? {
        val product = dataSource.getProductById(id) ?: return null
        return product.toEntity()
    }

    override fun update(productEntity: ProductEntity): ProductEntity {
        return dataSource.saveProduct(productEntity.toDTO()).toEntity()
    }

    override fun deleteById(id: UUID) {
        return dataSource.deleteById(id)
    }

    override fun findAllByCategory(category: CategoryEnum): List<ProductEntity> {
        return dataSource.getAllProductsByCategory(category).map { it.toEntity() }
    }
}
