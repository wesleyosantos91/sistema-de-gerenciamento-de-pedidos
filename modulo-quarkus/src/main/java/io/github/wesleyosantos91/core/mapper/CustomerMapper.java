package io.github.wesleyosantos91.core.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import io.github.wesleyosantos91.api.v1.request.CustomerRequest;
import io.github.wesleyosantos91.api.v1.response.CustomerResponse;
import io.github.wesleyosantos91.domain.entity.CustomerEntity;
import io.github.wesleyosantos91.domain.model.CustomerModel;
import io.quarkus.panache.common.Page;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CustomerMapper {

    CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);

    CustomerModel toModel(CustomerRequest request);


    CustomerModel toModel(CustomerEntity entity);

    CustomerEntity toEntity(CustomerModel model);

    CustomerEntity toEntity(CustomerModel model, @MappingTarget CustomerEntity entity);

    CustomerResponse toResponse(CustomerModel model);
//
//    default List<CustomerModel> toModelList(List<CustomerEntity> entities) {
//        return entities.stream().map(this::toModel).collect(Collectors.toList());
//    }
//
//    default List<CustomerResponse> toResponseList(List<CustomerModel> models) {
//        return models.stream().map(this::toResponse).collect(Collectors.toList());
//    }
//
//    default Page<CustomerModel> toPageModel(Page<CustomerEntity> pages) {
//        final List<CustomerModel> models = toModelList(pages.getContent());
//        return new PageImpl<>(models, pages.getPageable(), pages.getTotalElements());
//    }
//
//    default Page<CustomerResponse> toPageResponse(Page<CustomerModel> pages) {
//        final List<CustomerResponse> models = toResponseList(pages.getContent());
//        return new PageImpl<>(models, pages.getPageable(), pages.getTotalElements());
//    }

}
