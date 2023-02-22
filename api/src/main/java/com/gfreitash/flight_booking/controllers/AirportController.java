package com.gfreitash.flight_booking.controllers;

import com.gfreitash.flight_booking.controllers.assemblers.EntityModelAssembler;
import com.gfreitash.flight_booking.dto.AirportResource;
import com.gfreitash.flight_booking.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
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
        var selfLink = linkTo(methodOn(AirportController.class).getOneAirport(id)).withSelfRel();
        var collectionLink = linkTo(methodOn(AirportController.class).getAllAirports(null)).withRel("collection");

        return airportService.getAirportById(id)
                .map(airport -> airportAssembler.toModel(airport, selfLink, collectionLink))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<AirportResource>>> getAllAirports(Pageable pagination) {
        var airports = airportService.getAllAirports(pagination);
        var airportCollectionModel = airportAssembler.toCollectionModel(airports.getContent(), AirportResource::id);
        var pagedModel = airportAssembler.toPagedModel(airports, airportCollectionModel);

        return ResponseEntity.ok().body(pagedModel);
    }
}
