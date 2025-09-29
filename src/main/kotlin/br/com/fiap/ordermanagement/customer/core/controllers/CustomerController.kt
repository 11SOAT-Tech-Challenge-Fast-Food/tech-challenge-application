package br.com.fiap.ordermanagement.customer.core.controllers

import br.com.fiap.ordermanagement.customer.common.dtos.CustomerDTO
import br.com.fiap.ordermanagement.customer.common.interfaces.DataSource
import br.com.fiap.ordermanagement.customer.common.mapper.CustomerMapper.toEntity
import br.com.fiap.ordermanagement.customer.core.gateways.CustomerGateway
import br.com.fiap.ordermanagement.customer.core.gateways.ICustomerGateway
import br.com.fiap.ordermanagement.customer.core.presenters.CustomerPresenter
import br.com.fiap.ordermanagement.customer.core.usecases.CreateCustomerUseCase
import br.com.fiap.ordermanagement.customer.core.usecases.DeleteCustomerByIdUseCase
import br.com.fiap.ordermanagement.customer.core.usecases.FindAllCustomersUseCase
import br.com.fiap.ordermanagement.customer.core.usecases.FindCustomerByCpfUseCase
import br.com.fiap.ordermanagement.customer.core.usecases.FindCustomerByEmailUseCase
import br.com.fiap.ordermanagement.customer.core.usecases.FindCustomerByIdUseCase
import br.com.fiap.ordermanagement.customer.core.usecases.UpdateCustomerUseCase
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomerController(
    dataSource: DataSource
) {
    private val iCustomerGateway: ICustomerGateway = CustomerGateway(dataSource)

    fun findAllCustomers(): List<CustomerDTO> {
        val findAllCustomers = FindAllCustomersUseCase(iCustomerGateway)

        val customers = findAllCustomers.execute()

        return customers.map { CustomerPresenter.customerToDto(it) }
    }

    fun createCustomer(customerDTO: CustomerDTO): CustomerDTO {
        val createCustomer = CreateCustomerUseCase(iCustomerGateway)

        val customerCreated = createCustomer.execute(customerDTO.toEntity())

        return CustomerPresenter.customerToDto(customerCreated)
    }

    fun findCustomerById(id: UUID): CustomerDTO {
        val findCustomerById = FindCustomerByIdUseCase(iCustomerGateway)

        val customer = findCustomerById.execute(id)

        return CustomerPresenter.customerToDto(customer)
    }

    fun findCustomerByCpf(cpf: String): CustomerDTO {
        val findCustomerByCpf = FindCustomerByCpfUseCase(iCustomerGateway)

        val customer = findCustomerByCpf.execute(cpf)

        return CustomerPresenter.customerToDto(customer)
    }

    fun findCustomerByEmail(email: String): CustomerDTO {
        val findCustomerByEmail = FindCustomerByEmailUseCase(iCustomerGateway)

        val customer = findCustomerByEmail.execute(email)

        return CustomerPresenter.customerToDto(customer)
    }

    fun updateCustomer(id: UUID, customerDTO: CustomerDTO): CustomerDTO {
        val updateCustomer = UpdateCustomerUseCase(iCustomerGateway)

        val customerUpdated = updateCustomer.execute(id, customerDTO.toEntity())

        return CustomerPresenter.customerToDto(customerUpdated)
    }

    fun deleteCustomer(id: UUID) {
        val deleteCustomer = DeleteCustomerByIdUseCase(iCustomerGateway)

        deleteCustomer.execute(id)
    }
}
