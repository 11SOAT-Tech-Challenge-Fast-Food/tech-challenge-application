package br.com.fiap.ordermanagement.order.external.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderItemJpaRepository : JpaRepository<JpaOrderItem, UUID>
