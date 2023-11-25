package com.library.controller;

import com.library.exception.EntityNotFoundException;
import com.library.model.Publisher;
import com.library.model.dto.PublisherDTO;
import com.library.model.dto.converter.PublisherDTOConverter;
import com.library.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(final PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<PublisherDTO> getAllPublishers() {
        final List<Publisher> publishers = publisherService.getAllPublishers();
        return PublisherDTOConverter.convertToDTOList(publishers);
    }

    @GetMapping("/{id}")
    public EntityModel<PublisherDTO> getPublisherById(@PathVariable final Long id) {
        final Publisher publisher = publisherService.getPublisherById(id);

        if (publisher == null) {
            throw new EntityNotFoundException("Publisher with ID " + id + " not found");
        }

        final EntityModel<PublisherDTO> resource = EntityModel.of(PublisherDTOConverter.convertToDTO(publisher));

        final Link selfLink = Link.of(WebMvcLinkBuilder.linkTo(PublisherController.class).slash(id).withSelfRel().getHref());
        resource.add(selfLink);

        final Link publishersLink = Link.of(WebMvcLinkBuilder.linkTo(PublisherController.class).withRel("publishers").getHref());
        resource.add(publishersLink);

        return resource;
    }

    @PostMapping
    public PublisherDTO savePublisher(@RequestBody final PublisherDTO publisherDTO) {
        final Publisher publisher = PublisherDTOConverter.convertToEntity(publisherDTO);
        final Publisher savedPublisher = publisherService.savePublisher(publisher);
        return PublisherDTOConverter.convertToDTO(savedPublisher);
    }

    @PutMapping("/{id}")
    public PublisherDTO updatePublisher(@PathVariable final Long id, @RequestBody final PublisherDTO publisherDTO) {
        final Publisher existingPublisher = publisherService.getPublisherById(id);

        if (existingPublisher == null) {
            throw new EntityNotFoundException("Publisher with ID " + id + " not found");
        }

        final Publisher updatedPublisher = PublisherDTOConverter.updateEntityFromDTO(existingPublisher, publisherDTO);
        final Publisher savedPublisher = publisherService.savePublisher(updatedPublisher);
        return PublisherDTOConverter.convertToDTO(savedPublisher);
    }

    @DeleteMapping("/{id}")
    public void deletePublisher(@PathVariable final Long id) {
        publisherService.deletePublisher(id);
    }
}


