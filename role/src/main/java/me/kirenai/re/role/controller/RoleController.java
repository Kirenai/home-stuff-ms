package me.kirenai.re.role.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kirenai.re.role.dto.ApiResponse;
import me.kirenai.re.role.dto.RoleRequest;
import me.kirenai.re.role.dto.RoleResponse;
import me.kirenai.re.role.service.RoleService;
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
@RequestMapping("api/v0/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getRoles(
            @PageableDefault(size = 2)
            @SortDefault.SortDefaults(value = {
                    @SortDefault(sort = "roleId", direction = Sort.Direction.ASC)
            }) Pageable pageable
    ) {
        log.info("Invoking RoleController.getRoles method");
        List<RoleResponse> response = this.roleService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{roleId}")
    public ResponseEntity<RoleResponse> getRole(@PathVariable Long roleId) {
        log.info("Invoking RoleController.getRole method");
        RoleResponse response = this.roleService.findOne(roleId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@RequestBody @Valid RoleRequest roleDto) {
        log.info("Invoking RoleController.createRole method");
        RoleResponse response = this.roleService.create(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<RoleResponse>builder().response(response).build());
    }

    @PutMapping("{roleId}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(@PathVariable Long roleId,
                                                                @RequestBody @Valid RoleRequest roleDto) {
        log.info("Invoking RoleController.updateRole method");
        RoleResponse response = this.roleService.update(roleId, roleDto);
        return ResponseEntity.ok(ApiResponse.<RoleResponse>builder().response(response).build());
    }

}