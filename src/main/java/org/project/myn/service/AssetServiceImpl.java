package org.project.myn.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.project.myn.dto.AssetDTO;
import org.project.myn.entity.Asset;
import org.project.myn.repository.AccountRepository;
import org.project.myn.repository.AssetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

    @Override
    public Long register(AssetDTO assetDTO) {
        Asset asset = dtoToEntity(assetDTO);
        assetRepository.save(asset);
        return assetDTO.getId();
    }

    @Transactional
    @Override
    public AssetDTO get(Long id) {
        Optional<Asset> result = assetRepository.findById(id);
        return result.map(this::entityToDto).orElse(null);
    }

    @Override
    public void modify(AssetDTO assetDTO) {
        Long id = assetDTO.getId();
        Optional<Asset> result = assetRepository.findById(id);
        if (result.isPresent()) {
            Asset asset = result.get();
            asset.changeName(assetDTO.getName());
            asset.changeDescription(assetDTO.getDescription());
            assetRepository.save(asset);
        }
    }

    @Transactional
    @Override
    public void remove(Long id) {
        Optional<Asset> result = assetRepository.findById(id);
        if (result.isEmpty()) return;
        assetRepository.deleteById(id);
    }
}
