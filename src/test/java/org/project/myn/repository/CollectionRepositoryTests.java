package org.project.myn.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.project.myn.entity.Collection;
import org.project.myn.entity.QCollections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
public class CollectionRepositoryTests {

    @Autowired
    private CollectionRepository collectionsRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            String slug = "slug" + i;
            Collection collections = Collection.builder()
                    .name("name" + i)
                    .slug(slug)
                    .url("https://opensea.io/collection/" + slug)
                    .build();
            collectionsRepository.save(collections);
        });
    }

    // Querydsl --------------------------------------------------
    // 다중 항목 검색 테스트: collections의 name 혹은 slug에 '1'이 포함되어 있는 경우를 확인
    @Transactional
    @Test
    public void testQuery() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());

        QCollections qCollections = QCollections.collections;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression expName = qCollections.name.contains(keyword);
        BooleanExpression expSlug = qCollections.slug.contains(keyword);
        BooleanExpression expAll = expName.or(expSlug);

        builder.and(expAll);
        builder.and(qCollections.id.gt(0L));

        Page<Collection> result = collectionsRepository.findAll(builder, pageable);
        result.stream().forEach(System.out::println);
    }

}
