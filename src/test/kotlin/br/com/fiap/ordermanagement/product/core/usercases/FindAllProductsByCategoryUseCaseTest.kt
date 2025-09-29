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

class FindAllProductsByCategoryUseCaseTest {

    private var productGateway: IProductGateway = mockk(relaxed = false)
    private var findAllProductsByCategoryUseCase: FindAllProductsByCategoryUseCase = FindAllProductsByCategoryUseCase(productGateway)

    @Test
    fun `should find all products by category successfully`() {
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
                category = CategoryEnum.BURGUER,
                price = BigDecimal("45.90"),
                amount = 8
            )
        )

        every { productGateway.findAllByCategory(CategoryEnum.BURGUER) } returns products

        val result = findAllProductsByCategoryUseCase.execute("BURGUER")

        assertEquals(2, result.size)
        assertTrue(result.all { it.category == CategoryEnum.BURGUER })

        verify(exactly = 1) { productGateway.findAllByCategory(any()) }
    }

    @Test
    fun `should return empty list when no products found for category`() {
        val category = "DRINK"

        every { productGateway.findAllByCategory(CategoryEnum.DRINK) } returns emptyList()

        val result = findAllProductsByCategoryUseCase.execute(category)

        assertTrue(result.isEmpty())

        verify(exactly = 1) { productGateway.findAllByCategory(CategoryEnum.DRINK) }
    }
}
