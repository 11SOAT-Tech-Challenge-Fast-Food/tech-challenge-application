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

class CreateCustomerUseCaseTest {

    private var customerGateway: ICustomerGateway = mockk(relaxed = false)
    private var createCustomerUseCase: CreateCustomerUseCase = CreateCustomerUseCase(customerGateway)

    @Test
    fun `should create customer successfully`() {
        val customer = CustomerEntity(
            id = null,
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "123.456.789-00"
        )

        val expectedCustomer = customer.copy(
            id = UUID.randomUUID(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        every { customerGateway.findByCpf(any()) } returns null
        every { customerGateway.findByEmail(any()) } returns null
        every { customerGateway.save(any()) } returns expectedCustomer

        val result = createCustomerUseCase.execute(customer)

        assertEquals(expectedCustomer, result)

        verify(exactly = 1) { customerGateway.findByCpf(customer.cpf!!) }
        verify(exactly = 1) { customerGateway.findByEmail(customer.email!!) }
        verify(exactly = 1) { customerGateway.save(match { it.name == customer.name && it.email == customer.email && it.cpf == customer.cpf }) }
    }

    @Test
    fun `should throw exception if customer already exists`() {
        val customer = CustomerEntity(
            id = null,
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "123.456.789-00"
        )

        // Mock the gateway methods
        every { customerGateway.findByCpf(any()) } returns customer
        every { customerGateway.findByEmail(any()) } returns customer
        every { customerGateway.save(any()) } throws InvalidCustomerException("Customer already exist, cpf: ${customer.cpf}")

        assertThrows<InvalidCustomerException> {
            createCustomerUseCase.execute(customer)
        }

        verify(exactly = 1) { customerGateway.findByCpf(customer.cpf!!) }
        verify(exactly = 0) { customerGateway.findByEmail(customer.email!!) }
        verify(exactly = 0) { customerGateway.save(customer) }
    }

    @Test
    fun `should throw exception if email already exists`() {
        val customer = CustomerEntity(
            id = null,
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "123.456.789-00"
        )

        // Mock the gateway methods
        every { customerGateway.findByCpf(any()) } returns null
        every { customerGateway.findByEmail(any()) } returns customer
        every { customerGateway.save(any()) } throws InvalidCustomerException("Customer already exist, email: ${customer.email}")

        assertThrows<InvalidCustomerException> {
            createCustomerUseCase.execute(customer)
        }

        verify(exactly = 1) { customerGateway.findByCpf(customer.cpf!!) }
        verify(exactly = 1) { customerGateway.findByEmail(customer.email!!) }
        verify(exactly = 0) { customerGateway.save(customer) }
    }
}
