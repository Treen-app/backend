package com.app.treen.style.entity;

import com.app.treen.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "style_likes")
public class StyleLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "style_like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "style_id")
    private Style style;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
