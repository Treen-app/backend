package com.app.treen.user.entity;

import com.app.treen.mypage.dto.UpdateUserProfileDto;
import com.app.treen.products.entity.enumeration.Gender;
import com.app.treen.products.entity.enumeration.Size;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Entity
@Builder
@Table(name="user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_id")
    private Long id;

    @Column(nullable = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Size size;

    @Column(nullable = true)
    private LocalDate birthDate;

    @Column(nullable = true)
    private int height;

    @Column(nullable = true)
    private int weight;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void updateProfile(UpdateUserProfileDto dto) {
        this.nickname = dto.getNickname();
        this.gender = dto.getGender();
        this.birthDate = dto.getBirthDate();
        this.height = dto.getHeight();
        this.weight = dto.getWeight();
        this.size = dto.getSize();
    }
}
