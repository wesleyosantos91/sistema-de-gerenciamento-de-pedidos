package io.github.wesleyosantos91.api.v1.controller;

import io.github.wesleyosantos91.api.v1.request.CustomerRequest;
import io.github.wesleyosantos91.api.v1.response.CustomerResponse;
import io.github.wesleyosantos91.api.v1.response.PageResponse;
import io.github.wesleyosantos91.core.validation.Groups;
import io.github.wesleyosantos91.domain.commons.PageModel;
import io.github.wesleyosantos91.domain.entity.CustomerEntity;
import io.github.wesleyosantos91.domain.exception.ResourceNotFoundException;
import io.github.wesleyosantos91.domain.model.CustomerModel;
import io.github.wesleyosantos91.domain.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.ConvertGroup;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.wesleyosantos91.core.mapper.CustomerMapper.MAPPER;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v1/customers")
public class CustomerController  {

    private static final String REGEX_UUID = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @POST
    public Response create(@HeaderParam("x-correlationID") @Pattern(regexp = REGEX_UUID) String correlationId,
                           @Valid @ConvertGroup(to = Groups.Create.class) CustomerRequest request) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LOGGER.debug("Function started 'create customer'");
        final var response = MAPPER.toResponse(service.create(MAPPER.toModel(request)));
        stopWatch.stop();
        LOGGER.debug("Finished function with sucess 'create customer {}' in {} ms", response, stopWatch.getDuration().toMillis());
        return Response.status(Response.Status.CREATED).entity(request).build();
    }

    @GET
    public Response getAll(@HeaderParam("x-correlationID") @Pattern(regexp = REGEX_UUID) String correlationId,
                           @QueryParam("page_number") @DefaultValue("0") int pageNumber,
                           @QueryParam("page_size") @DefaultValue("10") int pageSize) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LOGGER.debug("Function started 'getAll customers'");
        final var customers = service.search(pageNumber, pageSize);
        final var response = new PageResponse<>(customers);
        stopWatch.stop();
        LOGGER.debug("Finished function with sucess 'getAll customers' {} in {} ms", customers, stopWatch.getDuration().toMillis());
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@HeaderParam("x-correlationID") @Pattern(regexp = REGEX_UUID) String correlationId,
                            @PathParam("id") UUID id) throws ResourceNotFoundException {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LOGGER.debug("Function started 'getById customer' with id {}", id);
        final var customer = service.findById(id);
        final var response = MAPPER.toResponse(customer);
        stopWatch.stop();
        LOGGER.debug("Finished function with sucess 'getById customer' {} in {} ms", response, stopWatch.getDuration().toMillis());

        return Response.ok().entity(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@HeaderParam("x-correlationID") @Pattern(regexp = REGEX_UUID) String correlationId,
                           @PathParam("id") UUID id,
                           @Valid @ConvertGroup(to = Groups.Update.class) CustomerRequest request) throws ResourceNotFoundException {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LOGGER.debug("Function started 'update customer' with id {}", id);
        final var customer = service.update(id, MAPPER.toModel(request));
        final var response = MAPPER.toResponse(customer);
        stopWatch.stop();
        LOGGER.debug("Finished function with sucess 'update customer' {} in {} ms", response, stopWatch.getDuration().toMillis());
        return Response.ok().entity(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@HeaderParam("x-correlationID") @Pattern(regexp = REGEX_UUID) String correlationId,
                           @PathParam("id") UUID id) throws ResourceNotFoundException {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        LOGGER.debug("Function started 'delete customer' with id {}", id);
        service.delete(id);
        stopWatch.stop();
        LOGGER.debug("Finished function with sucess 'delete customer' {} in {} ms", id, stopWatch.getDuration().toMillis());
        return Response.noContent().build();
    }
}
