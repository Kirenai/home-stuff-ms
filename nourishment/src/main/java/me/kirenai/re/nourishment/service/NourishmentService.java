package me.kirenai.re.nourishment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.nourishment.dto.NourishmentRequest;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import me.kirenai.re.nourishment.entity.Nourishment;
import me.kirenai.re.nourishment.mapper.NourishmentMapper;
import me.kirenai.re.nourishment.repository.NourishmentRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NourishmentService {

    private final NourishmentRepository nourishmentRepository;
    private final NourishmentMapper mapper;

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

    public List<NourishmentResponse> findAllByIsAvailable(boolean isAvailable) {
        log.info("Invoking NourishmentService.findAllNourishmentByStatus method");
        return this.nourishmentRepository.findByIsAvailable(isAvailable)
                .stream()
                .map(this.mapper::mapOutNourishmentToNourishmentResponse)
                .toList();
    }

    public NourishmentResponse create(Long userId, Long categoryId, NourishmentRequest nourishmentRequest) {
        log.info("Invoking NourishmentService.create method");
        Nourishment nourishment = this.mapper.mapOutNourishmentRequestToNourishment(nourishmentRequest);
        nourishment.setIsAvailable(Boolean.TRUE);
        // TODO: call service user and category
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
        this.nourishmentRepository.save(nourishment);
        return this.mapper.mapOutNourishmentToNourishmentResponse(nourishment);
    }

    public void delete(Long nourishmentId) {
        log.info("Invoking NourishmentService.delete method");
        this.nourishmentRepository.deleteById(nourishmentId);
    }

}