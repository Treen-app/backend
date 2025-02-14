package com.app.treen.style.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "style_images")
public class StyleImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "style_image_id")
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    private boolean isMain;

    private int order;

    @ManyToOne
    @JoinColumn(name = "style_id")
    private Style style;
}
