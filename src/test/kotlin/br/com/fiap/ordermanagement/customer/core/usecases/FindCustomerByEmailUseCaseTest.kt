package br.com.fiap.ordermanagement.customer.core.usecases

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.customer.core.gateways.ICustomerGateway
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*

class FindCustomerByEmailUseCaseTest {
    private var customerGateway: ICustomerGateway = mockk(relaxed = false)
    private var findCustomerByEmailUseCase: FindCustomerByEmailUseCase = FindCustomerByEmailUseCase(customerGateway)

    @Test
    fun `should find customer by email successfully`() {
        val email = "john.doe@example.com"
        val expectedCustomer = CustomerEntity(
            id = UUID.randomUUID(),
            name = "John Doe",
            email = email,
            cpf = "12345678900",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        every { customerGateway.findByEmail(email) } returns expectedCustomer
        val result = findCustomerByEmailUseCase.execute(email)
        assertNotNull(result)

        verify(exactly = 1) { customerGateway.findByEmail(any()) }
    }

    @Test
    fun `should throw exception when customer not found`() {
        every { customerGateway.findByEmail(any()) } throws NoSuchElementException("Customer not found")
        assertThrows<NoSuchElementException> {
            findCustomerByEmailUseCase.execute("random@example")
        }
        verify(exactly = 1) { customerGateway.findByEmail(any()) }
    }
}
