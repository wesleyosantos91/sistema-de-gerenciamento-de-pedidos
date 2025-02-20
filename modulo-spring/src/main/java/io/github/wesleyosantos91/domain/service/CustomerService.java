package io.github.wesleyosantos91.domain.service;

import io.github.wesleyosantos91.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final ProductRepository productRepository;

    public CustomerService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
