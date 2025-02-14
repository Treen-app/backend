package com.app.treen.transactions.service;

import com.app.treen.transactions.transactions_chat.chat_room.entity.ChatRoom;
import com.app.treen.common.response.code.status.ErrorStatus;
import com.app.treen.common.response.exception.CustomException;
import com.app.treen.jpa.repository.transactions.ChatRoomRepository;
import com.app.treen.jpa.repository.transactions.TransactionsRepository;
import com.app.treen.products.entity.enumeration.Status;
import com.app.treen.transactions.dto.request.TransactionsSaveRequestDto;
import com.app.treen.transactions.dto.response.TransactionsResponseDto;
import com.app.treen.transactions.entity.Transactions;
import com.app.treen.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;
    private final ChatRoomRepository chatRoomRepository;

    // 상품거래 예약 생성
    @Transactional
    public long createTransactions(User user, TransactionsSaveRequestDto dto) {

        ChatRoom findChatRoom = chatRoomRepository.findById(dto.getTransChatRoomId())
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_CHAT_ROOM));

        Transactions transactions = Transactions.builder()
                .transChatRoom(findChatRoom)
                .transDate(dto.getTransDate())
                .isDirect(dto.isDirect())
                .place(dto.getPlace())
                .build();

        if (!dto.isDirect()) {
            transactions.setDeliveryInfo(dto.getDeliveryAddress(), dto.getDeliveryRequest(), dto.getDeliveryFeeAccount());
        }

        // 상품 거래 상태 변경
        transactions.getTransChatRoom().getTransProduct().bookTransaction();

        return transactionsRepository.save(transactions).getId();
    }

    // 상품거래 예약 조회
    public TransactionsResponseDto getTransactions(Long transactionsId) {
        Transactions transactions = transactionsRepository.findById(transactionsId)
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_TRANSACTIONS));

        return TransactionsResponseDto.builder()
                .id(transactions.getId())
                .transDate(transactions.getTransDate())
                .isDirect(transactions.isDirect())
                .place(transactions.getPlace())
                .deliveryAddress(transactions.getDeliveryAddress())
                .deliveryRequest(transactions.getDeliveryRequest())
                .deliveryFeeAccount(transactions.getDeliveryFeeAccount())
                .build();
    }

    // 상품거래 예약 취소
    @Transactional
    public void cancelTransactions(User user, Long transactionsId) {

        Transactions transactions = transactionsRepository.findById(transactionsId)
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_TRANSACTIONS));

        checkEditTransactions(user, transactions);

        // 상품 상태 변경
        transactions.getTransChatRoom().getTransProduct().cancelTransaction();
        // 상품거래 예약 삭제
        transactionsRepository.delete(transactions);

    }

    // 거래 완료
    @Transactional
    public void completeTransactions(Long transactionsId) {

        Transactions transactions = transactionsRepository.findById(transactionsId)
                .orElseThrow(() -> new CustomException(ErrorStatus.NOT_FOUND_TRANSACTIONS));

        // 상품 상태 변경
        transactions.getTransChatRoom().getTransProduct().completeTransaction();
    }

    private void checkEditTransactions(User user, Transactions transactions) {

        // 예약한 거래 상품의 판매자만 예약 취소 가능
        if (!transactions.getTransChatRoom().getSeller().getId().equals(user.getId())) {
            throw new CustomException(ErrorStatus.PERMISSION_DENIED_TRANSACTIONS);
        }

        if (!transactions.getTransChatRoom().getTransProduct().getTransactionStatus().equals(Status.BOOKED)) {
            throw new CustomException(ErrorStatus.SHOULD_BE_BOOKED);
        }
    }

}
