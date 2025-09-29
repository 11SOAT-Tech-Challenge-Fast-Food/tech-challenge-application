package br.com.fiap.ordermanagement.customer.core.usecases

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.customer.core.gateways.ICustomerGateway
import br.com.fiap.ordermanagement.customer.core.gateways.exception.InvalidCustomerException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*

class UpdateCustomerUseCaseTest {
    private var customerGateway: ICustomerGateway = mockk(relaxed = false)
    private var updateCustomerUseCase: UpdateCustomerUseCase = UpdateCustomerUseCase(customerGateway)

    private val customerId = UUID.randomUUID()

    @Test
    fun `should update customer successfully`() {
        val existingCustomer = CustomerEntity(
            id = customerId,
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "123.456.789-00",
            createdAt = LocalDateTime.now().minusDays(1),
            updatedAt = LocalDateTime.now().minusDays(1)
        )

        val updatedData = CustomerEntity(
            id = null,
            name = "John Updated",
            email = "john.updated@example.com",
            cpf = "123.456.789-00"
        )
        every { customerGateway.findById(customerId) } returns existingCustomer
        every { customerGateway.findByEmail("john.updated@example.com") } returns null
        every { customerGateway.findByCpf("123.456.789-00") } returns null
        every { customerGateway.update(any()) } returns existingCustomer.copy(
            name = "John Updated",
            email = "john.updated@example.com"
        )

        val result = updateCustomerUseCase.execute(customerId, updatedData)
        assertEquals(customerId, result.id)
        assertEquals("John Updated", result.name)
        assertEquals("john.updated@example.com", result.email)

        verify(exactly = 1) { customerGateway.findById(customerId) }
        verify(exactly = 1) { customerGateway.findByEmail("john.updated@example.com") }
        verify(exactly = 1) { customerGateway.findByCpf("123.456.789-00") }
        verify(exactly = 1) { customerGateway.update(any()) }
    }

    @Test
    fun `should throw exception if customer not found`() {
        val updatedData = CustomerEntity(
            id = null,
            name = "John Updated",
            email = "john.updated@example.com",
            cpf = "123.456.789-00"
        )
        every { customerGateway.findById(customerId) } returns null

        assertThrows<InvalidCustomerException> {
            updateCustomerUseCase.execute(customerId, updatedData)
        }

        verify(exactly = 1) { customerGateway.findById(customerId) }
        verify(exactly = 0) { customerGateway.findByEmail(any()) }
        verify(exactly = 0) { customerGateway.findByCpf(any()) }
        verify(exactly = 0) { customerGateway.update(any()) }
    }

    @Test
    fun `should throw exception if email already exists`() {
        val existingCustomer = CustomerEntity(
            id = customerId,
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "123.456.789-00",
            createdAt = LocalDateTime.now().minusDays(1),
            updatedAt = LocalDateTime.now().minusDays(1)
        )
        val updatedData = CustomerEntity(
            id = null,
            name = "John Updated",
            email = "john.updated@example.com",
            cpf = "123.456.789-00"
        )
        val otherCustomer = CustomerEntity(
            id = UUID.randomUUID(),
            name = "Other Customer",
            email = "john.updated@example.com",
            cpf = "987.654.321-00",
            createdAt = LocalDateTime.now().minusDays(1),
            updatedAt = LocalDateTime.now().minusDays(1)
        )

        every { customerGateway.findById(customerId) } returns existingCustomer
        every { customerGateway.findByEmail("john.updated@example.com") } returns otherCustomer
        every { customerGateway.findByCpf("123.456.789-00") } returns null

        assertThrows<InvalidCustomerException> {
            updateCustomerUseCase.execute(customerId, updatedData)
        }

        verify(exactly = 1) { customerGateway.findById(customerId) }
        verify(exactly = 1) { customerGateway.findByCpf(any()) }
        verify(exactly = 1) { customerGateway.findByEmail("john.updated@example.com") }
        verify(exactly = 0) { customerGateway.update(any()) }
    }

    @Test
    fun `should throw exception if cpf already exists`() {
        val existingCustomer = CustomerEntity(
            id = customerId,
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "123.456.789-00",
            createdAt = LocalDateTime.now().minusDays(1),
            updatedAt = LocalDateTime.now().minusDays(1)
        )
        val updatedData = CustomerEntity(
            id = null,
            name = "John Updated",
            email = "john.updated@example.com",
            cpf = "123.456.789-00"
        )
        val otherCustomer = CustomerEntity(
            id = UUID.randomUUID(),
            name = "Other Customer",
            email = "other@example.com",
            cpf = "123.456.789-00",
            createdAt = LocalDateTime.now().minusDays(1),
            updatedAt = LocalDateTime.now().minusDays(1)
        )

        every { customerGateway.findById(customerId) } returns existingCustomer
        every { customerGateway.findByEmail("john.updated@example.com") } returns null
        every { customerGateway.findByCpf("123.456.789-00") } returns otherCustomer

        assertThrows<InvalidCustomerException> {
            updateCustomerUseCase.execute(customerId, updatedData)
        }

        verify(exactly = 1) { customerGateway.findById(customerId) }
        verify(exactly = 1) { customerGateway.findByCpf("123.456.789-00") }
        verify(exactly = 0) { customerGateway.findByEmail("john.updated@example.com") }
        verify(exactly = 0) { customerGateway.update(any()) }
    }
}
