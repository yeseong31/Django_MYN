package org.project.myn.repository;

import org.junit.jupiter.api.Test;
import org.project.myn.entity.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class CollectionsRepositoryTests {

    @Autowired
    private CollectionsRepository collectionsRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            String slug = "slug" + i;
            Collections collections = Collections.builder()
                    .name("name" + i)
                    .slug(slug)
                    .url("https://opensea.io/collection/" + slug)
                    .build();
            collectionsRepository.save(collections);
        });
    }

}
