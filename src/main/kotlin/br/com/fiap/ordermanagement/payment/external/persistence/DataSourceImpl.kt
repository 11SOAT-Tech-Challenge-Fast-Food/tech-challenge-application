package br.com.fiap.ordermanagement.payment.external.persistence

import br.com.fiap.ordermanagement.order.common.dtos.OrderDTO
import br.com.fiap.ordermanagement.order.common.dtos.UpdateOrderDTO
import br.com.fiap.ordermanagement.order.common.mapper.OrderMapper.toDto
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.order.external.api.controller.OrderApiController
import br.com.fiap.ordermanagement.order.external.persistence.jpa.OrderJpaRepository
import br.com.fiap.ordermanagement.payment.common.dtos.PaymentDTO
import br.com.fiap.ordermanagement.payment.common.interfaces.DataSource
import br.com.fiap.ordermanagement.payment.common.mapper.PaymentMapper.toDto
import br.com.fiap.ordermanagement.payment.common.mapper.PaymentMapper.toJpaPayment
import br.com.fiap.ordermanagement.payment.core.entities.enums.PaymentStatus
import br.com.fiap.ordermanagement.payment.external.persistence.jpa.PaymentJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component("paymentDataSource")
class DataSourceImpl(
    private val paymentJpaRepository: PaymentJpaRepository,
    private val orderJpaRepository: OrderJpaRepository,
    private val controller: OrderApiController
) : DataSource {
    override fun save(payment: PaymentDTO) {
        val paymentEntity = payment.toJpaPayment()

        paymentJpaRepository.save(paymentEntity)
    }

    override fun findById(id: UUID): PaymentDTO? {
        val payment = paymentJpaRepository.findById(id)

        if (payment.isEmpty) {
            return null
        }

        return payment.get().toDto()
    }

    override fun findByPaymentProviderId(id: String): PaymentDTO? {
        val payment = paymentJpaRepository.findByExternalId(id)

        if (payment.isEmpty) {
            return null
        }

        return payment.get().toDto()
    }

    override fun findOrderByOrderId(orderId: UUID): OrderDTO? {
        val order = orderJpaRepository.findById(orderId)

        if (order.isEmpty) {
            return null
        }

        return order.get().toDto()
    }

    override fun updateOrder(orderId: UUID, status: PaymentStatus) {
        val orderStatus = when (status) {
            PaymentStatus.APPROVED -> OrderStatus.PAID
            PaymentStatus.REPROVED -> OrderStatus.PAYMENT_REJECTED
            else -> return
        }

        controller.updateOrder(orderId, UpdateOrderDTO(orderStatus))
    }
}
