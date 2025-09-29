package br.com.fiap.ordermanagement.payment.core.usecases

import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.payment.core.entities.PaymentEntity
import br.com.fiap.ordermanagement.payment.core.entities.enums.PaymentStatus
import br.com.fiap.ordermanagement.payment.core.gateways.IPaymentGateway
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*

class FindPaymentUseCaseTest {

    private var paymentGateway: IPaymentGateway = mockk(relaxed = false)
    private var findPaymentUseCase: FindPaymentUseCase = FindPaymentUseCase(paymentGateway)

    @Test
    fun `should find payment by id successfully`() {
        val paymentId = UUID.randomUUID()

        val expectedPayment = PaymentEntity(
            id = paymentId,
            externalId = "external_id",
            status = PaymentStatus.PENDING,
            order = OrderEntity(),
            value = BigDecimal.TEN,
            qrCode = "test_qr_code"
        )

        every { paymentGateway.findById(paymentId) } returns expectedPayment

        val result = findPaymentUseCase.execute(paymentId)

        assertNotNull(result)

        verify(exactly = 1) { paymentGateway.findById(any()) }
    }

    @Test
    fun `should throw exception when payment is not found`() {
        every { paymentGateway.findById(any()) } throws NoSuchElementException("Payment not found")

        assertThrows<NoSuchElementException> {
            findPaymentUseCase.execute(UUID.randomUUID())
        }

        verify(exactly = 1) { paymentGateway.findById(any()) }
    }
}
