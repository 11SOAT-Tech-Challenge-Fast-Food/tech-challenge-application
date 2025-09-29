package br.com.fiap.ordermanagement.payment.common.dtos

import br.com.fiap.ordermanagement.payment.common.PaymentProviderStatus
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class WebhookDTO(
    val action: String,
    val apiVersion: String,
    val applicationId: String,
    val data: DataValue,
    val dateCreated: String,
    val liveMode: Boolean,
    val type: String,
    val userId: Long
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class DataValue(
    val externalReference: String? = null,
    val id: String,
    val status: PaymentProviderStatus,
    val statusDetail: String? = null,
    val totalAmount: String? = null,
    val totalPaidAmount: String? = null,
    val transactions: TransactionData? = null,
    val type: String? = null,
    val version: Int? = null
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class TransactionData(
    val payments: List<PaymentData>
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PaymentData(
    val amount: String,
    val id: String,
    val paidAmount: String,
    val paymentMethod: PaymentMethod,
    val reference: PaymentReference,
    val status: String,
    val statusDetail: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PaymentMethod(
    val id: String,
    val installments: Int,
    val type: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PaymentReference(
    val id: String
)
