package br.com.fiap.ordermanagement.product.core.entities

import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class ProductEntityTest {

    @Test
    fun `should create product with all fields`() {
        val id = UUID.randomUUID()
        val name = "Test Product"
        val description = "Test Description"
        val category = CategoryEnum.BURGUER
        val price = BigDecimal("19.99")
        val amount = 10

        val product = ProductEntity(
            id = id,
            name = name,
            description = description,
            category = category,
            price = price,
            amount = amount
        )

        assertEquals(id, product.id)
        assertEquals(name, product.name)
        assertEquals(description, product.description)
        assertEquals(category, product.category)
        assertEquals(price, product.price)
        assertEquals(amount, product.amount)
    }

    @Test
    fun `should create product with required fields only`() {
        val id = UUID.randomUUID()
        val name = "Test Product"
        val category = CategoryEnum.DRINK
        val price = BigDecimal("5.99")
        val amount = 1

        val product = ProductEntity(
            id = id,
            name = name,
            description = null,
            category = category,
            price = price,
            amount = amount
        )

        assertEquals(id, product.id)
        assertEquals(name, product.name)
        assertNull(product.description)
        assertEquals(category, product.category)
        assertEquals(price, product.price)
        assertEquals(amount, product.amount)
    }
}
