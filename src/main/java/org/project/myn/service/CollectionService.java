package org.project.myn.service;

import org.project.myn.dto.CollectionDTO;
import org.project.myn.entity.Collection;

public interface CollectionService {

    default Collection dtoToEntity(CollectionDTO collectionDTO) {
        return Collection.builder()
                .id(collectionDTO.getId())
                .slug(collectionDTO.getSlug())
                .name(collectionDTO.getName())
                .url(collectionDTO.getUrl())
                .build();
    }

    default CollectionDTO entityToDto(Collection entity) {
        return CollectionDTO.builder()
                .id(entity.getId())
                .slug(entity.getSlug())
                .name(entity.getName())
                .url(entity.getUrl())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
    }

}
