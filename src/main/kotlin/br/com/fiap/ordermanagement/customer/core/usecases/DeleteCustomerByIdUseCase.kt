package br.com.fiap.ordermanagement.customer.core.usecases

import br.com.fiap.ordermanagement.customer.core.gateways.ICustomerGateway
import br.com.fiap.ordermanagement.customer.core.gateways.exception.InvalidCustomerException
import java.util.UUID

class DeleteCustomerByIdUseCase(
    private val customerRepositoryGateway: ICustomerGateway
) {
    fun execute(id: UUID) {
        validateExistById(id)
        return customerRepositoryGateway.deleteById(id)
    }

    private fun validateExistById(id: UUID) {
        customerRepositoryGateway.findById(id)
            ?: throw InvalidCustomerException("Customer not found with id: $id")
    }
}
