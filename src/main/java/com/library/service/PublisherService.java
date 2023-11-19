package com.library.service;

import com.library.model.Publisher;
import com.library.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(final PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher getPublisherById(final Long id) {
        return publisherRepository.findById(id).orElse(null);
    }

    public Publisher savePublisher(final Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public void deletePublisher(final Long id) {
        publisherRepository.deleteById(id);
    }
}
