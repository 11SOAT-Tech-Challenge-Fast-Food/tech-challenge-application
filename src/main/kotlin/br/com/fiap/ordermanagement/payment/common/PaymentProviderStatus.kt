package br.com.fiap.ordermanagement.payment.common

import br.com.fiap.ordermanagement.payment.core.entities.enums.PaymentStatus

enum class PaymentProviderStatus {
    created,
    processed,
    action_required,
    failed,
    processing,
    refunded,
    canceled;

    fun toEntityStatus(): PaymentStatus {
        return when (this) {
            created -> PaymentStatus.PENDING
            processed -> PaymentStatus.APPROVED
            action_required -> PaymentStatus.PENDING
            failed -> PaymentStatus.REPROVED
            processing -> PaymentStatus.PENDING
            refunded -> PaymentStatus.REPROVED
            canceled -> PaymentStatus.REPROVED
        }
    }
}
