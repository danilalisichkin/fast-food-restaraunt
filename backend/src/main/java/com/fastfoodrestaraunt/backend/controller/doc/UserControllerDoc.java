package com.fastfoodrestaraunt.backend.controller.doc;

import com.fastfoodrestaraunt.backend.core.dto.pagination.PageDto;
import com.fastfoodrestaraunt.backend.core.dto.user.UserDto;
import com.fastfoodrestaraunt.backend.core.dto.user.UserUpdatingDto;
import com.fastfoodrestaraunt.backend.core.enums.sort.UserSortField;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@SecurityRequirement(name = "token")
@Tag(name = "User API Controller", description = "Provides CRUD-operations with users")
public interface UserControllerDoc {

    @Operation(
            summary = "Get page of users",
            description = "Allows to get page of users")
    ResponseEntity<PageDto<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer offset,
            @RequestParam(defaultValue = "10") @Positive Integer limit,
            @RequestParam(defaultValue = "phone") UserSortField sortBy,
            @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder,
            @RequestParam(required = false) Boolean active);

    @Operation(
            summary = "Get user",
            description = "Allows to get user")
    ResponseEntity<UserDto> getUserById(
            @Parameter(
                    description = "Identifier of user")
            @PathVariable String id);

    @Operation(
            summary = "Update user",
            description = "Allows to update user")
    ResponseEntity<UserDto> updateUserById(
            @Parameter(
                    description = "Identifier of user")
            @PathVariable String id,
            @Parameter(
                    name = "Required date",
                    description = "User data that will be changed", required = true)
            @RequestBody @Valid UserUpdatingDto updatingDto);

    @Operation(
            summary = "Activate user",
            description = "Allows to activate user")
    ResponseEntity<Void> activateUserById(
            @Parameter(
                    description = "Identifier of user")
            @PathVariable String id);

    @Operation(
            summary = "Deactivate user",
            description = "Allows to deactivate user")
    ResponseEntity<Void> deactivateUserById(
            @Parameter(
                    description = "Identifier of user")
            @PathVariable String id);
}
