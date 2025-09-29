package br.com.fiap.ordermanagement.product.common.interfaces

import br.com.fiap.ordermanagement.product.common.dtos.ProductDTO
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import java.util.*

interface DataSource {
    fun saveProduct(product: ProductDTO): ProductDTO
    fun updateProduct(product: ProductDTO): ProductDTO
    fun getAllProducts(): List<ProductDTO>
    fun getAllProductsByCategory(category: CategoryEnum): List<ProductDTO>
    fun getProductById(id: UUID): ProductDTO?
    fun deleteById(id: UUID)
}
