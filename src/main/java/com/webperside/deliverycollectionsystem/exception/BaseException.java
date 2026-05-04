package com.webperside.deliverycollectionsystem.exception;

import com.webperside.deliverycollectionsystem.exception.types.NotFoundExceptionType;
import com.webperside.deliverycollectionsystem.model.enums.response.ResponseMessages;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

import static com.webperside.deliverycollectionsystem.model.enums.response.ErrorResponseMessages.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseException extends RuntimeException {

    ResponseMessages responseMessage;
    NotFoundExceptionType notFoundData;

    @Override
    public String getMessage() {
        return responseMessage.message();
    }

    public static BaseException of(ResponseMessages responseMessage) {
        return BaseException.builder().responseMessage(responseMessage).build();
    }

    public static BaseException unexpected() {
        return of(UNEXPECTED);
    }

    public static BaseException notFound(String target, String field, Object value) {
        return BaseException.builder()
                .responseMessage(NOT_FOUND)
                .notFoundData(
                        NotFoundExceptionType.of(target, Map.of(field, value))
                )
                .build();
    }

    public static BaseException unauthorized() {
        return of(UNAUTHORIZED);
    }
}
