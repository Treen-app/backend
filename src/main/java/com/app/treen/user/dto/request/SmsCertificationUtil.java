package com.app.treen.user.dto.request;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SmsCertificationUtil {

    @Value("${spring.sms.senderNumber}")
    private String senderNumber;

    @Value("${spring.sms.apiKey}")
    private String apiKey;

    @Value("${spring.sms.apiSecret}")
    private String apiSecret;

    DefaultMessageService messageService;

    @PostConstruct
    public void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    public SingleMessageSentResponse sendSms(String to, String verification){
        Message message = new Message();
        message.setFrom(senderNumber);
        message.setTo(to);
        message.setText("[트린] 본인 확인 인증번호는 " + verification + "입니다.");
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        log.info(String.valueOf(response));
        return response;
    }
    public SingleMessageSentResponse sendRestPassword(String to, String verification){
        Message message = new Message();
        message.setFrom(senderNumber);
        message.setTo(to);
        message.setText("[트린] 임시 비밀번호는 " + verification + "입니다. 로그인 후에 비밀번호를 변경해주세요.");
        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        log.info(String.valueOf(response));
        return response;
    }
}
