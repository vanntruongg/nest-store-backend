package vantruong.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vantruong.productservice.repository.ProductRepository;
import vantruong.productservice.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
}
