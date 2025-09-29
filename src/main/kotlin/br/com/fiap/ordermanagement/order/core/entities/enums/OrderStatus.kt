package br.com.fiap.ordermanagement.order.core.entities.enums

enum class OrderStatus {
    CREATED,
    PAID,
    PAYMENT_REJECTED,
    CANCELED,
    RECEIVED,
    IN_PREPARATION,
    OVERDUE,
    READY,
    COMPLETED;

    fun validateChange(status: OrderStatus): Boolean {
        return when (this) {
            PAID -> status == CREATED
            CANCELED -> status == CREATED
            RECEIVED -> status == PAID
            IN_PREPARATION -> status == RECEIVED
            OVERDUE -> status == IN_PREPARATION
            READY -> status == IN_PREPARATION || status == OVERDUE
            COMPLETED -> status == READY
            else -> false
        }
    }
}
