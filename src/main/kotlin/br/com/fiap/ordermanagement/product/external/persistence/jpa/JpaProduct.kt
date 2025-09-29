package br.com.fiap.ordermanagement.product.external.persistence.jpa

import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "product")
data class JpaProduct(
    @Id
    @Column(name = "id")
    val id: UUID,

    @Column(name = "name")
    val name: String,

    @Column(name = "description")
    val description: String?,

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    val category: CategoryEnum,

    @Column(name = "price")
    val price: BigDecimal,

    @Column(name = "amount")
    val amount: Int
)
