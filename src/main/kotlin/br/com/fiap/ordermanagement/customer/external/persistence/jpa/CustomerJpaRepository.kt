package br.com.fiap.ordermanagement.customer.external.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerJpaRepository : JpaRepository<JpaCustomer, UUID> {
    fun findByCpf(cpf: String): JpaCustomer?
    fun findByEmail(email: String): JpaCustomer?
}
