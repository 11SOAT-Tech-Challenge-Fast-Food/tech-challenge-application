package br.com.fiap.ordermanagement.product.core.usercases

import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import br.com.fiap.ordermanagement.product.core.gateways.IProductGateway
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*

class UpdateProductUseCaseTest {

    private var productGateway: IProductGateway = mockk(relaxed = false)
    private var updateProductUseCase: UpdateProductUseCase = UpdateProductUseCase(productGateway)

    @Test
    fun `should update product successfully`() {
        val productId = UUID.randomUUID()
        val existingProduct = ProductEntity(
            id = productId,
            name = "Burger",
            description = "Delicious burger",
            category = CategoryEnum.BURGUER,
            price = BigDecimal("25.90"),
            amount = 10
        )

        val updatedProduct = ProductEntity(
            id = productId,
            name = "New Name",
            description = "Delicious burger",
            category = CategoryEnum.BURGUER,
            price = BigDecimal("25.90"),
            amount = 10
        )

        every { productGateway.findProductById(productId) } returns existingProduct
        every { productGateway.update(any()) } returns updatedProduct.copy()

        val result = updateProductUseCase.execute(updatedProduct)

        assertEquals("New Name", result.name)

        verify(exactly = 1) { productGateway.findProductById(any()) }
        verify(exactly = 1) {
            productGateway.update(any())
        }
    }

    @Test
    fun `should throw exception when product not found`() {
        val productId = UUID.randomUUID()
        val nonExistentProduct = ProductEntity(
            id = productId,
            name = "Non-existent",
            category = CategoryEnum.DRINK,
            price = BigDecimal("10.00"),
            amount = 1,
            description = "description"
        )

        every { productGateway.findProductById(any()) } throws NoSuchElementException("Product not found")

        assertThrows<NoSuchElementException> {
            updateProductUseCase.execute(nonExistentProduct)
        }

        verify(exactly = 1) { productGateway.findProductById(any()) }
        verify(exactly = 0) { productGateway.update(any()) }
    }
}
