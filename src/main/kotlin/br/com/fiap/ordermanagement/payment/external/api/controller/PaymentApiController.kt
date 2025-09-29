package br.com.fiap.ordermanagement.payment.external.api.controller

import br.com.fiap.ordermanagement.order.external.api.handler.ExceptionResponse
import br.com.fiap.ordermanagement.payment.common.dtos.CreateQRCodeDTO
import br.com.fiap.ordermanagement.payment.common.dtos.PaymentDTO
import br.com.fiap.ordermanagement.payment.common.dtos.WebhookDTO
import br.com.fiap.ordermanagement.payment.common.interfaces.DataSource
import br.com.fiap.ordermanagement.payment.common.interfaces.PaymentProviderClient
import br.com.fiap.ordermanagement.payment.core.controllers.PaymentController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/payment")
class PaymentApiController(
    dataSource: DataSource,
    qrCodeClient: PaymentProviderClient
) {
    private val paymentController = PaymentController(dataSource, qrCodeClient)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create QrCode")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "QRCode created",
                content = [Content(schema = Schema(implementation = PaymentDTO::class))]
            )
        ]
    )
    fun createQRCode(@RequestBody createQRCodeDTO: CreateQRCodeDTO): ResponseEntity<PaymentDTO> {
        val createdQRCode = paymentController.createQRCode(createQRCodeDTO.orderId)

        return ResponseEntity.created(URI.create("/payment")).body(createdQRCode)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get payment details by id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Payment found",
                content = [Content(schema = Schema(implementation = PaymentDTO::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Payment not found",
                content = [Content(schema = Schema(implementation = ExceptionResponse::class))]
            )
        ]
    )
    fun getPaymentDetails(@PathVariable id: UUID): ResponseEntity<PaymentDTO> {
        val payment = paymentController.getPaymentDetails(id)

        return ResponseEntity.ok().body(payment)
    }

    @PostMapping("/webhook")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create QrCode")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Webhook received",
                content = [Content(schema = Schema(implementation = WebhookDTO::class))]
            )
        ]
    )
    fun processWebhook(@RequestBody webhookDTO: WebhookDTO): ResponseEntity<Unit> {
        paymentController.processWebhook(webhookDTO)

        return ResponseEntity.ok().build()
    }
}
