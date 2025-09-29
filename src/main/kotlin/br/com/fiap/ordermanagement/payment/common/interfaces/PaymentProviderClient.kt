package br.com.fiap.ordermanagement.payment.common.interfaces

import br.com.fiap.ordermanagement.payment.common.dtos.QRCodeDTO
import java.math.BigDecimal
import java.util.*

interface PaymentProviderClient {
    fun generateQRCode(amount: BigDecimal, orderId: UUID): QRCodeDTO
}
