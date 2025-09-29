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

class FindCustomerByIdUseCaseTest {
    private var customerGateway: ICustomerGateway = mockk(relaxed = false)
    private var findCustomerByIdUseCase: FindCustomerByIdUseCase = FindCustomerByIdUseCase(customerGateway)

    @Test
    fun `should find customer by id successfully`() {
        val expectedCustomer = CustomerEntity(
            id = UUID.randomUUID(),
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "123.456.789-00",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        every { customerGateway.findById(any()) } returns expectedCustomer
        val result = findCustomerByIdUseCase.execute(expectedCustomer.id!!)

        assertNotNull(result)
        verify(exactly = 1) { customerGateway.findById(any()) }
    }

    @Test
    fun `should throw exception when customer not found`() {
        val customerId = UUID.randomUUID()

        every { customerGateway.findById(customerId) } throws NoSuchElementException("Customer not found")
        assertThrows<NoSuchElementException> {
            findCustomerByIdUseCase.execute(customerId)
        }

        verify(exactly = 1) { customerGateway.findById(any()) }
    }
}
