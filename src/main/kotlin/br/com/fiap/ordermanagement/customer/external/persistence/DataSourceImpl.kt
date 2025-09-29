package br.com.fiap.ordermanagement.customer.external.persistence

import br.com.fiap.ordermanagement.customer.common.dtos.CustomerDTO
import br.com.fiap.ordermanagement.customer.common.interfaces.DataSource
import br.com.fiap.ordermanagement.customer.common.mapper.CustomerMapper.toDto
import br.com.fiap.ordermanagement.customer.common.mapper.CustomerMapper.toJpa
import br.com.fiap.ordermanagement.customer.core.gateways.exception.NotFoundCustomerException
import br.com.fiap.ordermanagement.customer.external.persistence.jpa.CustomerJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component("customerDataSource")
class DataSourceImpl(
    private val customerJpaRepository: CustomerJpaRepository
) : DataSource {
    override fun saveCustomer(customer: CustomerDTO): CustomerDTO {
        val jpaNewCustomer = customer.toJpa()
        return customerJpaRepository.save(jpaNewCustomer).toDto()
    }

    override fun findCustomerById(id: UUID): CustomerDTO {
        val customer = customerJpaRepository.findById(id)

        if (customer.isEmpty) {
            throw NotFoundCustomerException("Customer not found with id: $id")
        }

        return customer.get().toDto()
    }

    override fun findAllCustomers(): List<CustomerDTO> {
        val listCustomers = customerJpaRepository.findAll()

        return listCustomers.map { it.toDto() }
    }

    override fun updateCustomer(customer: CustomerDTO): CustomerDTO {
        return customerJpaRepository.save(customer.toJpa()).toDto()
    }

    override fun deleteCustomerById(id: UUID) {
        customerJpaRepository.deleteById(id)
    }

    override fun findCustomerByCpf(cpf: String): CustomerDTO? {
        val jpaCustomer = customerJpaRepository.findByCpf(cpf) ?: return null

        return jpaCustomer.toDto()
    }

    override fun findCustomerByEmail(email: String): CustomerDTO? {
        val jpaCustomer = customerJpaRepository.findByEmail(email) ?: return null

        return jpaCustomer.toDto()
    }
}
