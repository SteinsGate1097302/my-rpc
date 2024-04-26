package com.ltl.provider.service.impl;

import com.ltl.provider.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public String getNickname(String username) {
        return "暴龙战士";
    }
}
