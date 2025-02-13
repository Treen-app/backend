package com.app.treen.jpa.repository.style;

import com.app.treen.style.entity.Style;
import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StyleRepository extends JpaRepository<Style,Long> {
    List<Style> findByUser(User user);
}
