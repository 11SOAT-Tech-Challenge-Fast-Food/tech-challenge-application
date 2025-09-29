package br.com.fiap.ordermanagement.payment.common.interfaces

import br.com.fiap.ordermanagement.order.common.dtos.OrderDTO
import br.com.fiap.ordermanagement.payment.common.dtos.PaymentDTO
import br.com.fiap.ordermanagement.payment.core.entities.enums.PaymentStatus
import java.util.*

interface DataSource {
    fun save(payment: PaymentDTO)
    fun findById(id: UUID): PaymentDTO?
    fun findByPaymentProviderId(id: String): PaymentDTO?
    fun findOrderByOrderId(orderId: UUID): OrderDTO?
    fun updateOrder(orderId: UUID, status: PaymentStatus)
}
