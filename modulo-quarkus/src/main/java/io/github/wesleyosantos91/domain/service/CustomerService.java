package io.github.wesleyosantos91.domain.service;

import static io.github.wesleyosantos91.core.mapper.CustomerMapper.MAPPER;

import io.github.wesleyosantos91.domain.model.CustomerModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CustomerService {

    @Transactional
    public CustomerModel create(CustomerModel model) {
        final var entity = MAPPER.toEntity(model);
        entity.persist();
        return MAPPER.toModel(entity);
    }
}
