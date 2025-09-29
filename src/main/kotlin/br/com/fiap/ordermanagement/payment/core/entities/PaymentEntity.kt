package br.com.fiap.ordermanagement.payment.core.entities

import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.payment.core.entities.enums.PaymentStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class PaymentEntity(
    val id: UUID = UUID.randomUUID(),
    val externalId: String? = null,
    val status: PaymentStatus = PaymentStatus.PENDING,
    val order: OrderEntity? = null,
    val value: BigDecimal = 0.toBigDecimal(),
    val qrCode: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
