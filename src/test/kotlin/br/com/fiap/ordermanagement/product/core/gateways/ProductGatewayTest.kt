package br.com.fiap.ordermanagement.product.core.gateways

import br.com.fiap.ordermanagement.product.common.dtos.ProductDTO
import br.com.fiap.ordermanagement.product.common.interfaces.DataSource
import br.com.fiap.ordermanagement.product.common.mapper.ProductMapper.toDTO
import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class ProductGatewayTest {

    private var dataSource: DataSource = mockk(relaxed = false)
    private var productGateway: ProductGateway = ProductGateway(dataSource)

    private val productId = UUID.randomUUID()

    @Test
    fun `should save product successfully`() {
        val productEntity = ProductEntity(
            id = productId,
            name = "Juice",
            description = "description",
            category = CategoryEnum.BURGUER,
            price = BigDecimal.TEN,
            amount = 1
        )

        every { dataSource.saveProduct(any()) } returns productEntity.toDTO()

        val result = productGateway.save(productEntity)

        assertEquals(productId, result.id)

        verify(exactly = 1) {
            dataSource.saveProduct(any())
        }
    }

    @Test
    fun `should get all products`() {
        val productDtos = listOf(
            ProductDTO(
                id = UUID.randomUUID(),
                name = "Juice",
                description = "description",
                category = CategoryEnum.BURGUER,
                price = BigDecimal.TEN,
                amount = 1
            ),
            ProductDTO(
                id = UUID.randomUUID(),
                name = "Juice",
                description = "description",
                category = CategoryEnum.BURGUER,
                price = BigDecimal.TEN,
                amount = 1
            )
        )

        every { dataSource.getAllProducts() } returns productDtos

        val result = productGateway.findAllProducts()

        assertEquals(2, result.size)

        verify(exactly = 1) { dataSource.getAllProducts() }
    }

    @Test
    fun `should return empty list when no products found`() {
        every { dataSource.getAllProducts() } returns emptyList()

        val result = productGateway.findAllProducts()

        assertTrue(result.isEmpty())
        verify(exactly = 1) { dataSource.getAllProducts() }
    }

    @Test
    fun `should get product by id`() {
        val productDto = ProductDTO(
            id = productId,
            name = "Juice",
            description = "description",
            category = CategoryEnum.BURGUER,
            price = BigDecimal.TEN,
            amount = 1
        )

        every { dataSource.getProductById(productId) } returns productDto

        val result = productGateway.findProductById(productId)

        assertEquals(productId, result!!.id)

        verify(exactly = 1) { dataSource.getProductById(productId) }
    }

    @Test
    fun `should return null product not found by id`() {
        every { dataSource.getProductById(productId) } returns null

        val result = productGateway.findProductById(productId)

        assertThat(result).isEqualTo(null)

        verify(exactly = 1) { dataSource.getProductById(productId) }
    }

    @Test
    fun `should update product successfully`() {
        val productId = UUID.randomUUID()
        val productEntity = ProductEntity(
            id = productId,
            name = "UpdatedName",
            description = "description",
            category = CategoryEnum.BURGUER,
            price = BigDecimal.TEN,
            amount = 1
        )

        every { dataSource.saveProduct(any()) } returns productEntity.toDTO()

        val result = productGateway.update(productEntity)

        // Then
        assertEquals(productId, result.id)
        assertEquals(productEntity.name, result.name)

        verify(exactly = 1) {
            dataSource.saveProduct(any())
        }
    }

    @Test
    fun `should delete product by id`() {
        every { dataSource.deleteById(productId) } just Runs

        productGateway.deleteById(productId)

        verify(exactly = 1) { dataSource.deleteById(productId) }
    }

    @Test
    fun `should find all products by category`() {
        val category = CategoryEnum.DRINK
        val products = listOf(
            ProductDTO(
                id = UUID.randomUUID(),
                name = "Juice",
                description = "description",
                category = category,
                price = BigDecimal.TEN,
                amount = 1
            )
        )

        every { dataSource.getAllProductsByCategory(category) } returns products

        val result = productGateway.findAllByCategory(category)

        assertEquals(1, result.size)
        assertTrue(result.all { it.category == category })

        verify(exactly = 1) { dataSource.getAllProductsByCategory(category) }
    }

    @Test
    fun `should return empty list when no products found by category`() {
        val category = CategoryEnum.DESSERT
        every { dataSource.getAllProductsByCategory(category) } returns emptyList()

        val result = productGateway.findAllByCategory(category)

        assertTrue(result.isEmpty())
        verify(exactly = 1) { dataSource.getAllProductsByCategory(category) }
    }
}
