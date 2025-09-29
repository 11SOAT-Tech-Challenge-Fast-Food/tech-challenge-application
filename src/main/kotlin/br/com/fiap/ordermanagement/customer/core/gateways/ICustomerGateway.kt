package br.com.fiap.ordermanagement.customer.core.gateways

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import java.util.UUID

interface ICustomerGateway {
    fun save(customerEntity: CustomerEntity): CustomerEntity
    fun update(customerEntity: CustomerEntity): CustomerEntity
    fun findAll(): List<CustomerEntity>
    fun findById(id: UUID): CustomerEntity?
    fun deleteById(id: UUID)
    fun findByCpf(cpf: String): CustomerEntity?
    fun findByEmail(email: String): CustomerEntity?
}
