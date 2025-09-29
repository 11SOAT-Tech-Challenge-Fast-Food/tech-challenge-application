package br.com.fiap.ordermanagement.order.external.persistence.jpa

import br.com.fiap.ordermanagement.product.external.persistence.jpa.JpaProduct
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "order_item")
data class JpaOrderItem(
    @Id
    @Column(name = "id")
    val id: UUID,

    @JoinColumn(name = "product_id")
    @ManyToOne
    val product: JpaProduct,

    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val order: JpaOrder
)
