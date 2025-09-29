package br.com.fiap.ordermanagement.product.core.usercases

import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import br.com.fiap.ordermanagement.product.core.gateways.IProductGateway
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class FindAllProductsUseCaseTest {

    private var productGateway: IProductGateway = mockk(relaxed = false)
    private var findAllProductsUseCase: FindAllProductsUseCase = FindAllProductsUseCase(productGateway)

    @Test
    fun `should return all products`() {
        val products = listOf(
            ProductEntity(
                id = UUID.randomUUID(),
                name = "Burger",
                description = "Delicious burger",
                category = CategoryEnum.BURGUER,
                price = BigDecimal("25.90"),
                amount = 10
            ),
            ProductEntity(
                id = UUID.randomUUID(),
                name = "Pizza",
                description = "Tasty pizza",
                category = CategoryEnum.DRINK,
                price = BigDecimal("45.90"),
                amount = 8
            )
        )

        every { productGateway.findAllProducts() } returns products

        val result = findAllProductsUseCase.execute()

        assertEquals(2, result.size)

        verify(exactly = 1) { productGateway.findAllProducts() }
    }

    @Test
    fun `should return empty list when no products exist`() {
        every { productGateway.findAllProducts() } returns emptyList()

        val result = findAllProductsUseCase.execute()

        assertTrue(result.isEmpty())

        verify(exactly = 1) { productGateway.findAllProducts() }
    }
}
