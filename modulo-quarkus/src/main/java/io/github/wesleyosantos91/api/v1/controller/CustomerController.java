package io.github.wesleyosantos91.api.v1.controller;

import io.github.wesleyosantos91.api.v1.request.CustomerRequest;
import io.github.wesleyosantos91.core.validation.Groups;
import io.github.wesleyosantos91.domain.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.ConvertGroup;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
        return Response.ok().build();
    }
}
