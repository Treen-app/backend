package com.app.treen.mypage.entity;

import com.app.treen.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "following")
public class Following {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "following_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 팔로우를 한 사용자

    @ManyToOne
    @JoinColumn(name = "following_user_id")
    private User followingUser; // 팔로우된 사용자
}
