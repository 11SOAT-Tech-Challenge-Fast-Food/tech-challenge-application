package br.com.fiap.ordermanagement.product.core.usercases

import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.gateways.IProductGateway

class CreateProductUseCase(
    private val productRepositoryGateway: IProductGateway
) {
    fun execute(productEntity: ProductEntity): ProductEntity {
        return productRepositoryGateway.save(productEntity)
    }
}
