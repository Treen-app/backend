package com.app.treen.style.entity;

import com.app.treen.BaseTimeEntity;
import com.app.treen.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Getter
@Table(name = "styles")
public class Style extends BaseTimeEntity {

    @Builder
    public Style(Long likeCount, Long viewCount, String content, User user) {
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.content = content;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "style_id")
    private Long id;

    @Column(name = "like_count")
    private Long likeCount = 0L;

    @Column(name = "view_count")
    private Long viewCount = 0L;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void update(String content){
        this.content = content;
    }

}
