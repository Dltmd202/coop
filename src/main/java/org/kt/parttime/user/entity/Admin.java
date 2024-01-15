package org.kt.parttime.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kt.parttime.user.dto.AdminEditForm;
import org.kt.parttime.user.service.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Admin extends User{

    @Column(length = 20)
    private String position;

    public Admin(String name, String email, String password, String position) {
        super(name, email, password, UserRole.ADMIN);
        this.position = position;
    }

    @Override
    public String getDetailPage() {
        return "admin/adminDetail";
    }

    public void update(AdminEditForm form, PasswordEncoder passwordEncoder) {
        super.update(form.getName(), form.getEmail(), form.getPassword(), passwordEncoder);
        this.position = form.getPosition();
    }
}
