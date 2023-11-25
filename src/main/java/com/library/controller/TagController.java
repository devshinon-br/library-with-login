package com.library.controller;

import com.library.exception.EntityNotFoundException;
import com.library.model.Tag;
import com.library.model.dto.TagDTO;
import com.library.model.dto.converter.TagDTOConverter;
import com.library.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(final TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagDTO> getAllTags() {
        final List<Tag> tags = tagService.getAllTags();
        return TagDTOConverter.convertToDTOList(tags);
    }

    @GetMapping("/{id}")
    public EntityModel<TagDTO> getTagById(@PathVariable final Long id) {
        final Tag tag = tagService.getTagById(id);

        if (tag == null) {
            throw new EntityNotFoundException("Tag with ID " + id + " not found");
        }

        final EntityModel<TagDTO> resource = EntityModel.of(TagDTOConverter.convertToDTO(tag));

        final Link selfLink = Link.of(WebMvcLinkBuilder.linkTo(TagController.class).slash(id).withSelfRel().getHref());
        resource.add(selfLink);

        final Link tagsLink = Link.of(WebMvcLinkBuilder.linkTo(TagController.class).withRel("tags").getHref());
        resource.add(tagsLink);

        return resource;
    }

    @PostMapping
    public TagDTO saveTag(@RequestBody final TagDTO tagDTO) {
        final Tag tag = TagDTOConverter.convertToEntity(tagDTO);
        final Tag savedTag = tagService.saveTag(tag);
        return TagDTOConverter.convertToDTO(savedTag);
    }

    @PutMapping("/{id}")
    public TagDTO updateTag(@PathVariable final Long id, @RequestBody final TagDTO tagDTO) {
        final Tag existingTag = tagService.getTagById(id);

        if (existingTag == null) {
            throw new EntityNotFoundException("Tag with ID " + id + " not found");
        }

        final Tag updatedTag = TagDTOConverter.updateEntityFromDTO(existingTag, tagDTO);
        final Tag savedTag = tagService.saveTag(updatedTag);
        return TagDTOConverter.convertToDTO(savedTag);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable final Long id) {
        tagService.deleteTag(id);
    }
}

