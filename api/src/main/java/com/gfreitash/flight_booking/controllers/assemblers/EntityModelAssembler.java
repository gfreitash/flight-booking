package com.gfreitash.flight_booking.controllers.assemblers;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.function.ToIntFunction;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class EntityModelAssembler<T, D> implements RepresentationModelAssembler<T, EntityModel<T>> {

    private final Class<D> controllerClass;

    public EntityModelAssembler(Class<D> controllerClass) {
        this.controllerClass = controllerClass;
    }

    @Override
    public EntityModel<T> toModel(T entity) {
        return EntityModel.of(entity);
    }

    public EntityModel<T> toModel(T entity, Link... links) {
        EntityModel<T> entityModel = EntityModel.of(entity);
        entityModel.add(links);
        return entityModel;
    }

    //The purpose of this method is to add a generalized way to add links to the collection model,
    //so that the controller doesn't have to do it and the paged model can be appropriately defined with HATEOAS standards
    public CollectionModel<EntityModel<T>> toCollectionModel(
            Iterable<? extends T> entities,
            ToIntFunction<T> getIdFunction) {
        CollectionModel<EntityModel<T>> collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);
        collectionModel.forEach(entityModel -> {
            String id = String.valueOf(getIdFunction.applyAsInt(entityModel.getContent()));
            entityModel.add(linkTo(controllerClass).slash(id).withSelfRel());
        });

        return collectionModel;
    }

    public PagedModel<EntityModel<T>> toPagedModel(Page<T> page, CollectionModel<EntityModel<T>> collectionModel) {
        PagedModel<EntityModel<T>> pagedModel = PagedModel.of(
               collectionModel.getContent(),
                new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements())
        );

        WebMvcLinkBuilder link = linkTo(controllerClass);
        pagedModel.add(link.withSelfRel());
        if (page.hasPrevious()) {
            pagedModel.add(link.withRel(IanaLinkRelations.PREVIOUS));
        }
        if (page.hasNext()) {
            pagedModel.add(link.withRel(IanaLinkRelations.NEXT));
        }

        return pagedModel;
    }
}
