package br.com.fiap.ordermanagement.product.core.usercases

import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import br.com.fiap.ordermanagement.product.core.gateways.IProductGateway
import br.com.fiap.ordermanagement.product.core.gateways.exception.NotFoundProductException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*

class DeleteProductUseCaseTest {

    private lateinit var productGateway: IProductGateway
    private lateinit var deleteProductUseCase: DeleteProductUseCase

    @BeforeEach
    fun setup() {
        productGateway = mockk(relaxed = false)
        deleteProductUseCase = DeleteProductUseCase(productGateway)
    }

    @Test
    fun `should delete product successfully`() {
        val productId = UUID.randomUUID()
        val productToDelete = ProductEntity(
            id = productId,
            name = "Product to Delete",
            description = "This product will be deleted",
            category = CategoryEnum.BURGUER,
            price = BigDecimal("19.99"),
            amount = 5
        )

        every { productGateway.findProductById(productId) } returns productToDelete
        every { productGateway.deleteById(productId) } returns Unit

        deleteProductUseCase.execute(productId)

        verify(exactly = 1) { productGateway.findProductById(productId) }
        verify(exactly = 1) { productGateway.deleteById(productId) }
    }

    @Test
    fun `should throw exception when product to delete not found`() {
        every { productGateway.findProductById(any()) } returns null

        assertThrows<NotFoundProductException> {
            deleteProductUseCase.execute(UUID.randomUUID())
        }

        verify(exactly = 1) { productGateway.findProductById(any()) }
        verify(exactly = 0) { productGateway.deleteById(any()) }
    }
}
