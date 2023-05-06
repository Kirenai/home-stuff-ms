package me.kirenai.re.consumption.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.api.NourishmentManager;
import me.kirenai.re.consumption.api.UserManager;
import me.kirenai.re.consumption.dto.ConsumptionRequest;
import me.kirenai.re.consumption.dto.ConsumptionResponse;
import me.kirenai.re.consumption.dto.NourishmentRequest;
import me.kirenai.re.consumption.entity.Consumption;
import me.kirenai.re.consumption.mapper.ConsumptionMapper;
import me.kirenai.re.consumption.repository.ConsumptionRepository;
import me.kirenai.re.consumption.util.ConsumptionProcess;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumptionService {

    private final ConsumptionRepository consumptionRepository;
    private final ConsumptionMapper mapper;
    private final UserManager userManager;
    private final NourishmentManager nourishmentManager;

//    public List<ConsumptionResponse> findAll(Pageable pageable) {
//        log.info("Invoking ConsumptionService.findAll method");
//        return this.consumptionRepository.findAll(pageable)
//                .getContent()
//                .stream()
//                .map(this.mapper::mapOutConsumptionToConsumptionResponse)
//                .toList();
//    }

    public Mono<ConsumptionResponse> findOne(Long consumptionId) {
        log.info("Invoking ConsumptionService.findOne method");
        return this.consumptionRepository.findById(consumptionId)
                .map(this.mapper::mapOutConsumptionToConsumptionResponse);
    }

    public Mono<ConsumptionResponse> create(Long nourishmentId, Long userId, ConsumptionRequest consumptionRequest,
                                            String token) {
        log.info("Invoking ConsumptionService.create method");
        Consumption consumption = this.mapper.mapInConsumptionRequestToConsumption(consumptionRequest);
        return this.userManager.findUser(userId, token)
                .flatMap(userResponse -> {
                    consumption.setUserId(userResponse.getUserId());
                    return this.nourishmentManager.findNourishment(nourishmentId, token);
                })
                .flatMap(nourishmentResponse -> {
                    consumption.setNourishmentId(nourishmentResponse.getNourishmentId());
                    NourishmentRequest nourishmentRequest = ConsumptionProcess.process(consumption, nourishmentResponse, this.mapper);
                    return this.nourishmentManager.updateNourishment(nourishmentRequest, nourishmentResponse, token);
                })
                .then(this.consumptionRepository.save(consumption))
                .map(this.mapper::mapOutConsumptionToConsumptionResponse);
    }

}