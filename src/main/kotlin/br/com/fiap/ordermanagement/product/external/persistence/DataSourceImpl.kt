package br.com.fiap.ordermanagement.product.external.persistence

import br.com.fiap.ordermanagement.product.common.dtos.ProductDTO
import br.com.fiap.ordermanagement.product.common.interfaces.DataSource
import br.com.fiap.ordermanagement.product.common.mapper.ProductMapper.toDTO
import br.com.fiap.ordermanagement.product.common.mapper.ProductMapper.toJpa
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import br.com.fiap.ordermanagement.product.external.persistence.jpa.ProductJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component("productDataSource")
class DataSourceImpl(
    private val productJpaRepository: ProductJpaRepository
) : DataSource {
    override fun saveProduct(product: ProductDTO): ProductDTO {
        return productJpaRepository.save(product.toJpa()).toDTO()
    }

    override fun updateProduct(product: ProductDTO): ProductDTO {
        return productJpaRepository.save(product.toJpa()).toDTO()
    }

    override fun getAllProducts(): List<ProductDTO> {
        return productJpaRepository.findAll().map { it.toDTO() }
    }

    override fun getAllProductsByCategory(category: CategoryEnum): List<ProductDTO> {
        return productJpaRepository.findAllByCategory(category).map { it.toDTO() }
    }

    override fun getProductById(id: UUID): ProductDTO? {
        return productJpaRepository.findById(id).map { it.toDTO() }.orElse(null)
    }

    override fun deleteById(id: UUID) {
        productJpaRepository.deleteById(id)
    }
}
