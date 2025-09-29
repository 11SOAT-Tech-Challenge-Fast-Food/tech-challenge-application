package br.com.fiap.ordermanagement.customer.external.persistence.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "customer")
data class JpaCustomer(
    @Id
    @Column(name = "id", length = 26)
    val id: UUID? = UUID.randomUUID(),

    @Column(name = "name")
    val name: String,

    @Column(name = "email")
    val email: String?,

    @Column(name = "cpf")
    val cpf: String?,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
