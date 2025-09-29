package br.com.fiap.ordermanagement.customer.core.usecases

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.customer.core.gateways.ICustomerGateway
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

class FindAllCustomersUseCaseTest {

    private var customerGateway: ICustomerGateway = mockk(relaxed = false)
    private var findAllCustomersUseCase: FindAllCustomersUseCase = FindAllCustomersUseCase(customerGateway)

    @Test
    fun `should return all customers successfully`() {
        val customers = listOf(
            CustomerEntity(
                id = UUID.randomUUID(),
                name = "John Doe",
                email = "john@example.com",
                cpf = "12345678900",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            ),
            CustomerEntity(
                id = UUID.randomUUID(),
                name = "Jane Smith",
                email = "jane@example.com",
                cpf = "98765432100",
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        )

        every { customerGateway.findAll() } returns customers

        val result = findAllCustomersUseCase.execute()

        assertEquals(2, result.size)

        verify(exactly = 1) { customerGateway.findAll() }
    }

    @Test
    fun `should return empty list when no customers exist`() {
        every { customerGateway.findAll() } returns emptyList()

        val result = findAllCustomersUseCase.execute()

        assertTrue(result.isEmpty())

        verify(exactly = 1) { customerGateway.findAll() }
    }
}
