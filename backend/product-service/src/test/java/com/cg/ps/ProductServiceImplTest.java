package com.cg.ps;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.cg.ps.dto.ProductDto;
import com.cg.ps.entity.Product;
import com.cg.ps.repository.ProductRepo;
import com.cg.ps.service.ProductCategoryService;
import com.cg.ps.serviceimpl.ProductServiceImpl;

public class ProductServiceImplTest {

    @Mock
    private ProductRepo mockProductRepo;

    @Mock
    private ProductCategoryService mockProductCategoryService;

    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        // Arrange
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        when(mockProductRepo.findAll()).thenReturn(productList);
        when(mockModelMapper.map(productList, new TypeToken<List<ProductDto>>() {}.getType())).thenReturn(new ArrayList<>());

        // Act
        List<ProductDto> result = productService.get();

        // Assert
        assertEquals(0, result.size());
        verify(mockProductRepo).findAll();
        verify(mockModelMapper).map(productList, new TypeToken<List<ProductDto>>() {}.getType());
    }

    @Test
    public void testGetProductById() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        Optional<Product> optionalProduct = Optional.of(product);
        when(mockProductRepo.findById(productId)).thenReturn(optionalProduct);
        when(mockModelMapper.map(product, ProductDto.class)).thenReturn(new ProductDto());

        // Act
        ProductDto result = productService.get(productId);

        // Assert
        assertNotNull(result);
        verify(mockProductRepo).findById(productId);
        verify(mockModelMapper).map(product, ProductDto.class);
    }

    @Test
    public void testAddProduct() {
        // Arrange
        ProductDto productDto = new ProductDto();
        Product product = new Product();
        when(mockModelMapper.map(productDto, Product.class)).thenReturn(product);
        when(mockProductRepo.save(product)).thenReturn(product);
        when(mockModelMapper.map(product, ProductDto.class)).thenReturn(new ProductDto());

        // Act
        ProductDto result = productService.add(productDto);

        // Assert
        assertNotNull(result);
        verify(mockModelMapper).map(productDto, Product.class);
        verify(mockProductRepo).save(product);
        verify(mockModelMapper).map(product, ProductDto.class);
    }

    @Test
    public void testUpdateProduct() {
        // Arrange
        Long productId = 1L;
        ProductDto productDto = new ProductDto();
        productDto.setProductId(productId);
        Product product = new Product();
        Optional<Product> optionalProduct = Optional.of(product);
        when(mockProductRepo.findById(productId)).thenReturn(optionalProduct);
        when(mockProductRepo.save(product)).thenReturn(product);
        when(mockModelMapper.map(product, ProductDto.class)).thenReturn(new ProductDto());

        // Act
        ProductDto result = productService.update(productDto);

        // Assert
        assertNotNull(result);
        verify(mockProductRepo).findById(productId);
        verify(mockProductRepo).save(product);
        verify(mockModelMapper).map(product, ProductDto.class);
    }

    @Test
    public void testDeleteProduct() {
        // Arrange
        Long productId = 1L;
        Product product = new Product();
        Optional<Product> optionalProduct = Optional.of(product);
        when(mockProductRepo.findById(productId)).thenReturn(optionalProduct);
        doNothing().when(mockProductRepo).deleteById(productId);
        when(mockModelMapper.map(product, ProductDto.class)).thenReturn(new ProductDto());

        // Act
        ProductDto result = (ProductDto) productService.delete(productId);

        // Assert
        assertNotNull(result);
        verify(mockProductRepo).findById(productId);
        verify(mockProductRepo).deleteById(productId);
        verify(mockModelMapper).map(product, ProductDto.class);
    }

    // Additional test cases can be added for other methods

}
