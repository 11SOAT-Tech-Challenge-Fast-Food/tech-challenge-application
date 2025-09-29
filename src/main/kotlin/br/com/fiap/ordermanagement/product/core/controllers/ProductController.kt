package br.com.fiap.ordermanagement.product.core.controllers

import br.com.fiap.ordermanagement.product.common.dtos.ProductDTO
import br.com.fiap.ordermanagement.product.common.interfaces.DataSource
import br.com.fiap.ordermanagement.product.common.mapper.ProductMapper.toEntity
import br.com.fiap.ordermanagement.product.core.gateways.ProductGateway
import br.com.fiap.ordermanagement.product.core.presenters.ProductPresenter
import br.com.fiap.ordermanagement.product.core.usercases.CreateProductUseCase
import br.com.fiap.ordermanagement.product.core.usercases.DeleteProductUseCase
import br.com.fiap.ordermanagement.product.core.usercases.FindAllProductsByCategoryUseCase
import br.com.fiap.ordermanagement.product.core.usercases.FindAllProductsUseCase
import br.com.fiap.ordermanagement.product.core.usercases.FindProductByIdUseCase
import br.com.fiap.ordermanagement.product.core.usercases.UpdateProductUseCase
import java.util.*

class ProductController(
    dataSource: DataSource
) {
    private val productRepositoryGateway = ProductGateway(dataSource)

    fun createProduct(productDTO: ProductDTO): ProductDTO {
        val createProductUseCase = CreateProductUseCase(productRepositoryGateway)

        val createProduct = createProductUseCase.execute(productDTO.toEntity())

        return ProductPresenter.productToDto(createProduct)
    }

    fun updateProduct(productDTO: ProductDTO): ProductDTO {
        val updateProductUseCase = UpdateProductUseCase(productRepositoryGateway)

        val updateProduct = updateProductUseCase.execute(productDTO.toEntity())

        return ProductPresenter.productToDto(updateProduct)
    }

    fun deleteProductById(id: UUID) {
        val deleteProductUseCase = DeleteProductUseCase(productRepositoryGateway)

        deleteProductUseCase.execute(id)
    }

    fun findAllProducts(): List<ProductDTO> {
        val findProductByIdUseCase = FindAllProductsUseCase(productRepositoryGateway)

        val findAllProducts = findProductByIdUseCase.execute()

        return findAllProducts.map { ProductPresenter.productToDto(it) }
    }

    fun findAllProductsByCategory(category: String): List<ProductDTO> {
        val findProductByIdUseCase = FindAllProductsByCategoryUseCase(productRepositoryGateway)

        val findAllProducts = findProductByIdUseCase.execute(category)

        return findAllProducts.map { ProductPresenter.productToDto(it) }
    }

    fun findProductById(id: UUID): ProductDTO {
        val findProductByIdUseCase = FindProductByIdUseCase(productRepositoryGateway)

        val findProduct = findProductByIdUseCase.execute(id)

        return ProductPresenter.productToDto(findProduct)
    }
}
