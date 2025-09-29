package br.com.fiap.ordermanagement.payment.core.controllers

import br.com.fiap.ordermanagement.payment.common.dtos.PaymentDTO
import br.com.fiap.ordermanagement.payment.common.dtos.WebhookDTO
import br.com.fiap.ordermanagement.payment.common.interfaces.DataSource
import br.com.fiap.ordermanagement.payment.common.interfaces.PaymentProviderClient
import br.com.fiap.ordermanagement.payment.core.gateways.IPaymentGateway
import br.com.fiap.ordermanagement.payment.core.gateways.PaymentGateway
import br.com.fiap.ordermanagement.payment.core.presenters.PaymentPresenter
import br.com.fiap.ordermanagement.payment.core.usecases.CreateQRCodeUseCase
import br.com.fiap.ordermanagement.payment.core.usecases.FindPaymentUseCase
import br.com.fiap.ordermanagement.payment.core.usecases.ProcessWebhookUseCase
import java.util.*

class PaymentController(
    dataSource: DataSource,
    qrCodeClient: PaymentProviderClient
) {
    private val paymentRepositoryGateway: IPaymentGateway = PaymentGateway(dataSource, qrCodeClient)

    fun createQRCode(orderId: UUID): PaymentDTO {
        val createQRCode = CreateQRCodeUseCase(paymentRepositoryGateway)

        val createdPayment = createQRCode.execute(orderId)

        return PaymentPresenter.paymentToDto(createdPayment)
    }

    fun getPaymentDetails(id: UUID): PaymentDTO {
        val getPaymentDetails = FindPaymentUseCase(paymentRepositoryGateway)

        val payment = getPaymentDetails.execute(id)

        return PaymentPresenter.paymentToDto(payment)
    }

    fun processWebhook(webhookDTO: WebhookDTO) {
        val processWebhook = ProcessWebhookUseCase(paymentRepositoryGateway)

        processWebhook.execute(webhookDTO.data.id, webhookDTO.data.status.toEntityStatus())
    }
}
