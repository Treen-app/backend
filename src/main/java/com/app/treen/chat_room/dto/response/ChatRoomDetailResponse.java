package com.app.treen.chat_room.dto.response;

import com.app.treen.message.document.Message;
import com.app.treen.products.entity.TransProduct;
import com.app.treen.products.entity.enumeration.Gender;
import com.app.treen.products.entity.enumeration.Method;
import com.app.treen.products.entity.enumeration.Size;
import com.app.treen.products.entity.enumeration.UsedRank;
import com.app.treen.transactions.entity.Transactions;
import com.app.treen.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ChatRoomDetailResponse {

    private Long chatRoomId;
    private List<MessageDto> messages;
    private MemberDto buyer;
    private MemberDto seller;
    private TransProductDto transProduct;
    private TransactionsDto transaction;

    @Data
    @Builder
    public static class MemberDto {
        private Long id;
        private String name;

        public static MemberDto from(User user) {
            return MemberDto.builder()
                    .id(user.getId())
                    .name(user.getUserName())
                    .build();
        }
    }

    @Data
    @Builder
    public static class MessageDto {
        private Long senderId;
        private String type;
        private String content;

        @JsonFormat(pattern = "yyyy-MM-dd-HH-mm-ss")
        private LocalDateTime sendDate;

        public static MessageDto from(Message message) {
            return MessageDto.builder()
                    .senderId(message.getWriterId())
                    .type(message.getType())
                    .content(message.getContent())
                    .sendDate(message.getCreatedDate())
                    .build();
        }
    }

    @Data
    @Builder
    public static class TransProductDto {
        private Long id;
        private String name;
        private String usedTerm;
        private Size size;
        private UsedRank usedRank;
        private Long point;
        private Method method;
        private String category;
        private String imageUrls;

        public static TransProductDto from(TransProduct transProduct) {
            return TransProductDto.builder()
                    .id(transProduct.getId())
                    .name(transProduct.getName())
                    .usedTerm(transProduct.getUsedTerm())
                    .size(transProduct.getSize())
                    .point(transProduct.getPoint())
                    .method(transProduct.getMethod())
                    .category(transProduct.getCategory().toString())
                    //.imageUrls(transProduct.)
                    .build();
        }
    }

    @Data
    @Builder
    public static class TransactionsDto {

        private Long id;
        private LocalDateTime transDate;
        private boolean isDirect;
        private String place;
        private String deliveryAddress;
        private String deliveryRequest;
        private String deliveryFeeAccount;

        public static TransactionsDto from(Transactions transaction) {
            return TransactionsDto.builder()
                    .id(transaction.getId())
                    .transDate(transaction.getTransDate())
                    .isDirect(transaction.isDirect())
                    .place(transaction.getPlace())
                    .deliveryAddress(transaction.getDeliveryAddress())
                    .deliveryRequest(transaction.getDeliveryRequest())
                    .deliveryFeeAccount(transaction.getDeliveryFeeAccount())
                    .build();
        }
    }

}
