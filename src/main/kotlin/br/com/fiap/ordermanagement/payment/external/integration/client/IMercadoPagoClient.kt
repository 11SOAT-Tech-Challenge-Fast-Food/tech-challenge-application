package br.com.fiap.ordermanagement.payment.external.integration.client

import br.com.fiap.ordermanagement.payment.external.config.FeignConfig
import br.com.fiap.ordermanagement.payment.external.integration.dtos.QRCodeRequestDTO
import br.com.fiap.ordermanagement.payment.external.integration.dtos.QRCodeResponseDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "mercado-pago-client", url = "\${mercado-pago.url}", configuration = [FeignConfig::class])
interface IMercadoPagoClient {
    @PostMapping("/v1/orders")
    fun generateQRCode(@RequestBody createQRCodeDTO: QRCodeRequestDTO, @RequestHeader("X-Idempotency-Key") idempotencyKey: String): QRCodeResponseDTO
}
