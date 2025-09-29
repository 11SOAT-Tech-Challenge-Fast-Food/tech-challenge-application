package br.com.fiap.ordermanagement.product.core.usercases

import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.gateways.IProductGateway
import br.com.fiap.ordermanagement.product.core.gateways.exception.NotFoundProductException

class UpdateProductUseCase(
    private val productRepositoryGateway: IProductGateway
) {
    fun execute(productEntity: ProductEntity): ProductEntity {
        val productFound = productRepositoryGateway.findProductById(productEntity.id)
            ?: throw NotFoundProductException("Product not found by id: ${productEntity.id}")

        return productRepositoryGateway.update(
            productFound.copy(
                name = productEntity.name,
                description = productEntity.description,
                category = productEntity.category,
                price = productEntity.price,
                amount = productEntity.amount
            )
        )
    }
}
