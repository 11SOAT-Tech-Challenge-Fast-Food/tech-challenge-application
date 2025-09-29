package br.com.fiap.ordermanagement.order.external.persistence.jpa

import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderJpaRepository : JpaRepository<JpaOrder, UUID> {
    fun findAllByStatus(status: OrderStatus): List<JpaOrder>
}
