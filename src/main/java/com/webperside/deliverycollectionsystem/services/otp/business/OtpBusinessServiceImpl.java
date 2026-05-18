package com.webperside.deliverycollectionsystem.services.otp.business;

import com.webperside.deliverycollectionsystem.model.entity.Otp;
import com.webperside.deliverycollectionsystem.services.otp.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpBusinessServiceImpl implements OtpBusinessService {

    private final OtpService otpService;

    @Override
    public Long generateOtp() {
        String otp = String.valueOf(
                ThreadLocalRandom.current().nextInt(100000, 999999)
        );

        return otpService.save(
                Otp.builder()
                        .otpCode(otp)
                        .verified(false)
                        .attemptCount(0)
                        .expiresAt(LocalDateTime.now().plusMinutes(5))
                        .createdAt(LocalDateTime.now())
                        .build()
        ).getId();
    }
}
