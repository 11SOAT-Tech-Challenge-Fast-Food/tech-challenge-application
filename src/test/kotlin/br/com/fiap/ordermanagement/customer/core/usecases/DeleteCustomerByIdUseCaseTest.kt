package br.com.fiap.ordermanagement.customer.core.usecases

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.customer.core.gateways.ICustomerGateway
import br.com.fiap.ordermanagement.customer.core.gateways.exception.InvalidCustomerException
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*

class DeleteCustomerByIdUseCaseTest {

    private var customerGateway: ICustomerGateway = mockk(relaxed = false)
    private var deleteCustomerByIdUseCase: DeleteCustomerByIdUseCase = DeleteCustomerByIdUseCase(customerGateway)
    private val customerId = UUID.randomUUID()

    @Test
    fun `should delete customer successfully`() {
        val existingCustomer = CustomerEntity(
            id = customerId,
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "123.456.789-00",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        every { customerGateway.findById(customerId) } returns existingCustomer
        every { customerGateway.deleteById(customerId) } just Runs

        deleteCustomerByIdUseCase.execute(customerId)

        verify(exactly = 1) { customerGateway.findById(customerId) }
        verify(exactly = 1) { customerGateway.deleteById(customerId) }
    }

    @Test
    fun `should throw exception if customer not found`() {
        every { customerGateway.findById(customerId) } returns null

        assertThrows<InvalidCustomerException> {
            deleteCustomerByIdUseCase.execute(customerId)
        }

        verify(exactly = 1) { customerGateway.findById(customerId) }
        verify(exactly = 0) { customerGateway.deleteById(any()) }
    }
}
