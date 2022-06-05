package org.project.myn.service;

import org.project.myn.dto.CollectionDTO;
import org.project.myn.entity.Collection;

public interface CollectionService {
    // 등록
    Long register(CollectionDTO collectionDTO);

    // 조회
    CollectionDTO get(Long id);

    // 수정
    void modify(CollectionDTO collectionDTO);

    // 삭제
    void remove(Long id);

    default Collection dtoToEntity(CollectionDTO dto) {
        return Collection.builder()
                .id(dto.getId())
                .slug(dto.getSlug())
                .name(dto.getName())
                .url(dto.getUrl())
                .build();
    }

    default CollectionDTO entityToDto(Collection collection) {
        return CollectionDTO.builder()
                .id(collection.getId())
                .slug(collection.getSlug())
                .name(collection.getName())
                .url(collection.getUrl())
                .regDate(collection.getRegDate())
                .modDate(collection.getModDate())
                .build();
    }

}
