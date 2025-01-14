package com.app.treen.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 공통 컬럼
    @Column(name = "user_name", length = 50)
    private String userName;

    @Column(name = "login_id", length = 50)
    private String loginId;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "phone", length = 50)
    private String phone;

    private int gender; // 0은 남자, 1은 여자
    private LocalDateTime birthDate;
    private int height;
    private int weight;
    private String size;
    private String status; // ACTIVE, DELETED

    private String profileImgUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeStatusToActive() {
        this.status = "ACTIVE";
    }

    public void changeStatusToDeleted() {
        this.status = "DELETED";
    }
}
