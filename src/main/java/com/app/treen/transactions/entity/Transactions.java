package com.app.treen.transactions.entity;

import com.app.treen.BaseTimeEntity;
import com.app.treen.chat_room.entity.ChatRoom;
import com.app.treen.products.entity.TransProduct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transactions extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactions_id")
    private Long id;

    // 대상테이블에 외래키 방식
    @OneToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom transChatRoom;

    private LocalDateTime transDate; // 거래일자
    private boolean isDirect; // 직거래 여부

    private String place; // 거래 장소(직거래시)

    private String deliveryAddress; // 택배 배송지
    private String deliveryRequest; // 택배 요청사항
    private String deliveryFeeAccount; // 택배비 송금계좌


    public void setDeliveryInfo(String deliveryAddress, String deliveryRequest, String deliveryFeeAccount) {
        this.deliveryAddress = deliveryAddress;
        this.deliveryRequest = deliveryRequest;
        this.deliveryFeeAccount = deliveryFeeAccount;
    }
}
