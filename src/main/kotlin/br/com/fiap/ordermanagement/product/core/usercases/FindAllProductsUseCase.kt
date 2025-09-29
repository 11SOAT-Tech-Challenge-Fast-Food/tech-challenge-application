package br.com.fiap.ordermanagement.product.core.usercases

import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.gateways.IProductGateway

class FindAllProductsUseCase(
    private val productRepositoryGateway: IProductGateway
) {
    fun execute(): List<ProductEntity> {
        return productRepositoryGateway.findAllProducts()
    }
}
