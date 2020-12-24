package com.ncedu.cheetahtest.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserPaginatedDto {
    private List<UserDto> users;
    private int totalElements;

}
