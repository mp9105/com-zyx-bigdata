package com.zyx.javademo.mapstruct.app;

import com.zyx.javademo.mapstruct.bean.SexEnum;
import com.zyx.javademo.mapstruct.bean.UserDAO;
import com.zyx.javademo.mapstruct.bean.UserDTO;
import com.zyx.javademo.mapstruct.mapper.UserMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/8 16:28
 * desc: MapStruct测试类
 */
public class MainTest {
    private UserDTO userDTO = new UserDTO();
    private List<UserDTO> userDTOList = new ArrayList<>(2);
    private UserDAO userDAO = new UserDAO();
    private List<UserDAO> userDAOList = new ArrayList<>(2);

    @Before
    public void initUserDTO() {
        userDTO.setId("1122");
        userDTO.setDesc("这是张三");
        userDTO.setName("张三");
        userDTO.setSex(SexEnum.man);
        userDTO.setCreateTime("2020-05-06 19:00:00");

        userDAO.setId(3377L);
        userDAO.setRemark("这是李梅梅");
        userDAO.setName("李梅梅");
        userDAO.setSex(0);
        userDAO.setCreateTime(new java.sql.Timestamp(1588765009399L));

        UserDTO userDTO2 = new UserDTO();
        userDTO2.setId("2211");
        userDTO2.setDesc("这是张三2");
        userDTO2.setName("张三2");
        userDTO2.setSex(SexEnum.man);
        userDTO2.setCreateTime("2020-05-06 19:49:00");

        UserDAO userDAO2 = new UserDAO();
        userDAO2.setId(7733L);
        userDAO2.setRemark("这是李梅梅2");
        userDAO2.setName("李梅梅2");
        userDAO2.setSex(0);
        userDAO2.setCreateTime(new java.sql.Timestamp(1588766094618L));

        userDAOList.add(userDAO);
        userDAOList.add(userDAO2);

        userDTOList.add(userDTO);
        userDTOList.add(userDTO2);
    }

    /**
     * DAO -> DTO
     */
    @Test
    public void test1() {
        UserDTO userDTO1 = UserMapper.INSTANCE.toDTO(userDAO);
        // UserDTO(id=3377, name=李梅梅, sex=woman, desc=这是李梅梅, createTime=2020-05-06 19:36:49)
        System.out.println(userDTO1.toString());
    }

    /**
     * DTO -> DAO
     */
    @Test
    public void test2() {
        UserDAO userDAO1 = UserMapper.INSTANCE.toDO(userDTO);
        // UserDAO(id=1122, name=张三, sex=1, remark=这是张三, createTime=2020-05-06 19:00:00.0)
        System.out.println(userDAO1.toString());
    }

    /**
     * List<DAO> -> List<DTO>
     */
    @Test
    public void test3() {
        List<UserDTO> userDTOList1 = UserMapper.INSTANCE.toDTOs(userDAOList);
        /**
         * UserDTO(id=3377, name=李梅梅, sex=woman, desc=这是李梅梅, createTime=2020-05-06 19:36:49)
         * UserDTO(id=7733, name=李梅梅2, sex=woman, desc=这是李梅梅2, createTime=2020-05-06 19:54:54)
         */
        userDTOList1.stream().forEach(x -> System.out.println(x));
    }

    /**
     * List<DTO> -> List<DAO>
     */
    @Test
    public void test4() {
        List<UserDAO> userDAOList1 = UserMapper.INSTANCE.toDOs(userDTOList);
        /**
         * UserDAO(id=1122, name=张三, sex=1, remark=这是张三, createTime=2020-05-06 19:00:00.0)——————这里的格式是TimeStamp的toString方法默认的实现，与本次转换无关
         * UserDAO(id=2211, name=张三2, sex=1, remark=这是张三2, createTime=2020-05-06 19:49:00.0)
         */
        userDAOList1.stream().forEach(x -> System.out.println(x));
        userDAOList1.stream().forEach(x -> System.out.println(x.getCreateTime()));
    }
}
