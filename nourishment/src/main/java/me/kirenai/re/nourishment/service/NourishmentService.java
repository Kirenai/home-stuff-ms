package me.kirenai.re.nourishment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.exception.nourishment.NourishmentNotFoundExceptionFactory;
import me.kirenai.re.nourishment.api.CategoryManager;
import me.kirenai.re.nourishment.api.UserManager;
import me.kirenai.re.nourishment.dto.NourishmentRequest;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import me.kirenai.re.nourishment.mapper.NourishmentMapper;
import me.kirenai.re.nourishment.repository.NourishmentRepository;
import me.kirenai.re.nourishment.repository.NourishmentSortingRepository;
import me.kirenai.re.nourishment.util.MapperUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class NourishmentService {

    private final NourishmentRepository nourishmentRepository;
    private final NourishmentSortingRepository nourishmentSortingRepository;
    private final NourishmentMapper mapper;
    private final UserManager userManager;
    private final CategoryManager categoryManager;

    public Flux<NourishmentResponse> findAll(PageRequest pageable) {
        log.info("Invoking NourishmentService.findAll method");
        return this.nourishmentSortingRepository.findAllBy(pageable)
                .map(this.mapper::mapOutNourishmentToNourishmentResponse);
    }

    public Mono<NourishmentResponse> findOne(Long nourishmentId) {
        log.info("Invoking NourishmentService.findOne method");
        return this.nourishmentRepository.findById(nourishmentId)
                .switchIfEmpty(Mono.error(new NourishmentNotFoundExceptionFactory(
                        String.format("Nourishment not found by nourishment id: %s", nourishmentId))))
                .flatMap(this.mapper::mapOutNourishmentResponseToMono);
    }

    public Flux<NourishmentResponse> findAllByUserId(Long userId) {
        log.info("Invoking NourishmentService.findNourishmentsByUserId method");
        return this.nourishmentRepository.findByUserId(userId)
                .flatMap(this.mapper::mapOutNourishmentResponseToMono);
    }

    public Flux<NourishmentResponse> findAllByIsAvailable(Boolean isAvailable) {
        log.info("Invoking NourishmentService.findAllNourishmentByStatus method");
        return this.nourishmentRepository.findByIsAvailable(isAvailable)
                .flatMap(this.mapper::mapOutNourishmentResponseToMono);
    }

    @Transactional
    public Mono<NourishmentResponse> create(Long userId, Long categoryId, NourishmentRequest nourishmentRequest) {
        log.info("Invoking NourishmentService.create method");
        return this.mapper.mapInNourishmentToMono(nourishmentRequest)
                .flatMap(nourishment -> {
                    nourishment.setIsAvailable(Boolean.TRUE);
                    return this.userManager.findUser(userId)
                            .flatMap(userResponse -> {
                                nourishment.setUserId(userResponse.getUserId());
                                return this.categoryManager.findCategory(categoryId);
                            })
                            .flatMap(categoryResponse -> {
                                nourishment.setCategoryId(categoryResponse.getCategoryId());
                                return this.nourishmentRepository.save(nourishment);
                            });
                })
                .flatMap(this.mapper::mapOutNourishmentResponseToMono);
    }

    @Transactional
    public Mono<NourishmentResponse> update(Long nourishmentId, NourishmentRequest nourishmentRequest) {
        log.info("Invoking NourishmentService.update method");
        return this.nourishmentRepository.findById(nourishmentId)
                .switchIfEmpty(Mono.error(new NourishmentNotFoundExceptionFactory(
                        String.format("Nourishment not found by nourishment id: %s", nourishmentId))))
                .map(nourishment -> MapperUtils.loadDataToNourishment(nourishment, nourishmentRequest))
                .flatMap(this.nourishmentRepository::save)
                .flatMap(this.mapper::mapOutNourishmentResponseToMono);
    }

    public Mono<Void> delete(Long nourishmentId) {
        log.info("Invoking NourishmentService.delete method");
        return this.nourishmentRepository
                .findById(nourishmentId)
                .switchIfEmpty(Mono.error(new NourishmentNotFoundExceptionFactory(
                        String.format("Nourishment not found by nourishment id: %s", nourishmentId))))
                .flatMap(this.nourishmentRepository::delete);
    }

}