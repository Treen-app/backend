package com.app.treen.user.entity;

import com.app.treen.mypage.dto.UpdateUserProfileDto;
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

    private String nickname;
    private String gender;
    private LocalDate birthDate;
    private int height;
    private int weight;
    private int footSize;
    private String clothingSize;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 프로필 업데이트 메서드
    public void updateProfile(UpdateUserProfileDto dto) {
        this.nickname = dto.getNickname();
        this.gender = dto.getGender();
        this.birthDate = LocalDate.parse(dto.getBirthDate());
        this.height = dto.getHeight();
        this.weight = dto.getWeight();
        this.footSize = dto.getFootSize();
        this.clothingSize = dto.getClothingSize();
    }
}
