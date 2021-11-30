package com.stephani.digiwallet.payload.response;

import com.stephani.digiwallet.elasticsearch.model.EsUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ListUserResponse extends BaseResponse{

    private List<EsUser> users;

    public ListUserResponse(String message, String code) {
        super(message, code);
    }

    public ListUserResponse(List<EsUser> users, String message, String code) {
        super(message, code);
        this.users = users;
    }
}
