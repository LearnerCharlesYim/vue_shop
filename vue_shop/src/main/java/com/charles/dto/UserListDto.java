package com.charles.dto;

import java.util.List;

public class UserListDto extends PageInfo{
    private List<UserDto> users;

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
