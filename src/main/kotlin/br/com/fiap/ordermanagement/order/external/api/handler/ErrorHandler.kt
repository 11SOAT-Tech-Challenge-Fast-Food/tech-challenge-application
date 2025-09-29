package br.com.fiap.ordermanagement.order.external.api.handler

import br.com.fiap.ordermanagement.customer.core.gateways.exception.InvalidCustomerException
import br.com.fiap.ordermanagement.customer.core.gateways.exception.NotFoundCustomerException
import br.com.fiap.ordermanagement.order.core.gateways.exception.InvalidCategoryException
import br.com.fiap.ordermanagement.order.core.gateways.exception.InvalidUpdateException
import br.com.fiap.ordermanagement.product.core.gateways.exception.NotFoundProductException
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException

@RestControllerAdvice
class ErrorHandler {
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ExceptionResponse> {
        return buildExceptionResponse(
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
            message = ex.localizedMessage,
            errorCode = "INTERNAL_SERVER_ERROR"
        )
    }

    @ExceptionHandler(HttpClientErrorException.UnprocessableEntity::class)
    fun handleUnprocessableEntity(ex: Exception): ResponseEntity<ExceptionResponse> {
        return buildExceptionResponse(
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
            message = ex.localizedMessage,
            errorCode = "UNPROCESSABLE_ENTITY"
        )
    }

    @ExceptionHandler(HttpClientErrorException.NotFound::class)
    fun handleNotFoundException(ex: ChangeSetPersister.NotFoundException): ResponseEntity<ExceptionResponse> {
        return buildExceptionResponse(
            httpStatus = HttpStatus.NOT_FOUND,
            message = ex.localizedMessage,
            errorCode = "NOT_FOUND"
        )
    }

    @ExceptionHandler(NotFoundProductException::class)
    fun handleNotFoundInternalException(ex: NotFoundProductException): ResponseEntity<ExceptionResponse> {
        return buildExceptionResponse(
            httpStatus = HttpStatus.NOT_FOUND,
            message = ex.localizedMessage,
            errorCode = "NOT_FOUND"
        )
    }

    @ExceptionHandler(InvalidCategoryException::class)
    fun handleInvalidCategoryException(ex: InvalidCategoryException): ResponseEntity<ExceptionResponse> {
        return buildExceptionResponse(
            httpStatus = HttpStatus.BAD_REQUEST,
            message = ex.localizedMessage,
            errorCode = "INVALID_CATEGORY"
        )
    }

    @ExceptionHandler(InvalidUpdateException::class)
    fun handleInvalidUpdateException(ex: InvalidUpdateException): ResponseEntity<ExceptionResponse> {
        return buildExceptionResponse(
            httpStatus = HttpStatus.BAD_REQUEST,
            message = ex.localizedMessage,
            errorCode = "BAD_REQUEST"
        )
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest::class)
    fun handleBadRequestException(ex: HttpClientErrorException.BadRequest): ResponseEntity<ExceptionResponse> {
        return buildExceptionResponse(
            httpStatus = HttpStatus.BAD_REQUEST,
            message = ex.localizedMessage,
            errorCode = "BAD_REQUEST"
        )
    }

    @ExceptionHandler(InvalidCustomerException::class)
    fun handleInvalidCreateException(ex: InvalidCustomerException): ResponseEntity<ExceptionResponse> {
        return buildExceptionResponse(
            httpStatus = HttpStatus.UNPROCESSABLE_ENTITY,
            message = ex.localizedMessage,
            errorCode = "UNPROCESSABLE_ENTITY"
        )
    }

    @ExceptionHandler(NotFoundCustomerException::class)
    fun handleNotFoundCustomerException(ex: NotFoundCustomerException): ResponseEntity<ExceptionResponse> {
        return buildExceptionResponse(
            httpStatus = HttpStatus.NOT_FOUND,
            message = ex.localizedMessage,
            errorCode = "NOT_FOUND"
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFoundCustomerException(ex: IllegalArgumentException): ResponseEntity<ExceptionResponse> {
        return buildExceptionResponse(
            httpStatus = HttpStatus.BAD_REQUEST,
            message = ex.localizedMessage,
            errorCode = "BAD_REQUEST"
        )
    }

    private fun buildExceptionResponse(
        httpStatus: HttpStatus,
        message: String,
        errorCode: String? = null
    ): ResponseEntity<ExceptionResponse> {
        val exceptionResponse = ExceptionResponse(
            httpStatus.value(),
            message,
            errorCode
        )
        return ResponseEntity.status(httpStatus).body(exceptionResponse)
    }
}
