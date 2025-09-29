package br.com.fiap.ordermanagement.product.core.usercases

import br.com.fiap.ordermanagement.product.core.gateways.IProductGateway
import br.com.fiap.ordermanagement.product.core.gateways.exception.NotFoundProductException
import java.util.*

class DeleteProductUseCase(
    private val iProductGateway: IProductGateway
) {
    fun execute(id: UUID) {
        val productTobeDeleted =
            iProductGateway.findProductById(id) ?: throw NotFoundProductException("Product not found by id: $id")

        iProductGateway.deleteById(productTobeDeleted.id)
    }
}
