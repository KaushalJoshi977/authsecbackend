package com.realnet.userDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleUpdateDto {
	
    private Long userId;

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
