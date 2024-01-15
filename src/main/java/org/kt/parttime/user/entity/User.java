package org.kt.parttime.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kt.parttime.common.entity.BaseTimeEntity;
import org.kt.parttime.user.service.PasswordEncoder;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = PROTECTED)
public abstract class User extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    protected String name;

    @Column(unique = true, nullable = false, length = 255)
    protected String email;

    @Column(nullable = false)
    protected String password;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String name, String email, String password, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void signup(PasswordEncoder encoder){
        this.password = encoder.encode(password);
    }

    public boolean login(PasswordEncoder passwordEncoder, String password) {
        return !passwordEncoder.matches(password, this.password);
    }

    public abstract String getDetailPage();

    public void update(String name, String email, String password, PasswordEncoder encoder){
        this.name = name;
        this.email = email;
        this.password = encoder.encode(password);
    }
}
