package br.com.fiap.ordermanagement.customer.core.presenters

import br.com.fiap.ordermanagement.customer.common.dtos.CustomerDTO
import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity

object CustomerPresenter {

    fun customerToDto(customer: CustomerEntity) = CustomerDTO(
        id = customer.id,
        name = customer.name,
        email = customer.email,
        cpf = customer.cpf
    )
}
