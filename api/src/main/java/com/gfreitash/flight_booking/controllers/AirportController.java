package com.gfreitash.flight_booking.controllers;

import com.gfreitash.flight_booking.controllers.assemblers.EntityModelAssembler;
import com.gfreitash.flight_booking.dto.AirportResource;
import com.gfreitash.flight_booking.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;
    private final EntityModelAssembler<AirportResource, AirportController> airportAssembler;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
        this.airportAssembler = new EntityModelAssembler<>(AirportController.class);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AirportResource>> getOneAirport(@PathVariable String id) {
        Link selfLink = linkTo(methodOn(AirportController.class).getOneAirport(id)).withSelfRel();
        return airportService.getAirportById(id)
                .map(airport -> airportAssembler.toModel(airport, selfLink))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<AirportResource>>> getAllAirports(Pageable pagination) {
        Page<AirportResource> airports = airportService.getAllAirports(pagination);

        CollectionModel<EntityModel<AirportResource>> airportCollectionModel = airportAssembler.toCollectionModel(
                airports.getContent(), AirportResource::id
        );

        PagedModel<EntityModel<AirportResource>> pagedModel = airportAssembler.toPagedModel(
                airports, airportCollectionModel
        );

        return ResponseEntity.ok().body(pagedModel);
    }
}
