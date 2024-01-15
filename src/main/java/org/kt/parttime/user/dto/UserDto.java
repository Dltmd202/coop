package org.kt.parttime.user.dto;

import lombok.Getter;
import org.kt.parttime.user.entity.Admin;
import org.kt.parttime.user.entity.Student;
import org.kt.parttime.user.entity.User;

@Getter
public abstract class UserDto {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String viewName;

    public UserDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole().name();
        this.viewName = user.getDetailPage();
    }

}
