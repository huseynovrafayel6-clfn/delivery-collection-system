package com.webperside.deliverycollectionsystem.services.assignments;

import com.webperside.deliverycollectionsystem.exception.BaseException;
import com.webperside.deliverycollectionsystem.model.dto.AssignmentDto;
import com.webperside.deliverycollectionsystem.model.dto.ShipmentProjection;
import com.webperside.deliverycollectionsystem.model.dto.UserInfo;
import com.webperside.deliverycollectionsystem.model.entity.Assignment;
import com.webperside.deliverycollectionsystem.model.entity.Role;
import com.webperside.deliverycollectionsystem.model.entity.Shipment;
import com.webperside.deliverycollectionsystem.model.entity.User;
import com.webperside.deliverycollectionsystem.model.payload.assignment.AssignmentPayload;
import com.webperside.deliverycollectionsystem.model.response.BaseResponse;
import com.webperside.deliverycollectionsystem.model.response.assignment.AssignmentResponse;
import com.webperside.deliverycollectionsystem.model.response.shipment.ShipmentResponse;
import com.webperside.deliverycollectionsystem.model.security.LoggedInUserDetails;
import com.webperside.deliverycollectionsystem.repository.AssignmentRepository;
import com.webperside.deliverycollectionsystem.repository.ShipmentRepository;
import com.webperside.deliverycollectionsystem.repository.UserRepository;
import com.webperside.deliverycollectionsystem.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.event.ListDataEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final ShipmentRepository shipmentRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public Long addAssignment(AssignmentPayload payload) {
        return assignmentRepository.save(fromPayloadToEntity(payload, new Assignment())).getId();
    }

    @Override
    public Long updateAssignmentById(Long id, AssignmentPayload payload) {
        Assignment assignment = fromPayloadToEntity(payload, assignmentRepository.findById(id).orElseThrow(
                () -> BaseException.notFound(Assignment.class.getSimpleName(), "id", id)
        ));

        return assignmentRepository.save(assignment).getId();
    }

    @Override
    public AssignmentDto getAssignmentById(Long id) {

        return assignmentRepository.findAssignmentDtoById(id).orElseThrow(
                () -> BaseException.notFound(Assignment.class.getSimpleName(), "id", id)
        );


    }

    @Override
    public List<ShipmentResponse> getShipmentByCourierId(Long courierId) {

        List<AssignmentDto> assignmentDtos = assignmentRepository.findAssignmentsDtoByCourierId(courierId);
        List<Long> shipmentIds = new ArrayList<>();
        for(AssignmentDto assignmentDto : assignmentDtos){
            shipmentIds.add(assignmentDto.shipmentId());
        }
        List<Shipment> shipments = shipmentRepository.findAllByIdInAndIsDeletedFalse(shipmentIds);
        List<ShipmentResponse> shipmentResponses = shipments.stream().map(
          shipment -> ShipmentResponse.builder()
                  .id(shipment.getId())
                  .trackingNumber(shipment.getTrackingNumber())
                  .sender(UserInfo.builder()
                          .id(shipment.getSender().getId())
                          .name(shipment.getSender().getName())
                          .surname(shipment.getSender().getSurname())
                          .email(shipment.getSender().getEmail())
                          .phoneNumber(shipment.getSender().getPhoneNumber())
                          .build())
                  .receiverName(shipment.getReceiverName())
                  .receiverEmail(shipment.getReceiverEmail())
                  .receiverPhoneNumber(shipment.getReceiverPhoneNumber())
                  .deliveryAddress(shipment.getDeliveryAddress())
                  .weight(shipment.getWeight())
                  .dimensions(shipment.getDimensions())
                  .serviceType(shipment.getServiceType())
                  .deliveryType(shipment.getDeliveryType())
                  .notes(shipment.getNotes())
                  .status(shipment.getStatus())
                  .createdAt(shipment.getCreatedAt())
                  .updatedAt(shipment.getUpdatedAt())
                  .build()
        ).toList();

        return shipmentResponses;
    }

    @Override
    public List<AssignmentDto> getAssignmentsByCourier() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof LoggedInUserDetails loggedInUserDetails)) {
            throw BaseException.unauthorized();
        }

        User user = userService.getByEmail(loggedInUserDetails.getUsername());
        for (Role role : user.getRoles()) {
            if (role.isDeleted()) {
                throw BaseException.notFound(Role.class.getSimpleName(), "id", role.getId());
            }

            if (!(role.getName().equals("courier"))) {
                throw BaseException.notFound("Courier", "id", user.getId());
            }
        }
        List<AssignmentDto> assignmentDtos = assignmentRepository.findAssignmentsDtoByCourierId(user.getId());

        return assignmentDtos;
    }


    private Assignment fromPayloadToEntity(AssignmentPayload payload, Assignment assignment) {

        Shipment shipment = shipmentRepository.findById(payload.getShipmentId()).orElseThrow(
                () -> BaseException.notFound(Shipment.class.getSimpleName(), "id", payload.getShipmentId())
        );
        if (shipment.isDeleted()) {
            throw BaseException.notFound(Shipment.class.getSimpleName(), "id", payload.getShipmentId());
        }
        assignment.setShipment(shipment);
        User user = userRepository.findById(payload.getCourierId()).orElseThrow(
                () -> BaseException.notFound(User.class.getSimpleName(), "id", payload.getCourierId())
        );
        if (user.isDeleted()) {
            throw BaseException.notFound(User.class.getSimpleName(), "id", payload.getCourierId());
        }
        if (user.getStatus().equals("INACTIVE")) {
            throw BaseException.notFound("Courier", "id", payload.getCourierId());
        }

        for (Role role : user.getRoles()) {
            if (role.isDeleted()) {
                throw BaseException.notFound(Role.class.getSimpleName(), "id", role.getId());
            }

            if (!(role.getName().equals("courier"))) {
                throw BaseException.notFound("Courier", "id", payload.getCourierId());
            }
        }
        assignment.setCourier(user);
        assignment.setStatus(payload.getStatus());
        assignment.setAssignmentType(payload.getAssignmentType());
        return assignment;
    }

    private AssignmentResponse fromEntityToResponse(Assignment assignment) {
        AssignmentResponse assignmentResponse = new AssignmentResponse();
        assignmentResponse.setId(assignment.getId());
        assignmentResponse.setShipment(assignment.getShipment());
        assignmentResponse.setCourier(assignment.getCourier());
        assignmentResponse.setStatus(assignment.getStatus());
        assignmentResponse.setAssignmentType(assignment.getAssignmentType());
        return assignmentResponse;
    }
}
