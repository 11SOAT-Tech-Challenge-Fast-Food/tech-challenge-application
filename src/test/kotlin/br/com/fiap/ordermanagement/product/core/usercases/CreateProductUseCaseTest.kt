package br.com.fiap.ordermanagement.product.core.usercases

import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import br.com.fiap.ordermanagement.product.core.gateways.IProductGateway
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class CreateProductUseCaseTest {

    private var productGateway: IProductGateway = mockk(relaxed = false)
    private var createProductUseCase: CreateProductUseCase = CreateProductUseCase(productGateway)

    @Test
    fun `should create product successfully`() {
        val newProduct = ProductEntity(
            id = UUID.randomUUID(),
            name = "New Product",
            description = "Product description",
            category = CategoryEnum.BURGUER,
            price = BigDecimal("29.99"),
            amount = 15
        )

        val savedProduct = newProduct.copy(id = UUID.randomUUID())

        every { productGateway.save(newProduct) } returns savedProduct

        // When
        val result = createProductUseCase.execute(newProduct)

        assertNotNull(result.id)

        verify(exactly = 1) { productGateway.save(newProduct) }
    }
}
