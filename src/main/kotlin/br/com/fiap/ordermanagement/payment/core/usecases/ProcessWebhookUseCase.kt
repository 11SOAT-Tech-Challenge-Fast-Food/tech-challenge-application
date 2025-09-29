package br.com.fiap.ordermanagement.payment.core.usecases

import br.com.fiap.ordermanagement.payment.core.entities.enums.PaymentStatus
import br.com.fiap.ordermanagement.payment.core.gateways.IPaymentGateway
import org.hibernate.query.sqm.tree.SqmNode.log

class ProcessWebhookUseCase(
    private val paymentGateway: IPaymentGateway
) {
    fun execute(paymentProviderId: String, status: PaymentStatus) {
        try {
            val payment = paymentGateway.findByPaymentProviderId(paymentProviderId)

            if (payment.status == PaymentStatus.PENDING) {
                paymentGateway.save(payment.copy(status = status))

                paymentGateway.updateOrder(payment.order!!.id, status)
            }
        } catch (e: NoSuchElementException) {
            log.warn("${e.message} for $paymentProviderId")
        }
    }
}
