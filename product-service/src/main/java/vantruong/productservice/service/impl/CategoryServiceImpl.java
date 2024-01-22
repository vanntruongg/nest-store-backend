package vantruong.productservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vantruong.productservice.repository.CategoryRepository;
import vantruong.productservice.service.CategoryService;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
}
