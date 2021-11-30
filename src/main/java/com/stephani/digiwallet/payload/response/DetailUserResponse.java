package com.stephani.digiwallet.payload.response;

import com.stephani.digiwallet.entity.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class DetailUserResponse extends BaseResponse {

    private UserDto user;

    public DetailUserResponse(String message, String code) {
        super(message, code);
    }

    public DetailUserResponse(UserDto user, String message, String code) {
        super(message, code);
        this.user = user;
    }
}
