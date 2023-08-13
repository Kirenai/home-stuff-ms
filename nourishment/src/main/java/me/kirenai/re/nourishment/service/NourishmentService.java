package me.kirenai.re.nourishment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.exception.nourishment.NourishmentNotFoundExceptionFactory;
import me.kirenai.re.nourishment.api.CategoryManager;
import me.kirenai.re.nourishment.api.UserManager;
import me.kirenai.re.nourishment.dto.NourishmentRequest;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import me.kirenai.re.nourishment.entity.Nourishment;
import me.kirenai.re.nourishment.mapper.NourishmentMapper;
import me.kirenai.re.nourishment.repository.NourishmentRepository;
import me.kirenai.re.nourishment.util.IsAuthorized;
import me.kirenai.re.nourishment.util.IsCreatingOwnNourishment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Mono<NourishmentResponse> findOne(Long nourishmentId) {
        log.info("Invoking NourishmentService.findOne method");
        return this.nourishmentRepository.findById(nourishmentId)
                .switchIfEmpty(Mono.error(new NourishmentNotFoundExceptionFactory(
                        String.format("Nourishment not found by nourishment id: %s", nourishmentId))))
                .map(this.mapper::mapOutNourishmentToNourishmentResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Flux<NourishmentResponse> findAllByUserId(Long userId) {
        log.info("Invoking NourishmentService.findNourishmentsByUserId method");
        return this.nourishmentRepository.findByUserId(userId)
                .map(this.mapper::mapOutNourishmentToNourishmentResponse);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Flux<NourishmentResponse> findAllByIsAvailable(Boolean isAvailable) {
        log.info("Invoking NourishmentService.findAllNourishmentByStatus method");
        return this.nourishmentRepository.findByIsAvailable(isAvailable)
                .map(this.mapper::mapOutNourishmentToNourishmentResponse);
    }

    @Transactional
    @IsCreatingOwnNourishment
    public Mono<NourishmentResponse> create(Long userId, Long categoryId, NourishmentRequest nourishmentRequest, String token) {
        log.info("Invoking NourishmentService.create method");
        Nourishment nourishment = this.mapper.mapInNourishmentRequestToNourishment(nourishmentRequest);
        nourishment.setIsAvailable(Boolean.TRUE);
        return this.userManager.findUser(userId, token)
                .flatMap(userResponse -> {
                    nourishment.setUserId(userResponse.getUserId());
                    return this.categoryManager.findCategory(categoryId, token);
                })
                .flatMap(categoryResponse -> {
                    nourishment.setCategoryId(categoryResponse.getCategoryId());
                    return this.nourishmentRepository.save(nourishment);
                })
                .map(this.mapper::mapOutNourishmentToNourishmentResponse);
    }

    @Transactional
    @IsAuthorized
    public Mono<NourishmentResponse> update(Long nourishmentId, NourishmentRequest nourishmentRequest) {
        log.info("Invoking NourishmentService.update method");
        return this.nourishmentRepository.findById(nourishmentId)
                .switchIfEmpty(Mono.error(new NourishmentNotFoundExceptionFactory(
                        String.format("Nourishment not found by nourishment id: %s", nourishmentId))))
                .flatMap(nourishment -> {
                    nourishment.setName(nourishmentRequest.getName());
                    nourishment.setDescription(nourishmentRequest.getDescription());
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

    @IsAuthorized
    public Mono<Void> delete(Long nourishmentId) {
        log.info("Invoking NourishmentService.delete method");
        return this.nourishmentRepository
                .findById(nourishmentId)
                .switchIfEmpty(Mono.error(new NourishmentNotFoundExceptionFactory(
                        String.format("Nourishment not found by nourishment id: %s", nourishmentId))))
                .flatMap(this.nourishmentRepository::delete);
    }

}