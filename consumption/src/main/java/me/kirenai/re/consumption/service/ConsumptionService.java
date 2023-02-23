package me.kirenai.re.consumption.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.dto.ConsumptionRequest;
import me.kirenai.re.consumption.dto.ConsumptionResponse;
import me.kirenai.re.consumption.dto.NourishmentResponse;
import me.kirenai.re.consumption.dto.UserResponse;
import me.kirenai.re.consumption.entity.Consumption;
import me.kirenai.re.consumption.mapper.ConsumptionMapper;
import me.kirenai.re.consumption.repository.ConsumptionRepository;
import me.kirenai.re.consumption.util.ConsumptionClient;
import me.kirenai.re.consumption.util.ConsumptionProcess;
import me.kirenai.re.security.jwt.JwtTokenProvider;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumptionService {

    private static final String USER_URL_GET_ONE = "http://USER/api/v0/users/{userId}";
    private static final String NOURISHMENT_URL_GET_ONE = "http://NOURISHMENT/api/v0/nourishments/{nourishmentsId}";

    private final ConsumptionRepository consumptionRepository;
    private final ConsumptionMapper mapper;
    private final ConsumptionClient client;
    private final JwtTokenProvider jwtTokenProvider;

    public List<ConsumptionResponse> findAll(Pageable pageable) {
        log.info("Invoking ConsumptionService.findAll method");
        return this.consumptionRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(this.mapper::mapOutConsumptionToConsumptionResponse)
                .toList();
    }

    public ConsumptionResponse findOne(Long consumptionId) {
        log.info("Invoking ConsumptionService.findOne method");
        Consumption consumption = this.consumptionRepository.findById(consumptionId)
                .orElseThrow();
        return this.mapper.mapOutConsumptionToConsumptionResponse(consumption);
    }

    public ConsumptionResponse create(Long nourishmentId, Long userId, ConsumptionRequest consumptionDto) {
        log.info("Invoking ConsumptionService.create method");
        Consumption consumption = this.mapper.mapInConsumptionRequestToConsumption(consumptionDto);
        ResponseEntity<UserResponse> userEntity = this.client.exchange(
                USER_URL_GET_ONE,
                HttpMethod.GET,
                new HttpEntity<>(this.jwtTokenProvider.getCurrentTokenAsHeader()),
                UserResponse.class,
                userId
        );
        UserResponse userResponse = userEntity.getBody();
        if (Objects.nonNull(userResponse)) consumption.setUserId(userResponse.getUserId());
        ResponseEntity<NourishmentResponse> nourishmentEntity = this.client.exchange(
                NOURISHMENT_URL_GET_ONE,
                HttpMethod.GET,
                new HttpEntity<>(this.jwtTokenProvider.getCurrentTokenAsHeader()),
                NourishmentResponse.class,
                nourishmentId
        );
        NourishmentResponse nourishmentResponse = nourishmentEntity.getBody();
        if (Objects.nonNull(nourishmentResponse)) {
            consumption.setNourishmentId(nourishmentResponse.getNourishmentId());
            ConsumptionProcess.process(consumption, nourishmentResponse, this.client, this.mapper, jwtTokenProvider);
        }
        this.consumptionRepository.save(consumption);
        return this.mapper.mapOutConsumptionToConsumptionResponse(consumption);
    }

}
