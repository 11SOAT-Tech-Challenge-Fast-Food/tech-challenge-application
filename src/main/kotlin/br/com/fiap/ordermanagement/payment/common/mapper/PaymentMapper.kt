package br.com.fiap.ordermanagement.payment.common.mapper

import br.com.fiap.ordermanagement.order.common.mapper.OrderMapper.toDto
import br.com.fiap.ordermanagement.order.common.mapper.OrderMapper.toEntity
import br.com.fiap.ordermanagement.order.common.mapper.OrderMapper.toJpaOrder
import br.com.fiap.ordermanagement.payment.common.dtos.PaymentDTO
import br.com.fiap.ordermanagement.payment.core.entities.PaymentEntity
import br.com.fiap.ordermanagement.payment.external.persistence.jpa.JpaPayment
import java.math.BigDecimal

object PaymentMapper {
    fun PaymentDTO.toEntity(value: BigDecimal = 0.toBigDecimal()) = PaymentEntity(
        id = this.id,
        externalId = this.externalId,
        status = this.status,
        order = this.order!!.toEntity(),
        qrCode = this.qrCode,
        value = value,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )

    fun PaymentEntity.toDto() = PaymentDTO(
        id = this.id,
        externalId = this.externalId!!,
        status = this.status,
        order = this.order?.toDto(),
        qrCode = this.qrCode,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )

    fun JpaPayment.toDto() = PaymentDTO(
        id = this.id,
        externalId = this.externalId,
        status = this.status,
        order = this.order.toDto(),
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )

    fun PaymentDTO.toJpaPayment() = JpaPayment(
        id = this.id,
        externalId = this.externalId!!,
        status = this.status,
        order = this.order!!.toJpaOrder(),
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
