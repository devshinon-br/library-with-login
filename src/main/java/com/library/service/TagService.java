package com.library.service;

import com.library.model.Tag;
import com.library.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Tag getTagById(final Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    public Tag saveTag(final Tag tag) {
        return tagRepository.save(tag);
    }

    public void deleteTag(final Long id) {
        tagRepository.deleteById(id);
    }
}
