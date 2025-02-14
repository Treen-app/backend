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
@Table(name = "follower")
public class Follower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follower_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 팔로우된 사용자

    @ManyToOne
    @JoinColumn(name = "follower_user_id")
    private User followerUser; // 팔로우한 사용자
}
