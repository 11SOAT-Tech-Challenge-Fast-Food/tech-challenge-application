package br.com.fiap.ordermanagement.payment.external.integration.dtos

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class QRCodeResponseDTO(
    val id: String,
    val typeResponse: TypeResponseDTO
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class TypeResponseDTO(
    val qrData: String
)
