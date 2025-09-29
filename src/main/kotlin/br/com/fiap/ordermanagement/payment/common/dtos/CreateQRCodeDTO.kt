package br.com.fiap.ordermanagement.payment.common.dtos

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.util.*

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateQRCodeDTO(
    val orderId: UUID
)
