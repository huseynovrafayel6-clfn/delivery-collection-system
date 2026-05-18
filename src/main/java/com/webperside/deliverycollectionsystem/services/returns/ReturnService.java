package com.webperside.deliverycollectionsystem.services.returns;

import com.webperside.deliverycollectionsystem.model.entity.Return;
import com.webperside.deliverycollectionsystem.model.payload.returns.ReturnPayload;
import com.webperside.deliverycollectionsystem.model.response.returns.ReturnResponse;
import org.springframework.data.domain.Page;

public interface ReturnService {

    Long initiateReturn(ReturnPayload payload);

    Long approveReturn(Long id);

    Long inTransitReturn(Long id);

    Long completeReturn(Long id);

    Long cancelReturn(Long id);

    ReturnResponse getById(Long id);

    Page<ReturnResponse> getAll(int page, int size);

    Return findById(Long id);

}
