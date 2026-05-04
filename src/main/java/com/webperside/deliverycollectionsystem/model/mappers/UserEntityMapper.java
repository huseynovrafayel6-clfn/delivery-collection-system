package com.webperside.deliverycollectionsystem.model.mappers;


import com.webperside.deliverycollectionsystem.model.entity.User;
import com.webperside.deliverycollectionsystem.model.payload.auth.SignUpPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserEntityMapper {

    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    @Mapping(target = "password", source = "encryptedPassword")
    @Mapping(target = "status", constant = "ACTIVE")
    User fromSignUpPayloadToUser(SignUpPayload payload, String encryptedPassword);

}
