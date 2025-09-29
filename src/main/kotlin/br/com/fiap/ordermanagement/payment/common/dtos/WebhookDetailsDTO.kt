package br.com.fiap.ordermanagement.payment.common.dtos

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class WebhookDetailsDTO(
    val externalReference: String,
    val payments: List<PaymentDetailsDTO>
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PaymentDetailsDTO(
    val id: String,
    val status: String,
    val statusDetail: String
)
