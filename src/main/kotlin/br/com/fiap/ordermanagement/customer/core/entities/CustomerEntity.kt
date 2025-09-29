package br.com.fiap.ordermanagement.customer.core.entities

import java.time.LocalDateTime
import java.util.UUID

data class CustomerEntity(
    val id: UUID?,
    val name: String,
    val email: String?,
    val cpf: String?,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
