package br.com.fiap.ordermanagement.customer.core.usecases

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.customer.core.gateways.ICustomerGateway
import br.com.fiap.ordermanagement.customer.core.gateways.exception.NotFoundCustomerException

class FindCustomerByEmailUseCase(
    private val customerRepositoryGateway: ICustomerGateway
) {
    fun execute(email: String): CustomerEntity {
        return customerRepositoryGateway.findByEmail(email)
            ?: throw NotFoundCustomerException("Customer not found by email: $email")
    }
}
