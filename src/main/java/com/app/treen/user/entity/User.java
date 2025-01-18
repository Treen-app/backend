package com.app.treen.user.entity;

import com.app.treen.products.entity.enumeration.Gender;
import com.app.treen.products.entity.enumeration.Size;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Column(name = "login_id", unique = true, nullable = false)
    private String loginId;
    private String password;
    @Column(name = "phone_num", unique = true, nullable = false)
    private String phoneNum;

    @Enumerated(EnumType.STRING)
    private UserStatus status; // ACTIVE, DELETED, DEACTIVATED

    @Setter
    @Column(name = "profile_img_url")
    private String profileImgUrl;

    @Builder.Default
    private Long point = 1000L;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "member_roles", joinColumns = @JoinColumn(name = "member_id"))
    @Enumerated(EnumType.STRING)
    private List<RoleType> roles = new ArrayList<>();


    public void changePassword(String password) {
        this.password = password;
    }

    public void changeStatusToActive() {
        this.status = UserStatus.ACTIVE;
    }

    public void changeStatusToDeleted() {
        this.status = UserStatus.DELETED;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

}

