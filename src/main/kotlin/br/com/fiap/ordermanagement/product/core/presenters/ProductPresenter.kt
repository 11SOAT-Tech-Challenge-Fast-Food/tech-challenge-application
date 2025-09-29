package br.com.fiap.ordermanagement.product.core.presenters

import br.com.fiap.ordermanagement.product.common.dtos.ProductDTO
import br.com.fiap.ordermanagement.product.core.entities.ProductEntity

object ProductPresenter {
    fun productToDto(product: ProductEntity) = ProductDTO(
        id = product.id,
        name = product.name,
        description = product.description,
        category = product.category,
        price = product.price,
        amount = product.amount
    )
}
