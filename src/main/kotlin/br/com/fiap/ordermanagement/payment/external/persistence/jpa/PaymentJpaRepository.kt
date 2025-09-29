package br.com.fiap.ordermanagement.payment.external.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PaymentJpaRepository : JpaRepository<JpaPayment, UUID> {
    fun findByExternalId(externalId: String): Optional<JpaPayment>
}
