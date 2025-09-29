package br.com.fiap.ordermanagement.product.core.usercases

import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import br.com.fiap.ordermanagement.product.core.gateways.IProductGateway
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*

class FindProductByIdUseCaseTest {

    private var productGateway: IProductGateway = mockk(relaxed = false)
    private var findProductByIdUseCase: FindProductByIdUseCase = FindProductByIdUseCase(productGateway)

    @Test
    fun `should find product by id successfully`() {
        val product = ProductEntity(
            id = UUID.randomUUID(),
            name = "Burger",
            description = "Delicious burger",
            category = CategoryEnum.BURGUER,
            price = BigDecimal("25.90"),
            amount = 10
        )

        every { productGateway.findProductById(any()) } returns product

        val result = findProductByIdUseCase.execute(product.id)

        assertNotNull(result)

        verify(exactly = 1) { productGateway.findProductById(any()) }
    }

    @Test
    fun `should throw exception when product not found`() {
        every { productGateway.findProductById(any()) } throws NoSuchElementException("Product not found")

        assertThrows<NoSuchElementException> {
            findProductByIdUseCase.execute(UUID.randomUUID())
        }

        verify(exactly = 1) { productGateway.findProductById(any()) }
    }
}
