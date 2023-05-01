package me.kirenai.re.nourishment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.nourishment.api.CategoryManager;
import me.kirenai.re.nourishment.api.UserManager;
import me.kirenai.re.nourishment.dto.CategoryResponse;
import me.kirenai.re.nourishment.dto.NourishmentRequest;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import me.kirenai.re.nourishment.dto.UserResponse;
import me.kirenai.re.nourishment.entity.Nourishment;
import me.kirenai.re.nourishment.mapper.NourishmentMapper;
import me.kirenai.re.nourishment.repository.NourishmentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class NourishmentService {

    private final NourishmentRepository nourishmentRepository;
    private final NourishmentMapper mapper;
    private final UserManager userManager;
    private final CategoryManager categoryManager;

//    public List<NourishmentResponse> findAll(Pageable pageable) {
//        log.info("Invoking NourishmentService.findAll method");
//        return this.nourishmentRepository.findAll(pageable)
//                .getContent()
//                .stream()
//                .map(this.mapper::mapOutNourishmentToNourishmentResponse)
//                .toList();
//    }

    public Mono<NourishmentResponse> findOne(Long nourishmentId) {
        log.info("Invoking NourishmentService.findOne method");
        return this.nourishmentRepository.findById(nourishmentId)
                .map(this.mapper::mapOutNourishmentToNourishmentResponse);
    }

    public Flux<NourishmentResponse> findAllByUserId(Long userId) {
        log.info("Invoking NourishmentService.findNourishmentsByUserId method");
        return this.nourishmentRepository.findByUserId(userId)
                .map(this.mapper::mapOutNourishmentToNourishmentResponse);
    }

    public Flux<NourishmentResponse> findAllByIsAvailable(Boolean isAvailable) {
        log.info("Invoking NourishmentService.findAllNourishmentByStatus method");
        return this.nourishmentRepository.findByIsAvailable(isAvailable)
                .map(this.mapper::mapOutNourishmentToNourishmentResponse);
    }

    public Mono<NourishmentResponse> create(Long userId, Long categoryId, NourishmentRequest nourishmentRequest, String token) {
        log.info("Invoking NourishmentService.create method");
        Nourishment nourishment = this.mapper.mapInNourishmentRequestToNourishment(nourishmentRequest);
        nourishment.setIsAvailable(Boolean.TRUE);
        Mono<UserResponse> userResponse = this.userManager.findUser(userId, token);
        userResponse.subscribe(user -> nourishment.setUserId(user.getUserId()));
        Mono<CategoryResponse> categoryResponse = this.categoryManager.findCategory(categoryId, token);
        categoryResponse.subscribe(category -> nourishment.setCategoryId(category.getCategoryId()));
        return this.nourishmentRepository.save(nourishment)
                .map(this.mapper::mapOutNourishmentToNourishmentResponse);
    }

    public Mono<NourishmentResponse> update(Long nourishmentId, NourishmentRequest nourishmentRequest) {
        log.info("Invoking NourishmentService.update method");
        return this.nourishmentRepository.findById(nourishmentId)
                .flatMap(nourishment -> {
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
                    return this.nourishmentRepository.save(nourishment);
                })
                .map(this.mapper::mapOutNourishmentToNourishmentResponse);
    }

    public Mono<Void> delete(Long nourishmentId) {
        log.info("Invoking NourishmentService.delete method");
        return Mono.fromRunnable(() -> this.nourishmentRepository.deleteById(nourishmentId));
    }

}