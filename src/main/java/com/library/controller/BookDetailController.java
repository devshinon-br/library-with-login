package com.library.controller;

import com.library.exception.EntityNotFoundException;
import com.library.model.BookDetail;
import com.library.model.dto.BookDetailDTO;
import com.library.model.dto.converter.BookDetailDTOConverter;
import com.library.service.BookDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/book-details")
public class BookDetailController {

    private final BookDetailService bookDetailService;

    @Autowired
    public BookDetailController(final BookDetailService bookDetailService) {
        this.bookDetailService = bookDetailService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/yaml"})
    public List<BookDetailDTO> getAllBookDetails() {
        final List<BookDetail> bookDetails = bookDetailService.getAllBookDetails();
        return BookDetailDTOConverter.convertToDTOList(bookDetails);
    }

    @GetMapping("/{id}")
    public EntityModel<BookDetailDTO> getBookDetailById(@PathVariable final Long id) {
        final BookDetail bookDetail = bookDetailService.getBookDetailById(id);

        if (bookDetail == null) {
            throw new EntityNotFoundException("BookDetail with ID " + id + " not found");
        }

        final EntityModel<BookDetailDTO> resource = EntityModel.of(BookDetailDTOConverter.convertToDTO(bookDetail));

        final Link selfLink = Link.of(WebMvcLinkBuilder.linkTo(BookDetailController.class).slash(id).withSelfRel().getHref());
        resource.add(selfLink);

        final Link bookDetailsLink = Link.of(WebMvcLinkBuilder.linkTo(BookDetailController.class).withRel("bookdetails").getHref());
        resource.add(bookDetailsLink);

        return resource;
    }

    @PostMapping
    public BookDetailDTO saveBookDetail(@RequestBody final BookDetailDTO bookDetailDTO) {
        final BookDetail bookDetail = BookDetailDTOConverter.convertToEntity(bookDetailDTO);
        final BookDetail savedBookDetail = bookDetailService.saveBookDetail(bookDetail);
        return BookDetailDTOConverter.convertToDTO(savedBookDetail);
    }

    @PutMapping("/{id}")
    public BookDetailDTO updateBookDetail(@PathVariable final Long id, @RequestBody final BookDetailDTO bookDetailDTO) {
        final BookDetail existingBookDetail = bookDetailService.getBookDetailById(id);

        if (existingBookDetail == null) {
            throw new EntityNotFoundException("BookDetail with ID " + id + " not found");
        }

        final BookDetail updatedBookDetail = BookDetailDTOConverter.updateEntityFromDTO(existingBookDetail, bookDetailDTO);
        final BookDetail savedBookDetail = bookDetailService.saveBookDetail(updatedBookDetail);
        return BookDetailDTOConverter.convertToDTO(savedBookDetail);
    }

    @DeleteMapping("/{id}")
    public void deleteBookDetail(@PathVariable final Long id) {
        bookDetailService.deleteBookDetail(id);
    }
}