package org.design.mapper;

import org.design.model.User;

public interface UserMapper {

    User get(Integer id);

    void save(User user);
}
