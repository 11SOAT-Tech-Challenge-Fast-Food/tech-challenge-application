package br.com.fiap.ordermanagement.payment.external.integration.client

import br.com.fiap.ordermanagement.payment.common.dtos.QRCodeDTO
import br.com.fiap.ordermanagement.payment.common.interfaces.PaymentProviderClient
import br.com.fiap.ordermanagement.payment.external.integration.dtos.ConfigDTO
import br.com.fiap.ordermanagement.payment.external.integration.dtos.ConfigQRDTO
import br.com.fiap.ordermanagement.payment.external.integration.dtos.PaymentDTO
import br.com.fiap.ordermanagement.payment.external.integration.dtos.QRCodeRequestDTO
import br.com.fiap.ordermanagement.payment.external.integration.dtos.Transactions
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*

@Component
class PaymentProviderClientImpl(
    private val mercadoPagoClient: IMercadoPagoClient,
    @Value("\${mercado-pago.external-pos-id}") private val externalPosId: String
) : PaymentProviderClient {
    override fun generateQRCode(amount: BigDecimal, orderId: UUID): QRCodeDTO {
        val qrCodeResponse = mercadoPagoClient.generateQRCode(
            QRCodeRequestDTO(
                totalAmount = amount.toString(),
                description = "QRCode FastFood Order",
                externalReference = orderId.toString(),
                config = ConfigDTO(
                    qr = ConfigQRDTO(
                        externalPosId = externalPosId
                    )
                ),
                transactions = Transactions(
                    payments = listOf(
                        PaymentDTO(
                            amount = amount.toString()
                        )
                    )
                )
            ),
            UUID.randomUUID().toString()
        )

        return QRCodeDTO(
            externalId = qrCodeResponse.id,
            qrCode = qrCodeResponse.typeResponse.qrData
        )
    }
}
