package br.com.fiap.ordermanagement.customer.core.gateways

import br.com.fiap.ordermanagement.customer.common.interfaces.DataSource
import br.com.fiap.ordermanagement.customer.common.mapper.CustomerMapper.toDto
import br.com.fiap.ordermanagement.customer.common.mapper.CustomerMapper.toEntity
import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import java.util.*

class CustomerGateway(
    private val dataSource: DataSource
) : ICustomerGateway {
    override fun save(customerEntity: CustomerEntity): CustomerEntity {
        val customerCreated = dataSource.saveCustomer(customerEntity.toDto())

        return customerCreated.toEntity()
    }

    override fun update(customerEntity: CustomerEntity): CustomerEntity {
        val customerUpdated = dataSource.updateCustomer(customerEntity.toDto())

        return customerUpdated.toEntity()
    }

    override fun findAll(): List<CustomerEntity> {
        val listCustomers = dataSource.findAllCustomers()

        return listCustomers.map { it.toEntity() }
    }

    override fun findById(id: UUID): CustomerEntity? {
        val customer = dataSource.findCustomerById(id) ?: return null

        return customer.toEntity()
    }

    override fun deleteById(id: UUID) {
        dataSource.deleteCustomerById(id)
    }

    override fun findByCpf(cpf: String): CustomerEntity? {
        val customer = dataSource.findCustomerByCpf(cpf) ?: return null

        return customer.toEntity()
    }

    override fun findByEmail(email: String): CustomerEntity? {
        val customer = dataSource.findCustomerByEmail(email) ?: return null

        return customer.toEntity()
    }
}
