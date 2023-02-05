package me.kirenai.re.nourishment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.nourishment.dto.ApiResponse;
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
import java.util.Objects;

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
            }) Pageable pageable, @RequestParam(value = "userId", required = false) Long userId
    ) {
        log.info("Invoking NourishmentController.getNourishments method");
        if (Objects.nonNull(userId)) {
            List<NourishmentResponse> response = this.nourishmentService.findAllByUserId(userId);
            return ResponseEntity.ok(response);
        }
        List<NourishmentResponse> response = this.nourishmentService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{nourishmentId}")
    public ResponseEntity<NourishmentResponse> getNourishment(@PathVariable Long nourishmentId) {
        log.info("Invoking NourishmentController.getNourishment method");
        NourishmentResponse response = this.nourishmentService.findOne(nourishmentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("stock/{is_available}")
    public ResponseEntity<List<NourishmentResponse>> getNourishmentStockStatus(@PathVariable("is_available") Boolean isAvailable) {
        log.info("Invoking NourishmentController.getNourishmentStockStatus method");
        List<NourishmentResponse> response = this.nourishmentService.findAllByIsAvailable(isAvailable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<NourishmentResponse>> createNourishment(@RequestParam("userId") Long userId,
                                                                              @RequestParam("categoryId") Long categoryId,
                                                                              @RequestBody @Valid NourishmentRequest nourishmentRequest) {
        log.info("Invoking NourishmentController.createNourishment method");
        NourishmentResponse response = this.nourishmentService.create(userId, categoryId, nourishmentRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<NourishmentResponse>builder().response(response).message("Successfully created").build());
    }

    @PutMapping("{nourishmentId}")
    public ResponseEntity<ApiResponse<NourishmentResponse>> updateNourishment(@PathVariable Long nourishmentId,
                                                                              @RequestBody @Valid NourishmentRequest nourishmentRequest) {
        log.info("Invoking NourishmentController.updateNourishment method");
        NourishmentResponse response = this.nourishmentService.update(nourishmentId, nourishmentRequest);
        return ResponseEntity.ok(ApiResponse.<NourishmentResponse>builder().response(response).message("Successfully updated").build());
    }

    @DeleteMapping("{nourishmentId}")
    public ResponseEntity<NourishmentResponse> deleteNourishment(@PathVariable Long nourishmentId) {
        log.info("Invoking NourishmentController.deleteNourishment method");
        this.nourishmentService.delete(nourishmentId);
        return ResponseEntity.noContent().build();
    }

}