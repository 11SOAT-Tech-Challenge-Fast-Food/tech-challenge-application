package br.com.fiap.ordermanagement.customer.core.usecases

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.customer.core.gateways.ICustomerGateway
import br.com.fiap.ordermanagement.customer.core.gateways.exception.InvalidCustomerException
import java.util.*

class UpdateCustomerUseCase(
    private val customerRepositoryGateway: ICustomerGateway
) {
    fun execute(id: UUID, customer: CustomerEntity): CustomerEntity {
        val customerToBeUpdated = validateCustomerToUpdate(customer.copy(id = id))

        return customerRepositoryGateway.update(customerToBeUpdated)
    }

    private fun validateCustomerToUpdate(customerToBeUpdated: CustomerEntity): CustomerEntity {
        validateExistById(customerToBeUpdated.id!!)

        when {
            customerToBeUpdated.cpf != null && customerToBeUpdated.email != null -> {
                validateExistByCpf(customerToBeUpdated)
                validateExistByEmail(customerToBeUpdated)
            }

            customerToBeUpdated.cpf != null -> {
                validateExistByCpf(customerToBeUpdated)
            }

            customerToBeUpdated.email != null -> {
                validateExistByEmail(customerToBeUpdated)
            }
        }

        return customerToBeUpdated
    }

    private fun validateExistById(id: UUID) {
        customerRepositoryGateway.findById(id)
            ?: throw InvalidCustomerException("Customer not found with id: $id")
    }

    private fun validateExistByEmail(customer: CustomerEntity) {
        val customerFoundByEmail = customerRepositoryGateway.findByEmail(customer.email!!) ?: return

        if (customerFoundByEmail.id != customer.id) {
            throw InvalidCustomerException("Email already registered by different customer, email: ${customer.email}")
        }
    }

    private fun validateExistByCpf(customer: CustomerEntity) {
        val customerFoundByCpf = customerRepositoryGateway.findByCpf(customer.cpf!!) ?: return

        if (customerFoundByCpf.id != customer.id) {
            throw InvalidCustomerException("Cpf already registered by different customer, cpf: ${customerFoundByCpf.cpf}")
        }
    }
}
