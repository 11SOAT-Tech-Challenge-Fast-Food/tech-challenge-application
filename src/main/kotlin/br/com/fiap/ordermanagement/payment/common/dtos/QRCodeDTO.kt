package br.com.fiap.ordermanagement.payment.common.dtos

data class QRCodeDTO(
    val externalId: String,
    val qrCode: String
)
