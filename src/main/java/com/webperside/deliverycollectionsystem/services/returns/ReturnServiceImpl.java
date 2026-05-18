package com.webperside.deliverycollectionsystem.services.returns;

import com.webperside.deliverycollectionsystem.exception.BaseException;
import com.webperside.deliverycollectionsystem.model.entity.Return;
import com.webperside.deliverycollectionsystem.model.entity.Shipment;
import com.webperside.deliverycollectionsystem.model.entity.User;
import com.webperside.deliverycollectionsystem.model.enums.returns.ReturnStatus;
import com.webperside.deliverycollectionsystem.model.payload.returns.ReturnPayload;
import com.webperside.deliverycollectionsystem.model.response.returns.ReturnResponse;
import com.webperside.deliverycollectionsystem.model.security.LoggedInUserDetails;
import com.webperside.deliverycollectionsystem.repository.ReturnRepository;
import com.webperside.deliverycollectionsystem.repository.ShipmentRepository;
import com.webperside.deliverycollectionsystem.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.webperside.deliverycollectionsystem.model.enums.returns.ReturnStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReturnServiceImpl implements ReturnService{

    private final ReturnRepository returnRepository;
    private final ShipmentRepository shipmentRepository;
    private final UserService userService;

    @Override
    public Long initiateReturn(ReturnPayload payload) {

        Shipment shipment = shipmentRepository.findById(payload.getShipmentId()).orElseThrow(
                () -> BaseException.notFound(Shipment.class.getSimpleName(),"shipmentId", payload.getShipmentId()));
        if (shipment.isDeleted()){
            throw BaseException.notFound(Shipment.class.getSimpleName(),"shipmentId", payload.getShipmentId());
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof LoggedInUserDetails loggedInUserDetails)) {
            throw BaseException.unauthorized();
        }
        User user = userService.getByEmail(loggedInUserDetails.getUsername());

        Return returnObj = Return.builder()
                .shipment(shipment)
                .reason(payload.getReason())
                .notes(payload.getNotes())
                .status(ReturnStatus.INITIATED)
                .initiatedAt(LocalDateTime.now())
                .initiatedBy(user)
                .build();

        return returnRepository.save(returnObj).getId();
    }

    @Override
    public Long approveReturn(Long id) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof LoggedInUserDetails loggedInUserDetails)) {
            throw BaseException.unauthorized();
        }
        User user = userService.getByEmail(loggedInUserDetails.getUsername());

        Return returnObj = findById(id);
        returnObj.setStatus(APPROVED);
        returnObj.setApprovedAt(LocalDateTime.now());
        returnObj.setApprovedBy(user);

        return returnRepository.save(returnObj).getId();
    }

    @Override
    public Long inTransitReturn(Long id) {

        Return returnObj = findById(id);
        returnObj.setStatus(IN_TRANSIT);
        returnObj.setDeliveredToSenderAt(LocalDateTime.now());

        return returnRepository.save(returnObj).getId();
    }

    @Override
    public Long completeReturn(Long id) {
        Return returnObj = findById(id);
        returnObj.setStatus(COMPLETED);

        return returnRepository.save(returnObj).getId();
    }

    @Override
    public Long cancelReturn(Long id) {
        Return returnObj = findById(id);
        returnObj.setStatus(CANCELLED);

        return returnRepository.save(returnObj).getId();
    }

    @Override
    public ReturnResponse getById(Long id) {

        Return returnObj = findById(id);

        return ReturnResponse.builder()
                .id(returnObj.getId())
                .shipmentId(returnObj.getShipment() != null
                        ? returnObj.getShipment().getId()
                        : null)
                .reason(returnObj.getReason())
                .notes(returnObj.getNotes())
                .status(returnObj.getStatus())
                .initiatedAt(returnObj.getInitiatedAt())
                .approvedAt(returnObj.getApprovedAt())
                .deliveredToSenderAt(returnObj.getDeliveredToSenderAt())
                .completedAt(returnObj.getCompletedAt())
                .initiatedBy(returnObj.getInitiatedBy() != null
                        ? returnObj.getInitiatedBy().getId()
                        : null)
                .approvedBy(returnObj.getApprovedBy() != null
                        ? returnObj.getApprovedBy().getId()
                        : null)
                .createdAt(returnObj.getCreatedAt())
                .updatedAt(returnObj.getUpdatedAt())
                .build();
    }

    @Override
    public Page<ReturnResponse> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Return> returnPage = returnRepository.findByIsDeletedFalse(pageable);

        return returnPage.map(returnObj -> {
            ReturnResponse returnResponse = new ReturnResponse();
            returnResponse.setId(returnObj.getId());
            returnResponse.setShipmentId(returnObj.getShipment() != null
                    ? returnObj.getShipment().getId()
                    : null);
            returnResponse.setReason(returnObj.getReason());
            returnResponse.setNotes(returnObj.getNotes());
            returnResponse.setInitiatedAt(returnObj.getInitiatedAt());
            returnResponse.setApprovedAt(returnObj.getApprovedAt());
            returnResponse.setDeliveredToSenderAt(returnObj.getDeliveredToSenderAt());
            returnResponse.setCompletedAt(returnObj.getCompletedAt());
            returnResponse.setInitiatedBy(returnObj.getInitiatedBy() != null
                    ? returnObj.getInitiatedBy().getId()
                    : null);
            returnResponse.setApprovedBy(returnObj.getApprovedBy() != null
                    ? returnObj.getApprovedBy().getId()
                    : null);
            returnResponse.setCreatedAt(returnObj.getCreatedAt());
            returnResponse.setUpdatedAt(returnObj.getUpdatedAt());
            return returnResponse;
        });
    }

    @Override
    public Return findById(Long id) {
        Return returnObj = returnRepository.findById(id).orElseThrow(
                () -> BaseException.notFound(Return.class.getSimpleName(), "returnId", id)
        );
        if (returnObj.isDeleted())
            throw BaseException.notFound(Return.class.getSimpleName(), "returnId", id);

        return returnObj;

    }

}
