package br.com.fiap.ordermanagement.payment.external.persistence.jpa

import br.com.fiap.ordermanagement.order.external.persistence.jpa.JpaOrder
import br.com.fiap.ordermanagement.payment.core.entities.enums.PaymentStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "payments")
data class JpaPayment(
    @Id
    @Column(name = "id")
    val id: UUID,

    @Column(name = "external_id")
    val externalId: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: PaymentStatus,

    @JoinColumn(name = "order_id")
    @ManyToOne
    val order: JpaOrder,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
