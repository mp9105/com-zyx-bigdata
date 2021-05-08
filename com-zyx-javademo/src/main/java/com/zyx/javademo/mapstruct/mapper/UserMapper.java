package com.zyx.javademo.mapstruct.mapper;

import com.zyx.javademo.mapstruct.bean.UserDAO;
import com.zyx.javademo.mapstruct.bean.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/8 16:17
 * desc: UserDTO与UserDAO之间的转化类
 */

@Mapper(uses = {
        SexEnumIntegerMapper.class,
        StringTimestampMapper.class
})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * 网络传输对象转用户接口对象
     * @param userDTO 网络传输对象
     * @return 用户接口对象
     */
    @Mappings({
            @Mapping(source = "sex", target = "sex",qualifiedByName = {"SexEnumIntegerMapper", "integerBySexEnum"}),
            @Mapping(source = "desc", target = "remark"),
            @Mapping(source = "createTime", target = "createTime",qualifiedByName = {"StringTimestampMapper", "timestampByString"})
    })
    UserDAO toDO(UserDTO userDTO);

    List<UserDAO> toDOs(List<UserDTO> userDTOList);

    /**
     * 用户接口对象转网络传输对象
     * @param userDAO 用户接口类对象
     * @return 网络传输对象
     */
    @Mappings({
            @Mapping(source = "sex", target = "sex",qualifiedByName = {"SexEnumIntegerMapper", "sexEnumByInteger"}),
            @Mapping(source = "remark", target = "desc"),
            @Mapping(source = "createTime", target = "createTime",qualifiedByName = {"StringTimestampMapper", "stringByTimestamp"})
    })
    UserDTO toDTO(UserDAO userDAO);

    List<UserDTO> toDTOs(List<UserDAO> userDAOList);
}