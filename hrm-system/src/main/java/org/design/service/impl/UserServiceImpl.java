package org.design.service.impl;

import org.design.mapper.UserMapper;
import org.design.model.User;
import org.design.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User get(Integer id) {
        return userMapper.get(id);
    }

    @Override
    public void save(User user) {
        userMapper.save(user);
    }
}
