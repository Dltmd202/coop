package org.kt.parttime.user.dto;

import lombok.Getter;
import org.kt.parttime.user.entity.Admin;

@Getter
public class AdminDto extends UserDto{
    private String position;

    public AdminDto(Admin user) {
        super(user);
        this.position = user.getPosition();
    }
}
