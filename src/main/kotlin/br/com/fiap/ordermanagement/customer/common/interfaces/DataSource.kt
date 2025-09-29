package br.com.fiap.ordermanagement.customer.common.interfaces

import br.com.fiap.ordermanagement.customer.common.dtos.CustomerDTO
import java.util.*

interface DataSource {
    fun saveCustomer(customer: CustomerDTO): CustomerDTO
    fun findCustomerById(id: UUID): CustomerDTO?
    fun findAllCustomers(): List<CustomerDTO>
    fun updateCustomer(customer: CustomerDTO): CustomerDTO
    fun deleteCustomerById(id: UUID)
    fun findCustomerByCpf(cpf: String): CustomerDTO?
    fun findCustomerByEmail(email: String): CustomerDTO?
}
