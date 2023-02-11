package me.kirenai.re.nourishment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.nourishment.dto.CategoryResponse;
import me.kirenai.re.nourishment.dto.NourishmentRequest;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import me.kirenai.re.nourishment.dto.UserResponse;
import me.kirenai.re.nourishment.entity.Nourishment;
import me.kirenai.re.nourishment.mapper.NourishmentMapper;
import me.kirenai.re.nourishment.repository.NourishmentRepository;
import me.kirenai.re.nourishment.util.NourishmentClient;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class NourishmentService {

    private static final String USER_URL_GET_ONE = "http://USER/api/v0/users/{userId}";
    private static final String CATEGORY_URL_GET_ONE = "http://CATEGORY/api/categories/{category}";

    private final NourishmentRepository nourishmentRepository;
    private final NourishmentMapper mapper;
    private final NourishmentClient client;

    public List<NourishmentResponse> findAll(Pageable pageable) {
        log.info("Invoking NourishmentService.findAll method");
        return this.nourishmentRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(this.mapper::mapOutNourishmentToNourishmentResponse)
                .toList();
    }

    public NourishmentResponse findOne(Long nourishmentId) {
        log.info("Invoking NourishmentService.findOne method");
        Nourishment nourishment = this.nourishmentRepository.findById(nourishmentId)
                .orElseThrow();
        return this.mapper.mapOutNourishmentToNourishmentResponse(nourishment);
    }

    public List<NourishmentResponse> findAllByUserId(Long userId) {
        log.info("Invoking NourishmentService.findNourishmentsByUserId method");
        return this.nourishmentRepository.findByUserId(userId)
                .stream()
                .map(this.mapper::mapOutNourishmentToNourishmentResponse)
                .toList();
    }

    public List<NourishmentResponse> findAllByIsAvailable(Boolean isAvailable) {
        log.info("Invoking NourishmentService.findAllNourishmentByStatus method");
        return this.nourishmentRepository.findByIsAvailable(isAvailable)
                .stream()
                .map(this.mapper::mapOutNourishmentToNourishmentResponse)
                .toList();
    }

    public NourishmentResponse create(Long userId, Long categoryId, NourishmentRequest nourishmentRequest) {
        log.info("Invoking NourishmentService.create method");
        Nourishment nourishment = this.mapper.mapInNourishmentRequestToNourishment(nourishmentRequest);
        nourishment.setIsAvailable(Boolean.TRUE);
        UserResponse userResponse = this.client.getResponse(userId, USER_URL_GET_ONE, UserResponse.class);
        if (Objects.nonNull(userResponse)) nourishment.setUserId(userResponse.getUserId());
        CategoryResponse categoryResponse = this.client.getResponse(categoryId, CATEGORY_URL_GET_ONE, CategoryResponse.class);
        if (Objects.nonNull(categoryResponse)) nourishment.setCategoryId(categoryResponse.getCategoryId());
        this.nourishmentRepository.save(nourishment);
        return this.mapper.mapOutNourishmentToNourishmentResponse(nourishment);
    }

    public NourishmentResponse update(Long nourishmentId, NourishmentRequest nourishmentRequest) {
        log.info("Invoking NourishmentService.update method");
        Nourishment nourishment = this.nourishmentRepository.findById(nourishmentId)
                .orElseThrow();
        nourishment.setName(nourishmentRequest.getName());
        nourishment.setDescription(nourishment.getDescription());
        nourishment.setImageUrl(nourishmentRequest.getImageUrl());
        if (Objects.nonNull(nourishmentRequest.getUnit())) {
            nourishment.setUnit(nourishmentRequest.getUnit());
            if (nourishmentRequest.getUnit() == 0) nourishment.setIsAvailable(Boolean.FALSE);
        }
        if (Objects.nonNull(nourishmentRequest.getPercentage())) {
            nourishment.setPercentage(nourishmentRequest.getPercentage());
            if (nourishmentRequest.getPercentage() == 0) nourishment.setIsAvailable(Boolean.FALSE);
        }
        this.nourishmentRepository.save(nourishment);
        return this.mapper.mapOutNourishmentToNourishmentResponse(nourishment);
    }

    public void delete(Long nourishmentId) {
        log.info("Invoking NourishmentService.delete method");
        this.nourishmentRepository.deleteById(nourishmentId);
    }

}