package com.app.treen.products.entity;


import com.app.treen.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "transaction_likes")
public class TransLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "trans_product_id")
    private TransProduct transProduct;

    @Builder
    public TransLikes(User user, TransProduct transProduct) {
        this.user = user;
        this.transProduct = transProduct;
    }

}