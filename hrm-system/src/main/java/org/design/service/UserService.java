package org.design.service;

import org.design.model.User;

public interface UserService {

    User get(Integer id);

    void save(User user);
}
