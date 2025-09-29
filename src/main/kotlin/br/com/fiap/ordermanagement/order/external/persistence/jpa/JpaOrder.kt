package br.com.fiap.ordermanagement.order.external.persistence.jpa

import br.com.fiap.ordermanagement.customer.external.persistence.jpa.JpaCustomer
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "orders")
data class JpaOrder(
    @Id
    @Column(name = "id")
    val id: UUID,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: OrderStatus,

    @Column(name = "items")
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var items: List<JpaOrderItem>,

    @JoinColumn(name = "customer_id")
    @ManyToOne
    val customer: JpaCustomer?,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
