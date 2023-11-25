package com.library.config;

import com.library.model.Book;
import com.library.model.Genre;
import com.library.model.dto.response.BookResponse;
import com.library.model.dto.response.GenreResponse;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<List<Genre>, List<GenreResponse>> genresConverter = context -> {
            List<Genre> sourceGenres = context.getSource();
            return sourceGenres.stream()
                .map(genre -> modelMapper.map(genre, GenreResponse.class))
                .collect(Collectors.toList());
        };

        TypeMap<Book, BookResponse> bookTypeMap = modelMapper.createTypeMap(Book.class, BookResponse.class);
        bookTypeMap.addMappings(mapper -> mapper.using(genresConverter).map(Book::getGenres, BookResponse::setGenres));

        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        return modelMapper;
    }
}
