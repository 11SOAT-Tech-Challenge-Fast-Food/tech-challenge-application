package br.com.fiap.ordermanagement.customer.core.usecases

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.customer.core.gateways.ICustomerGateway
import br.com.fiap.ordermanagement.customer.core.gateways.exception.NotFoundCustomerException

class FindCustomerByCpfUseCase(
    private val customerRepositoryGateway: ICustomerGateway
) {
    fun execute(cpf: String): CustomerEntity {
        return customerRepositoryGateway.findByCpf(cpf)
            ?: throw NotFoundCustomerException("Customer not found with cpf: $cpf")
    }
}
