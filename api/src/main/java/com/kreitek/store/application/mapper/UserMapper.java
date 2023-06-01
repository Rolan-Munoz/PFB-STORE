package com.kreitek.store.application.mapper;


import com.kreitek.store.application.dto.UserDTO;

import com.kreitek.store.domain.entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {

}
