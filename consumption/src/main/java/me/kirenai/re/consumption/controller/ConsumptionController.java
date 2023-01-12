package me.kirenai.re.consumption.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.consumption.dto.ConsumptionRequest;
import me.kirenai.re.consumption.dto.ConsumptionResponse;
import me.kirenai.re.consumption.service.ConsumptionService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/consumptions")
public class ConsumptionController {

    private final ConsumptionService consumptionService;

    @GetMapping
    public ResponseEntity<List<ConsumptionResponse>> getConsumptions(
            @PageableDefault(size = 3)
            @SortDefault.SortDefaults(value = {
                    @SortDefault(sort = "consumptionId", direction = Sort.Direction.ASC)
            }) Pageable pageable
    ) {
        log.info("Invoking ConsumptionController.getConsumptions method");
        List<ConsumptionResponse> response = this.consumptionService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{consumptionId}")
    public ResponseEntity<ConsumptionResponse> getConsumption(@PathVariable Long consumptionId) {
        log.info("Invoking ConsumptionController.getConsumption method");
        ConsumptionResponse response = this.consumptionService.findOne(consumptionId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ConsumptionResponse> createConsumption(@RequestParam("userId") Long userId,
                                                                 @RequestParam("nourishmentId") Long nourishmentId,
                                                                 @RequestBody ConsumptionRequest consumptionRequest) {
        log.info("Invoking ConsumptionController.createConsumption method");
        ConsumptionResponse response = this.consumptionService.create(nourishmentId, userId, consumptionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
