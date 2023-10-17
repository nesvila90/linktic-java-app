package com.linktic.persistence.product.mapper;

import com.linktic.model.commons.PageDTO;
import com.linktic.model.commons.PageMetadata;
import com.linktic.model.product.ProductModel;
import com.linktic.persistence.product.model.Product;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDataEntityMapper {

  private final ProductEntityMapper productEntityMapper;

  public PageDTO<ProductModel> toPageDTO(Page<Product> page) {

    PageMetadata pageMetadata = PageMetadata.builder()
        .number(page.getNumber())
        .totalElements(page.getTotalElements())
        .totalPages(page.getTotalPages())
        .size(page.getSize())
        .build();

    List<ProductModel> productModels = page.getContent().stream()
        .map(productEntityMapper::toModel)
        .collect(Collectors.toList());

    return PageDTO.<ProductModel>builder()
        .pageMetadata(pageMetadata)
        .content(productModels)
        .build();
  }

  public ProductModel toModel(Product product) {
    return productEntityMapper.toModel(product);
  }

}
