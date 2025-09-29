package br.com.fiap.ordermanagement.customer.core.usecases

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.customer.core.gateways.ICustomerGateway
import br.com.fiap.ordermanagement.customer.core.gateways.exception.InvalidCustomerException

class CreateCustomerUseCase(
    private val customerRepositoryGateway: ICustomerGateway
) {
    fun execute(customer: CustomerEntity): CustomerEntity {
        validateCustomer(customer)
        return customerRepositoryGateway.save(customer)
    }

    private fun validateCustomer(customer: CustomerEntity) {
        when {
            customer.cpf != null && customer.email != null -> {
                validateAlreadyExistByCpf(customer.cpf)
                validateAlreadyExistByEmail(customer.email)
            }

            customer.cpf != null -> {
                validateAlreadyExistByCpf(customer.cpf)
            }

            customer.email != null -> {
                validateAlreadyExistByEmail(customer.email)
            }
        }
    }

    private fun validateAlreadyExistByEmail(email: String) {
        customerRepositoryGateway.findByEmail(email)?.let {
            throw InvalidCustomerException("Customer already exist with email: $email")
        }
    }

    private fun validateAlreadyExistByCpf(cpf: String) {
        customerRepositoryGateway.findByCpf(cpf)?.let {
            throw InvalidCustomerException("Customer already exist with cpf: $cpf")
        }
    }
}
