package com.app.treen.jpa.repository.style;

import com.app.treen.style.entity.Style;
import com.app.treen.style.entity.StyleScrap;
import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StyleScrapRepository extends JpaRepository<StyleScrap, Long> {
    boolean existsByUserAndStyle(User user, Style style);

    Optional<StyleScrap> findByUserAndStyle(User user, Style style);

    List<StyleScrap> findByUser(User user);
}
