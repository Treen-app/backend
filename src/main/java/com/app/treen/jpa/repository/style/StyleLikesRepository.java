package com.app.treen.jpa.repository.style;

import com.app.treen.style.entity.Style;
import com.app.treen.style.entity.StyleLikes;
import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StyleLikesRepository extends JpaRepository<StyleLikes,Long> {
    boolean existsByUserAndStyle(User user, Style style);

    StyleLikes findByUserAndStyle(User user, Style style);
}
