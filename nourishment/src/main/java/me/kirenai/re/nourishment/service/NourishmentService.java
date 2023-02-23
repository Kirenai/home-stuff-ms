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
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class NourishmentService {

    private static final String USER_URL_GET_ONE = "http://USER/api/v0/users/{userId}";
    private static final String CATEGORY_URL_GET_ONE = "http://CATEGORY/api/v0/categories/{categoryId}";

    private final NourishmentRepository nourishmentRepository;
    private final NourishmentMapper mapper;
    private final NourishmentClient client;
    private final JwtTokenProvider jwtTokenProvider;

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
        HttpHeaders currentTokenAsHeader = this.jwtTokenProvider.getCurrentTokenAsHeader();
        ResponseEntity<UserResponse> userEntity = this.client.exchange(
                USER_URL_GET_ONE,
                HttpMethod.GET,
                new HttpEntity<>(currentTokenAsHeader),
                UserResponse.class,
                userId
        );
        UserResponse userResponse = userEntity.getBody();
        if (Objects.nonNull(userResponse)) nourishment.setUserId(userResponse.getUserId());
        ResponseEntity<CategoryResponse> categoryEntity = this.client.exchange(
                CATEGORY_URL_GET_ONE,
                HttpMethod.GET,
                new HttpEntity<>(currentTokenAsHeader),
                CategoryResponse.class,
                categoryId
        );
        CategoryResponse categoryResponse = categoryEntity.getBody();
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