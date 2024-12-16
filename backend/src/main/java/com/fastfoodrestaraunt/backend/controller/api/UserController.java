package com.fastfoodrestaraunt.backend.controller.api;

import com.fastfoodrestaraunt.backend.controller.doc.UserControllerDoc;
import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.dto.user.UserDto;
import com.fastfoodrestaraunt.backend.core.dto.user.UserUpdatingDto;
import com.fastfoodrestaraunt.backend.core.enums.sort.UserSortField;
import com.fastfoodrestaraunt.backend.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserControllerDoc {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<PageDto<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive Integer limit,
            @RequestParam(defaultValue = "phone") UserSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder,
            @RequestParam(required = false) Boolean active) {

        PageDto<UserDto> page = userService.getPageOfUsers(offset, limit, sortBy, sortOrder, active);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        UserDto user = userService.getUser(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUserById(
            @PathVariable String id,
            @RequestBody @Valid UserUpdatingDto updatingDto) {

        UserDto user = userService.updateUser(id, updatingDto);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateUserById(@PathVariable String id) {
        userService.activateUser(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateUserById(@PathVariable String id) {
        userService.deactivateUser(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
