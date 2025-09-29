package br.com.fiap.ordermanagement.product.core.usercases

import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.gateways.IProductGateway
import br.com.fiap.ordermanagement.product.core.gateways.exception.NotFoundProductException
import java.util.*

class FindProductByIdUseCase(
    private val productRepositoryGateway: IProductGateway
) {
    fun execute(id: UUID): ProductEntity {
        return productRepositoryGateway.findProductById(id)?: throw NotFoundProductException("Product not found by id: $id")
    }
}
