package com.farmSphere.farming.service;

import com.farmSphere.core.enums.PRODUCE_CATEGORY;
import com.farmSphere.farming.data.model.Crop;
import com.farmSphere.farming.data.repository.CropRepository;
import com.farmSphere.farming.dto.request.CreateCropRequest;
import com.farmSphere.farming.dto.response.CropResponse;
import com.farmSphere.infrastructure.exception.DomainException;
import com.farmSphere.infrastructure.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.farmSphere.farming.util.Mapper.mapToCreateCrop;


@Service
@RequiredArgsConstructor
public class CropServiceImplemetation implements CropService{
    private final CropRepository cropRepository;

    @Override
    public CropResponse createCrop(CreateCropRequest request) {
        SecurityUtils.requireAdmin();
        if (cropRepository.existsByCropNameIgnoreCase(request.getCropName())) throw new DomainException("Crop '" + request.getCropName() + "' already exists", 409);
        Crop crop = mapToCreateCrop(request);

        return CropResponse.from(cropRepository.save(crop));

    }


    @Override
    public List<CropResponse> getAllCrops() {return cropRepository.findAll().stream().map(CropResponse::from).toList();    }

    @Override
    public CropResponse getCropById(Long cropId) {return cropRepository.findById(cropId).map(CropResponse::from).orElseThrow(() -> new DomainException("Crop not found", 404));    }

    @Override
    public List<CropResponse> getCropsByCategory(PRODUCE_CATEGORY category) {return cropRepository.findAllByCategory(category).stream().map(CropResponse::from).toList();}
}
