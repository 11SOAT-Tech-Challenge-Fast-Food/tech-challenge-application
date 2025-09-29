package br.com.fiap.ordermanagement.customer.core.gateways

import br.com.fiap.ordermanagement.customer.common.dtos.CustomerDTO
import br.com.fiap.ordermanagement.customer.common.interfaces.DataSource
import br.com.fiap.ordermanagement.customer.common.mapper.CustomerMapper.toDto
import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class CustomerGatewayTest {
    private var dataSource: DataSource = mockk(relaxed = false)
    private var customerGateway: CustomerGateway = CustomerGateway(dataSource)
    private val customerId = UUID.randomUUID()

    @Test
    fun `should save customer successfully`() {
        val customerEntity = CustomerEntity(
            id = null,
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "123.456.789-00"
        )

        val customerDto = customerEntity.toDto().copy(
            id = customerId
        )

        every { dataSource.saveCustomer(any()) } returns customerDto

        val result = customerGateway.save(customerEntity)

        assertEquals(customerId, result.id)

        verify(exactly = 1) { dataSource.saveCustomer(any()) }
    }

    @Test
    fun `should update customer successfully`() {
        val customerEntity = CustomerEntity(
            id = customerId,
            name = "John Updated",
            email = "john.doe@example.com",
            cpf = "123.456.789-00"
        )

        val customerDto = customerEntity.toDto().copy(
            id = customerId
        )

        every { dataSource.updateCustomer(any()) } returns customerDto

        val result = customerGateway.update(customerEntity)

        assertEquals(customerId, result.id)

        verify(exactly = 1) { dataSource.updateCustomer(any()) }
    }

    @Test
    fun `should delete customer by id`() {
        every { dataSource.deleteCustomerById(customerId) } just Runs

        customerGateway.deleteById(customerId)

        verify(exactly = 1) { dataSource.deleteCustomerById(customerId) }
    }

    @Test
    fun `should find all customers`() {
        val customerDtos = listOf(
            CustomerDTO(
                id = UUID.randomUUID(),
                name = "John Doe",
                email = "john@example.com",
                cpf = "111.222.333-44"
            ),
            CustomerDTO(
                id = UUID.randomUUID(),
                name = "Jane Smith",
                email = "jane@example.com",
                cpf = "555.666.777-88"
            )
        )

        every { dataSource.findAllCustomers() } returns customerDtos

        val result = customerGateway.findAll()

        assertEquals(2, result.size)

        verify(exactly = 1) { dataSource.findAllCustomers() }
    }

    @Test
    fun `should find customer by id`() {
        val customerDto = CustomerDTO(
            id = customerId,
            name = "John Doe",
            email = "john@example.com",
            cpf = "123.456.789-00"
        )

        every { dataSource.findCustomerById(customerId) } returns customerDto

        val result = customerGateway.findById(customerId)

        assertEquals(customerId, result?.id)

        verify(exactly = 1) { dataSource.findCustomerById(customerId) }
    }

    @Test
    fun `should handle error when customer not found by id`() {
        every { dataSource.findCustomerById(customerId) } throws NoSuchElementException("Customer not found")

        assertThrows<NoSuchElementException> {
            customerGateway.findById(customerId)
        }

        verify(exactly = 1) { dataSource.findCustomerById(customerId) }
    }

    @Test
    fun `should find customer by CPF`() {
        val cpf = "123.456.789-00"
        val customerDto = CustomerDTO(
            id = customerId,
            name = "John Doe",
            email = "john@example.com",
            cpf = cpf
        )

        every { dataSource.findCustomerByCpf(cpf) } returns customerDto

        val result = customerGateway.findByCpf(cpf)

        assertEquals(cpf, result?.cpf)

        verify(exactly = 1) { dataSource.findCustomerByCpf(cpf) }
    }

    @Test
    fun `should handle error when customer not found by CPF`() {
        val cpf = "000.000.000-00"
        every { dataSource.findCustomerByCpf(cpf) } throws NoSuchElementException("Customer with CPF $cpf not found")

        assertThrows<NoSuchElementException> {
            customerGateway.findByCpf(cpf)
        }

        verify(exactly = 1) { dataSource.findCustomerByCpf(cpf) }
    }

    @Test
    fun `should find customer by email`() {
        val email = "john@example.com"
        val customerDto = CustomerDTO(
            id = customerId,
            name = "John Doe",
            email = email,
            cpf = "123.456.789-00"
        )

        every { dataSource.findCustomerByEmail(email) } returns customerDto

        val result = customerGateway.findByEmail(email)

        assertEquals(email, result?.email)

        verify(exactly = 1) { dataSource.findCustomerByEmail(email) }
    }

    @Test
    fun `should handle error when customer not found by email`() {
        val email = "nonexistent@example.com"
        every { dataSource.findCustomerByEmail(email) } throws NoSuchElementException("Customer with email $email not found")

        assertThrows<NoSuchElementException> {
            customerGateway.findByEmail(email)
        }

        verify(exactly = 1) { dataSource.findCustomerByEmail(email) }
    }
}
