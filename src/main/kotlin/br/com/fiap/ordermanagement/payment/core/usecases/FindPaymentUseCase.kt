package br.com.fiap.ordermanagement.payment.core.usecases

import br.com.fiap.ordermanagement.payment.core.entities.PaymentEntity
import br.com.fiap.ordermanagement.payment.core.gateways.IPaymentGateway
import java.util.*

class FindPaymentUseCase(
    private val paymentRepository: IPaymentGateway
) {
    fun execute(id: UUID): PaymentEntity {
        return paymentRepository.findById(id)
    }
}
