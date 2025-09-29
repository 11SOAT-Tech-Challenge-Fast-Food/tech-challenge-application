package br.com.fiap.ordermanagement.customer.external.api.controller

import br.com.fiap.ordermanagement.customer.common.dtos.CustomerDTO
import br.com.fiap.ordermanagement.customer.core.controllers.CustomerController
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
import java.util.UUID

@RestController
@RequestMapping("/customer")
class CustomerApiController(
    private val customerController: CustomerController
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all customers")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Customers found",
                content = [Content(array = ArraySchema(schema = Schema(implementation = CustomerDTO::class)))]
            ),
            ApiResponse(
                responseCode = "204",
                description = "No customer found",
                content = [Content(schema = Schema(hidden = true))]
            )
        ]
    )
    fun getAllCustomers(): ResponseEntity<List<CustomerDTO>> {
        val customers = customerController.findAllCustomers()

        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build()
        }

        return ResponseEntity.ok().body(customers)
    }

    @PostMapping
    @Operation(summary = "Create new customer")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Customer created",
                content = [Content(schema = Schema(implementation = CustomerDTO::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid request",
                content = [Content(schema = Schema(hidden = true))]
            )
        ]
    )
    fun createCustomer(@RequestBody customerDTO: CustomerDTO): ResponseEntity<CustomerDTO> {
        val customer = customerController.createCustomer(customerDTO)
        return ResponseEntity.created(URI.create("/customer/${customer.id}")).body(customer)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Customer found",
                content = [Content(schema = Schema(implementation = CustomerDTO::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Customer not found",
                content = [Content(schema = Schema(hidden = true))]
            )
        ]
    )
    fun getCustomerById(@PathVariable id: UUID): ResponseEntity<CustomerDTO> {
        val customer = customerController.findCustomerById(id)
        return ResponseEntity.ok().body(customer)
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Get customer by CPF")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Customer found",
                content = [Content(schema = Schema(implementation = CustomerDTO::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Customer not found",
                content = [Content(schema = Schema(hidden = true))]
            )
        ]
    )
    fun getCustomerByCpf(@PathVariable cpf: String): ResponseEntity<CustomerDTO> {
        val customer = customerController.findCustomerByCpf(cpf)
        return ResponseEntity.ok().body(customer)
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get customer by email")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Customer found",
                content = [Content(schema = Schema(implementation = CustomerDTO::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Customer not found",
                content = [Content(schema = Schema(hidden = true))]
            )
        ]
    )
    fun getCustomerByEmail(@PathVariable email: String): ResponseEntity<CustomerDTO> {
        val customer = customerController.findCustomerByEmail(email)
        return ResponseEntity.ok().body(customer)
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update customer")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Customer updated",
                content = [Content(schema = Schema(implementation = CustomerDTO::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Customer not found",
                content = [Content(schema = Schema(hidden = true))]
            )
        ]
    )
    fun updateCustomer(
        @PathVariable id: UUID,
        @RequestBody customerDTO: CustomerDTO
    ): ResponseEntity<CustomerDTO> {
        val customerUpdated = customerController.updateCustomer(id, customerDTO)
        return ResponseEntity.ok().body(customerUpdated)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete customer")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Customer deleted"
            ),
            ApiResponse(
                responseCode = "404",
                description = "Customer not found",
                content = [Content(schema = Schema(hidden = true))]
            )
        ]
    )
    fun deleteCustomer(@PathVariable id: UUID): ResponseEntity<Unit> {
        customerController.deleteCustomer(id)

        return ResponseEntity.ok().build()
    }
}
