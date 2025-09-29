package br.com.fiap.ordermanagement.product.external.api.controller

import br.com.fiap.ordermanagement.product.common.dtos.ProductDTO
import br.com.fiap.ordermanagement.product.common.interfaces.DataSource
import br.com.fiap.ordermanagement.product.core.controllers.ProductController
import br.com.fiap.ordermanagement.product.core.gateways.exception.NotFoundProductException
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
@RequestMapping("/product")
class ProductApiController(
    dataSource: DataSource
) {

    private val productController = ProductController(dataSource)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new product")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Product created successfully",
                content = [Content(schema = Schema(implementation = ProductDTO::class))]
            ), ApiResponse(
                responseCode = "404",
                description = "Product not found",
                content = [Content(schema = Schema(implementation = NotFoundProductException::class))]
            )
        ]
    )
    fun createProduct(@RequestBody productDTO: ProductDTO): ResponseEntity<ProductDTO> {
        val newProduct = productController.createProduct(productDTO)

        return ResponseEntity.created(URI("/product")).body(newProduct)
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update a product")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Product updated successfully",
                content = [Content(schema = Schema(implementation = ProductDTO::class))]
            ), ApiResponse(
                responseCode = "404",
                description = "Product not found",
                content = [Content(schema = Schema(implementation = NotFoundProductException::class))]
            )
        ]
    )
    fun updateProduct(@RequestBody productDTO: ProductDTO): ResponseEntity<ProductDTO> {
        val productUpdated = productController.updateProduct(productDTO)

        return ResponseEntity.ok().body(productUpdated)
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get all products by category")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Products found",
                content = [Content(array = ArraySchema(schema = Schema(implementation = ProductDTO::class)))]
            )
        ]
    )
    fun getAllProductsByCategory(@PathVariable category: String): ResponseEntity<List<ProductDTO>> {
        return ResponseEntity.ok().body(productController.findAllProductsByCategory(category))
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Product found",
                content = [Content(array = ArraySchema(schema = Schema(implementation = ProductDTO::class)))]
            ), ApiResponse(
                responseCode = "404",
                description = "Product not found",
                content = [Content(schema = Schema(implementation = NotFoundProductException::class))]
            )
        ]
    )
    fun getProductById(@PathVariable id: UUID): ResponseEntity<ProductDTO> {
        return ResponseEntity.ok().body(productController.findProductById(id))
    }

    @GetMapping
    @Operation(summary = "Get all products")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Products found",
                content = [Content(array = ArraySchema(schema = Schema(implementation = ProductDTO::class)))]
            )
        ]
    )
    fun getAllProducts(): ResponseEntity<List<ProductDTO>> {
        return ResponseEntity.ok().body(productController.findAllProducts())
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a product by id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Product deleted successfully",
                content = [Content(schema = Schema(hidden = true))]
            ), ApiResponse(
                responseCode = "404",
                description = "Product not found",
                content = [Content(schema = Schema(implementation = NotFoundProductException::class))]
            )
        ]
    )
    fun deleteByProductId(@PathVariable id: UUID): ResponseEntity<Unit> {
        productController.deleteProductById(id)
        return ResponseEntity.ok().build()
    }
}
