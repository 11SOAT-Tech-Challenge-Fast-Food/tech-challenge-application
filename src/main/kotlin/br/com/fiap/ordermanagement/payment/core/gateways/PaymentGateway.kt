package br.com.fiap.ordermanagement.payment.core.gateways

import br.com.fiap.ordermanagement.order.common.mapper.OrderMapper.toEntity
import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.payment.common.interfaces.DataSource
import br.com.fiap.ordermanagement.payment.common.interfaces.PaymentProviderClient
import br.com.fiap.ordermanagement.payment.common.mapper.PaymentMapper.toDto
import br.com.fiap.ordermanagement.payment.common.mapper.PaymentMapper.toEntity
import br.com.fiap.ordermanagement.payment.core.entities.PaymentEntity
import br.com.fiap.ordermanagement.payment.core.entities.enums.PaymentStatus
import br.com.fiap.ordermanagement.payment.core.gateways.exception.NotFoundException
import java.math.BigDecimal
import java.util.*

class PaymentGateway(
    private val dataSource: DataSource,
    private val qrCodeClient: PaymentProviderClient
) : IPaymentGateway {
    override fun save(payment: PaymentEntity) {
        val paymentDTO = payment.toDto()

        dataSource.save(paymentDTO)
    }

    override fun findById(id: UUID): PaymentEntity {
        val payment = dataSource.findById(id) ?: throw NotFoundException("Payment not found")

        return payment.toEntity()
    }

    override fun findByPaymentProviderId(id: String): PaymentEntity {
        val payment = dataSource.findByPaymentProviderId(id) ?: throw NotFoundException("Payment not found")

        return payment.toEntity()
    }

    override fun findOrderByOrderId(orderId: UUID): OrderEntity {
        val order = dataSource.findOrderByOrderId(orderId) ?: throw NotFoundException("Order not found")

        return order.toEntity()
    }

    override fun generateQRCode(totalAmount: BigDecimal, order: OrderEntity): PaymentEntity {
        val payment = PaymentEntity(
            order = order,
            value = totalAmount
        )

        val qrCode = qrCodeClient.generateQRCode(totalAmount, payment.id)

        return payment.copy(
            externalId = qrCode.externalId,
            qrCode = qrCode.qrCode
        )
    }

    override fun updateOrder(orderId: UUID, status: PaymentStatus) {
        dataSource.updateOrder(orderId, status)
    }
}
