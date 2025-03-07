package io.github.wesleyosantos91.domain.service;

import io.github.wesleyosantos91.core.metric.annotation.CounterExecution;
import io.github.wesleyosantos91.domain.entity.CustomerEntity;
import io.github.wesleyosantos91.domain.exception.CustomerHasOrdersException;
import io.github.wesleyosantos91.domain.exception.ResourceNotFoundException;
import io.github.wesleyosantos91.domain.model.CustomerModel;
import io.github.wesleyosantos91.domain.repository.CustomerRepository;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static io.github.wesleyosantos91.core.mapper.CustomerMapper.MAPPER;
import static java.text.MessageFormat.format;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @CounterExecution(name = "customers.serivce.create")
    @Transactional
    public CustomerModel create(CustomerModel model) {
        final var entity= repository.save(MAPPER.toEntity(model));
        return MAPPER.toModel(entity);
    }

    @CounterExecution(name = "customers.serivce.findById")
    @Transactional(readOnly = true)
    public CustomerModel findById(UUID id) throws ResourceNotFoundException {
        return repository.findById(id)
                .map(MAPPER::toModel)
                .orElseThrow(() -> new ResourceNotFoundException(format("Not found regitstry with code {0}", id)));
    }

    @CounterExecution(name = "customer.service.search")
    @Transactional(readOnly = true)
    public Page<CustomerModel> search(CustomerModel model, Pageable pageable) {

        final var page = repository.search(MAPPER.toEntity(model), pageable);

        return MAPPER.toPageModel(page);
    }

    @Transactional
    public CustomerModel update(UUID id, CustomerModel model) throws ResourceNotFoundException {
        final var customer = MAPPER.toEntity(model, getCustomer(id));
        final var customerUpdated = repository.save(customer);
        return MAPPER.toModel(customerUpdated);
    }

    @Transactional
    public void delete(UUID id) throws ResourceNotFoundException {

        if (repository.findCustomerWithoutOrdersById(id).isPresent()) {
            throw new CustomerHasOrdersException(
                    format("Cannot delete the customer with ID {0} because there are associated orders.", id));
        }

        final var customer = getCustomer(id);
        repository.delete(customer);
    }

    private CustomerEntity getCustomer(UUID id) throws ResourceNotFoundException {
        final var customer = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("Not found regitstry with code {0}", id)));
        return customer;
    }
}
