package io.github.wesleyosantos91.domain.service;

import static io.github.wesleyosantos91.core.mapper.CustomerMapper.MAPPER;

import static java.text.MessageFormat.format;

import io.github.wesleyosantos91.domain.commons.PageModel;
import io.github.wesleyosantos91.domain.entity.CustomerEntity;
import io.github.wesleyosantos91.domain.exception.CustomerHasOrdersException;
import io.github.wesleyosantos91.domain.exception.ResourceNotFoundException;
import io.github.wesleyosantos91.domain.model.CustomerModel;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.UUID;

@ApplicationScoped
public class CustomerService {

    @Transactional(Transactional.TxType.REQUIRED)
    public CustomerModel create(CustomerModel model) {
        final var entity = MAPPER.toEntity(model);
        entity.persistAndFlush();
        return MAPPER.toModel(entity);
    }

    public CustomerModel findById(UUID id) throws ResourceNotFoundException {
        return CustomerEntity.findByIdOptional(id)
                .map(customerEntity -> MAPPER.toModel((CustomerEntity) customerEntity))
                .orElseThrow(() -> new ResourceNotFoundException("Registry not found with code: " + id));
    }

    public PageModel<CustomerModel> search(int page, int size) {
        final var customers = CustomerEntity.findAll()
                .page(Page.of(page, size))
                .list()
                .stream()
                .map(customerEntity -> MAPPER.toModel((CustomerEntity) customerEntity))
                .toList();

        final var total = CustomerEntity.count();

        return new PageModel<>(customers, total);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public CustomerModel update(UUID id, CustomerModel model) throws ResourceNotFoundException {
        final var customerEntity = MAPPER.toEntity(model, getCustomer(id));
        customerEntity.persistAndFlush();
        return MAPPER.toModel(customerEntity);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void delete(UUID id) throws ResourceNotFoundException {

        if (CustomerEntity.findCustomerWithoutOrdersById(id).isPresent()) {
            throw new CustomerHasOrdersException(
                    format("Cannot delete the customer with ID {0} because there are associated orders.", id));
        }

        final var customer = getCustomer(id);
        customer.delete();
    }

    private CustomerEntity getCustomer(UUID id) throws ResourceNotFoundException {
        final var customer = CustomerEntity.findByIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Not found regitstry with code {0}", id)));
        return (CustomerEntity) customer;
    }
}
