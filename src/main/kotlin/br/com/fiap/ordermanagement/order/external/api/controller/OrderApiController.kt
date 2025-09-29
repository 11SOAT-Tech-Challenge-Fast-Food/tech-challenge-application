package br.com.fiap.ordermanagement.order.external.api.controller

import br.com.fiap.ordermanagement.order.common.dtos.CreateOrderDTO
import br.com.fiap.ordermanagement.order.common.dtos.OrderDTO
import br.com.fiap.ordermanagement.order.common.dtos.UpdateOrderDTO
import br.com.fiap.ordermanagement.order.common.interfaces.DataSource
import br.com.fiap.ordermanagement.order.core.controllers.OrderController
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.order.external.api.handler.ExceptionResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/order")
class OrderApiController(
    dataSource: DataSource
) {
    private val orderController = OrderController(dataSource)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create order")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Order created",
                content = [Content(schema = Schema(implementation = OrderDTO::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Customer not found",
                content = [Content(schema = Schema(implementation = ExceptionResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Product out of stock",
                content = [Content(schema = Schema(implementation = ExceptionResponse::class))]
            )
        ]
    )
    fun createOrder(@RequestBody orderRequest: CreateOrderDTO): ResponseEntity<OrderDTO> {
        val createdOrder = orderController.createOrder(orderRequest.customerId, orderRequest.products)
        return ResponseEntity.created(URI.create("/order")).body(createdOrder)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all orders")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Orders found",
                content = [Content(array = ArraySchema(schema = Schema(implementation = OrderDTO::class)))]
            ),
            ApiResponse(
                responseCode = "204",
                description = "No order found",
                content = [Content(schema = Schema(hidden = true))]
            )
        ]
    )
    fun getAllOrders(): ResponseEntity<List<OrderDTO>> {
        val orders = orderController.getAllOrders()

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        return ResponseEntity.ok().body(orders)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get order details by id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Order found",
                content = [Content(schema = Schema(implementation = OrderDTO::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Order not found",
                content = [Content(schema = Schema(implementation = ExceptionResponse::class))]
            )
        ]
    )
    fun getOrderById(@PathVariable id: UUID): ResponseEntity<OrderDTO> {
        return ResponseEntity.ok().body(orderController.getOrderById(id))
    }

    @GetMapping("/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all orders by status")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Orders found",
                content = [Content(array = ArraySchema(schema = Schema(implementation = OrderDTO::class)))]
            ),
            ApiResponse(
                responseCode = "204",
                description = "No order found",
                content = [Content(schema = Schema(hidden = true))]
            )
        ]
    )
    fun getOrdersByStatus(@PathVariable status: OrderStatus): ResponseEntity<List<OrderDTO>> {
        val orders = orderController.getOrdersByStatus(status)

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        return ResponseEntity.ok().body(orders)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Cancel order")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Order canceled",
                content = [Content(schema = Schema(hidden = true))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Order not found",
                content = [Content(schema = Schema(implementation = ExceptionResponse::class))]
            )
        ]
    )
    fun cancel(@PathVariable id: UUID): ResponseEntity<Unit> {
        orderController.cancel(id)

        return ResponseEntity.ok().build()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update order status")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Order updated",
                content = [Content(schema = Schema(hidden = true))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Order not found",
                content = [Content(schema = Schema(implementation = ExceptionResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Order is in incorrect status, for status requested",
                content = [Content(schema = Schema(implementation = ExceptionResponse::class))]
            )
        ]
    )
    fun updateOrder(@PathVariable id: UUID, @RequestBody updateOrderRequest: UpdateOrderDTO): ResponseEntity<Unit> {
        orderController.updateOrder(id, updateOrderRequest.status)

        return ResponseEntity.ok().build()
    }
}
