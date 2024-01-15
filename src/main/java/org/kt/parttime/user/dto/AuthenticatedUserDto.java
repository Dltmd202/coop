package org.kt.parttime.user.dto;

import lombok.Getter;
import org.kt.parttime.user.entity.User;
import org.kt.parttime.user.entity.UserRole;

import java.util.Objects;

@Getter
public class AuthenticatedUserDto {
    private Long userId;
    private String  role;
    private String name;

    public AuthenticatedUserDto(User user){
        userId = user.getId();
        role = user.getRole().name();
        name = user.getName();
    }

    public boolean isAdmin(){
        return Objects.equals(role, "ADMIN");
    }
}
