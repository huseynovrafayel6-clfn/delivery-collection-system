package com.webperside.deliverycollectionsystem.services.otp;

import com.webperside.deliverycollectionsystem.model.entity.Otp;
import com.webperside.deliverycollectionsystem.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService{

    private final OtpRepository otpRepository;

    @Override
    public Otp save(Otp otp) {
        return otpRepository.save(otp);
    }

}
