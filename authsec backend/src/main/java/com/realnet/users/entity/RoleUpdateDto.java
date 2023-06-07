package com.realnet.users.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

public class RoleUpdateDto {
	
	@ApiModelProperty(notes = "User identifier", required = true)
    @NotBlank(message = "The userId is required")
    private Long userId;

    @ApiModelProperty(notes = "Array of roles to give to an user", required = true)
    @NotEmpty(message = "The field must have at least one item")
    private String[] roles;

    public Long getUserId() {
        return userId;
    }

    public RoleUpdateDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String[] getRoles() {
        return roles;
    }

    public RoleUpdateDto setRoles(String[] roles) {
        this.roles = roles;
        return this;
    }

}
