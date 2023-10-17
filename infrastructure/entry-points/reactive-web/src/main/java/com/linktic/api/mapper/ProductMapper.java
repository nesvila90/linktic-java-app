package com.linktic.api.mapper;

import com.linktic.api.request.FiltersDTO;
import com.linktic.model.product.ProductQueryFilters;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

  ProductQueryFilters toProductQueryFilters(FiltersDTO filtersDTO);

}
