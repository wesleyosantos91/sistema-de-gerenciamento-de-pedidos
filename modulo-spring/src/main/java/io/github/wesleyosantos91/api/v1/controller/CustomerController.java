package io.github.wesleyosantos91.api.v1.controller;

import static io.github.wesleyosantos91.core.mapper.CustomerMapper.MAPPER;

import io.github.wesleyosantos91.api.v1.request.CustomerQueryRequest;
import io.github.wesleyosantos91.api.v1.request.CustomerRequest;
import io.github.wesleyosantos91.api.v1.response.CustomerResponse;
import io.github.wesleyosantos91.core.validation.Groups;
import io.github.wesleyosantos91.domain.exception.ResourceNotFoundException;
import io.github.wesleyosantos91.domain.service.CustomerService;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customers")
public record CustomerController(CustomerService service) {

    private static final String REGEX_UUID = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestHeader("x-correlationID") @Pattern(regexp = REGEX_UUID) String correlationId,
                                                   @Validated(Groups.Create.class) @RequestBody CustomerRequest request) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LOGGER.debug("Function started 'create customer'");
        final var response = MAPPER.toResponse(service.create(MAPPER.toModel(request)));
        stopWatch.stop();
        LOGGER.debug("Finished function with sucess 'create customer {}' in {} ms", response, stopWatch.getTotalTimeMillis());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerResponse> getById(@RequestHeader("x-correlationID") @Pattern(regexp = REGEX_UUID) String correlationId,
                                                @PathVariable UUID id) throws ResourceNotFoundException {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LOGGER.debug("Function started 'getById customer' with id {}", id);
        final var customer = service.findById(id);
        final var response = MAPPER.toResponse(customer);
        stopWatch.stop();
        LOGGER.debug("Finished function with sucess 'getById customer' {} in {} ms", response, stopWatch.getTotalTimeMillis());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<PagedModel<CustomerResponse>> search(@RequestHeader("x-correlationID") @Pattern(regexp = REGEX_UUID) String correlationId,
                                                               @ModelAttribute CustomerQueryRequest query,
                                                               @PageableDefault(sort = "createdAt", direction = Sort.Direction.ASC) Pageable page) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LOGGER.info("Function started 'find customer'");
        final var pageEntity = service.search(MAPPER.toModel(query), page);
        stopWatch.stop();
        LOGGER.info("Finished function with customer 'find person' in {} ms", stopWatch.getTotalTimeMillis());

        return pageEntity.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok().body(new PagedModel<>(MAPPER.toPageResponse(pageEntity)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomerResponse> update(@RequestHeader("x-correlationID") @Pattern(regexp = REGEX_UUID) String correlationId,
                                               @PathVariable UUID id,
                                               @Validated(Groups.Update.class)
                                               @RequestBody CustomerRequest request) throws ResourceNotFoundException {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LOGGER.debug("Function started 'update customer'");
        final var customer = service.update(id, MAPPER.toModel(request));
        stopWatch.stop();
        LOGGER.debug("Finished function with sucess 'update customer' {} in {} ms", customer, stopWatch.getTotalTimeMillis());
        return ResponseEntity.status(HttpStatus.OK).body(MAPPER.toResponse(customer));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@RequestHeader("x-correlationID") @Pattern(regexp = REGEX_UUID) String correlationId,
                                       @PathVariable UUID id) throws ResourceNotFoundException {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LOGGER.debug("Function started 'delete customer' with id {}", id);
        service.delete(id);
        stopWatch.stop();
        LOGGER.debug("Finished function with success 'delete person' in {} ms", stopWatch.getTotalTimeMillis());

        return ResponseEntity.noContent().build();
    }
}
