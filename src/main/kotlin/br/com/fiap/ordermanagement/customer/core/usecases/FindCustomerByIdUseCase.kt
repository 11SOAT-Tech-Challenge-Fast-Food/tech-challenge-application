package br.com.fiap.ordermanagement.customer.core.usecases

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.customer.core.gateways.ICustomerGateway
import br.com.fiap.ordermanagement.customer.core.gateways.exception.NotFoundCustomerException
import java.util.UUID

class FindCustomerByIdUseCase(
    private val customerRepositoryGateway: ICustomerGateway
) {
    fun execute(id: UUID): CustomerEntity {
        return customerRepositoryGateway.findById(id)
            ?: throw NotFoundCustomerException("Customer not found with id: $id")
    }
}
