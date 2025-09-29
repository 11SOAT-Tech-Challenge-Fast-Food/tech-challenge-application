package br.com.fiap.ordermanagement.payment.core.presenters

import br.com.fiap.ordermanagement.payment.common.dtos.PaymentDTO
import br.com.fiap.ordermanagement.payment.core.entities.PaymentEntity

object PaymentPresenter {
    fun paymentToDto(payment: PaymentEntity) = PaymentDTO(
        id = payment.id,
        status = payment.status,
        externalId = payment.externalId,
        qrCode = payment.qrCode,
        order = null,
        createdAt = payment.createdAt,
        updatedAt = payment.updatedAt
    )
}
