package com.webperside.deliverycollectionsystem.services.proof_of_deliveries;

import com.webperside.deliverycollectionsystem.repository.ProofOfDeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProofOfDeliveryServiceImpl implements ProofOfDeliveryService{

    private final ProofOfDeliveryRepository proofOfDeliveryRepository;



}
