package com.app.treen.transactions.controller;

import com.app.treen.transactions.dto.request.TransactionsSaveRequestDto;
import com.app.treen.transactions.dto.response.TransactionsResponseDto;
import com.app.treen.transactions.service.TransactionsService;
import com.app.treen.user.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;

    // 상품거래 예약 생성
    @PostMapping("/auth/save")
    public ResponseEntity<TransactionsResponseDto> saveTransactions(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                    TransactionsSaveRequestDto transactionsSaveRequestDto) {

        Long transactionsId = transactionsService.createTransactions(userDetails.getUser(), transactionsSaveRequestDto);
        TransactionsResponseDto responseDto = transactionsService.getTransactions(transactionsId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 상품거래 예약 취소
    @DeleteMapping("/auth/delete/{transactionsId}")
    public ResponseEntity<Void> deleteTransactions(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                   @PathVariable("transactionsId") Long transactionsId) {

        transactionsService.cancelTransactions(userDetails.getUser(), transactionsId);

        return ResponseEntity.ok().build();
    }

}
