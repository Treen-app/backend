package com.app.treen.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private Long id;

    // 공통 컬럼
    private String userName;
    private String loginId;
    private String password;
    private String nickname;
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
