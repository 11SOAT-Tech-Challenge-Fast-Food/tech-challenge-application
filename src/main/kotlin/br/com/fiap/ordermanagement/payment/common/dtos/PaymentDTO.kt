package br.com.fiap.ordermanagement.payment.common.dtos

import br.com.fiap.ordermanagement.order.common.dtos.OrderDTO
import br.com.fiap.ordermanagement.payment.core.entities.enums.PaymentStatus
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import java.util.*

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class PaymentDTO(
    val id: UUID,
    val externalId: String?,
    val status: PaymentStatus,
    val order: OrderDTO?,
    val qrCode: String? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
