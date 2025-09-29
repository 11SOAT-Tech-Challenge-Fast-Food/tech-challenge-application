package br.com.fiap.ordermanagement.product.common.mapper

import br.com.fiap.ordermanagement.product.common.dtos.ProductDTO
import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.external.persistence.jpa.JpaProduct
import java.util.*

object ProductMapper {
    fun ProductDTO.toEntity() = ProductEntity(
        id = this.id ?: UUID.randomUUID(),
        name = this.name,
        description = this.description,
        category = this.category,
        price = this.price,
        amount = this.amount
    )

    fun ProductEntity.toDTO() = ProductDTO(
        id = this.id,
        name = this.name,
        description = this.description,
        category = this.category,
        price = this.price,
        amount = this.amount
    )

    fun ProductDTO.toJpa() = JpaProduct(
        id = this.id!!,
        name = this.name,
        description = this.description,
        category = this.category,
        price = this.price,
        amount = this.amount
    )

    fun JpaProduct.toDTO() = ProductDTO(
        id = this.id,
        name = this.name,
        description = this.description,
        category = this.category,
        price = this.price,
        amount = this.amount
    )
}
