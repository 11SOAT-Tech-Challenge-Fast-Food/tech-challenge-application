package br.com.fiap.ordermanagement.customer.common.mapper

import br.com.fiap.ordermanagement.customer.common.dtos.CustomerDTO
import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.customer.external.persistence.jpa.JpaCustomer
import java.util.*

object CustomerMapper {
    fun CustomerEntity.toDto(): CustomerDTO =
        CustomerDTO(
            id = this.id,
            name = this.name,
            email = this.email,
            cpf = this.cpf
        )

    fun CustomerDTO.toEntity(): CustomerEntity =
        CustomerEntity(
            id = this.id,
            name = this.name,
            email = this.email,
            cpf = this.cpf
        )

    fun CustomerDTO.toJpa(): JpaCustomer = JpaCustomer(
        id = this.id ?: UUID.randomUUID(),
        name = this.name,
        email = this.email,
        cpf = this.cpf
    )

    fun JpaCustomer.toDto() = CustomerDTO(
        id = id,
        name = name,
        email = email,
        cpf = cpf
    )
}
