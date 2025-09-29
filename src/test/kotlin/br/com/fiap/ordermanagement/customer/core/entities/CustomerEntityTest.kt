package br.com.fiap.ordermanagement.customer.core.entities

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

class CustomerEntityTest {

    @Test
    fun `should create customer with all fields`() {
        val id = UUID.randomUUID()
        val name = "John Doe"
        val email = "john.doe@example.com"
        val cpf = "123.456.789-00"
        val now = LocalDateTime.now()

        val customer = CustomerEntity(
            id = id,
            name = name,
            email = email,
            cpf = cpf,
            createdAt = now,
            updatedAt = now
        )

        assertNotNull(customer)
        assertEquals(id, customer.id)
        assertEquals(name, customer.name)
        assertEquals(email, customer.email)
        assertEquals(cpf, customer.cpf)
        assertEquals(now, customer.createdAt)
        assertEquals(now, customer.updatedAt)
    }

    @Test
    fun `should create customer with required fields only`() {
        val name = "John Doe"

        val customer = CustomerEntity(
            id = null,
            name = name,
            email = null,
            cpf = null
        )

        assertNotNull(customer)
        assertNull(customer.id)
        assertEquals(name, customer.name)
        assertNull(customer.email)
        assertNull(customer.cpf)
        assertNotNull(customer.createdAt)
        assertNotNull(customer.updatedAt)
    }
}
