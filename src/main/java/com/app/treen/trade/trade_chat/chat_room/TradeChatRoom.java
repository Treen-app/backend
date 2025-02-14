package com.app.treen.trade.trade_chat.chat_room;

import com.app.treen.BaseTimeEntity;
import com.app.treen.products.entity.TradeProduct;
import com.app.treen.products.entity.TransProduct;
import com.app.treen.user.entity.User;
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
public class TradeChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_chat_room_id")
    private Long id;
    private String title;
    private boolean isReserved; // 거래 예약 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_product_id")
    private TradeProduct transProduct;

    @Builder
    public TradeChatRoom(String title, boolean isReserved) {
        this.title = title;
        this.isReserved = isReserved;
    }

    public User getOtherMember(Long memberId) {
        if (buyer.getId().equals(memberId)) {
            return buyer;
        }

        return seller;
    }

    // 거래예약 생성 여부
    public void updateReservation() {
        this.isReserved = true;
    }
}
