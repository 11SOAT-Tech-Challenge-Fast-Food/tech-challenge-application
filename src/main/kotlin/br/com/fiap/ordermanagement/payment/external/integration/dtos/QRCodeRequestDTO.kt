package br.com.fiap.ordermanagement.payment.external.integration.dtos

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class QRCodeRequestDTO(
    val type: String = "qr",
    val totalAmount: String,
    val description: String,
    val externalReference: String,
    val config: ConfigDTO,
    val transactions: Transactions
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ConfigDTO(
    val qr: ConfigQRDTO
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ConfigQRDTO(
    val externalPosId: String,
    val mode: String = "dynamic"
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Transactions(
    val payments: List<PaymentDTO>
)

data class PaymentDTO(
    val amount: String
)
