package org.project.myn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.myn.dto.CollectionDTO;
import org.project.myn.entity.Collection;
import org.project.myn.repository.CollectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;

    @Override
    public Long register(CollectionDTO collectionDTO) {
        Collection collection = dtoToEntity(collectionDTO);
        collectionRepository.save(collection);
        return collectionDTO.getId();
    }

    @Override
    public CollectionDTO get(Long id) {
        Optional<Collection> result = collectionRepository.findById(id);
        return result.map(this::entityToDto).orElse(null);
    }

    @Override
    public void modify(CollectionDTO collectionDTO) {
        Long id = collectionDTO.getId();
        Optional<Collection> result = collectionRepository.findById(id);
        if (result.isPresent()) {
            Collection collection = result.get();
            collection.changeName(collectionDTO.getName());
            collectionRepository.save(collection);
        }
    }

    @Transactional
    @Override
    public void remove(Long id) {
        Optional<Collection> result = collectionRepository.findById(id);
        if (result.isEmpty()) return;
        collectionRepository.deleteById(id);
    }
}
