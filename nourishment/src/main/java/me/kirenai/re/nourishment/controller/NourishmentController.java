package me.kirenai.re.nourishment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.nourishment.dto.NourishmentRequest;
import me.kirenai.re.nourishment.dto.NourishmentResponse;
import me.kirenai.re.nourishment.service.NourishmentService;
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
@RequestMapping("api/nourishments")
public class NourishmentController {

    private final NourishmentService nourishmentService;

    @GetMapping
    public ResponseEntity<List<NourishmentResponse>> getNourishments(
            @PageableDefault(size = 5)
            @SortDefault.SortDefaults(value = {
                    @SortDefault(sort = "nourishmentId", direction = Sort.Direction.ASC)
            }) Pageable pageable
    ) {
        log.info("Invoking NourishmentController.getNourishments method");
        List<NourishmentResponse> response = this.nourishmentService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{nourishmentId}")
    public ResponseEntity<NourishmentResponse> getNourishment(@PathVariable Long nourishmentId) {
        log.info("Invoking NourishmentController.getNourishment method");
        NourishmentResponse response = this.nourishmentService.findOne(nourishmentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("stock/{is_available}")
    public ResponseEntity<List<NourishmentResponse>> getNourishmentStockStatus(@PathVariable("is_available") boolean isAvailable) {
        log.info("Invoking NourishmentController.getNourishmentStockStatus method");
        List<NourishmentResponse> response = this.nourishmentService.findAllByIsAvailable(isAvailable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<NourishmentResponse> createNourishment(@RequestParam("userId") Long userId,
                                                                 @RequestParam("categoryId") Long categoryId,
                                                                 @RequestBody NourishmentRequest nourishmentRequest) {
        log.info("Invoking NourishmentController.createNourishment method");
        NourishmentResponse response = this.nourishmentService.create(userId, categoryId, nourishmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{nourishmentId}")
    public ResponseEntity<NourishmentResponse> updateNourishment(@PathVariable Long nourishmentId,
                                                                 @RequestBody NourishmentRequest nourishmentRequest) {
        log.info("Invoking NourishmentController.updateNourishment method");
        NourishmentResponse response = this.nourishmentService.update(nourishmentId, nourishmentRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("{nourishmentId}")
    public ResponseEntity<NourishmentResponse> deleteNourishment(@PathVariable Long nourishmentId) {
        log.info("Invoking NourishmentController.deleteNourishment method");
        this.nourishmentService.delete(nourishmentId);
        return ResponseEntity.noContent().build();
    }
}