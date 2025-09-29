package br.com.fiap.ordermanagement.product.core.usercases

import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import br.com.fiap.ordermanagement.product.core.gateways.IProductGateway
import br.com.fiap.ordermanagement.product.core.gateways.exception.InvalidCategoryException
import java.util.*

class FindAllProductsByCategoryUseCase(
    private val productRepositoryGateway: IProductGateway
) {
    fun execute(category: String): List<ProductEntity> {
        try {
            val categoryEnum = CategoryEnum.valueOf(category.uppercase(Locale.getDefault()))
            return productRepositoryGateway.findAllByCategory(categoryEnum)
        } catch (e: IllegalArgumentException) {
            throw InvalidCategoryException("Category '$category' not found. Valid categories are: ${CategoryEnum.entries.joinToString { it.name }}")
        }
    }
}
