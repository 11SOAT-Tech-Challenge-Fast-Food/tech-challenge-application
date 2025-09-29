package br.com.fiap.ordermanagement.customer.core.usecases

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.customer.core.gateways.ICustomerGateway

class FindAllCustomersUseCase(
    private val customerRepositoryGateway: ICustomerGateway
) {
    fun execute(): List<CustomerEntity> {
        return customerRepositoryGateway.findAll()
    }
}
