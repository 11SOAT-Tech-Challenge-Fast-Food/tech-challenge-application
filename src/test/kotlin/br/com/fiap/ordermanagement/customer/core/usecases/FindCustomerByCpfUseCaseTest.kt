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

class FindCustomerByCpfUseCaseTest {

    private var customerGateway: ICustomerGateway = mockk(relaxed = false)
    private var findCustomerByCpfUseCase: FindCustomerByCpfUseCase = FindCustomerByCpfUseCase(customerGateway)

    @Test
    fun `should find customer by CPF successfully`() {
        val cpf = "12345678900"
        val expectedCustomer = CustomerEntity(
            id = UUID.randomUUID(),
            name = "John Doe",
            email = "john@example.com",
            cpf = cpf,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        every { customerGateway.findByCpf(cpf) } returns expectedCustomer

        val result = findCustomerByCpfUseCase.execute(cpf)

        assertNotNull(result)

        verify(exactly = 1) { customerGateway.findByCpf(any()) }
    }

    @Test
    fun `should throw exception when customer not found`() {
        every { customerGateway.findByCpf(any()) } throws NoSuchElementException("Customer not found")
        assertThrows<NoSuchElementException> {
            findCustomerByCpfUseCase.execute("000.000.000-00")
        }
        verify(exactly = 1) { customerGateway.findByCpf(any()) }
    }
}
