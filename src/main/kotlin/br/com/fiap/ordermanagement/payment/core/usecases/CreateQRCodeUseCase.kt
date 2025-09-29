package br.com.fiap.ordermanagement.payment.core.usecases

import br.com.fiap.ordermanagement.payment.core.entities.PaymentEntity
import br.com.fiap.ordermanagement.payment.core.gateways.IPaymentGateway
import java.util.*

class CreateQRCodeUseCase(
    private val paymentRepository: IPaymentGateway
) {
    fun execute(orderId: UUID): PaymentEntity {
        val order = paymentRepository.findOrderByOrderId(orderId)

        val totalAmount = order.items.sumOf { it.product.price }

        val payment = paymentRepository.generateQRCode(totalAmount, order)

        paymentRepository.save(payment)

        return payment
    }
}
