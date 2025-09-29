package br.com.fiap.ordermanagement.payment.core.gateways

import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.payment.core.entities.PaymentEntity
import br.com.fiap.ordermanagement.payment.core.entities.enums.PaymentStatus
import java.math.BigDecimal
import java.util.*

interface IPaymentGateway {
    fun save(payment: PaymentEntity)
    fun findById(id: UUID): PaymentEntity
    fun findByPaymentProviderId(id: String): PaymentEntity
    fun findOrderByOrderId(orderId: UUID): OrderEntity
    fun generateQRCode(totalAmount: BigDecimal, order: OrderEntity): PaymentEntity
    fun updateOrder(orderId: UUID, status: PaymentStatus)
}
