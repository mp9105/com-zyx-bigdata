package com.zyx.javademo.mapstructdemo.mapper;




import com.zyx.javademo.mapstructdemo.bean.EmpDAO;
import com.zyx.javademo.mapstructdemo.bean.EmpDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Yaxi.Zhang
 * @since 2021/5/8 17:00
 * desc:
 */
@Mapper(uses = {TsDtfMapper.class, DoubleStringMapper.class})
public interface EmpMapper {
    EmpMapper INSTANCE = Mappers.getMapper(EmpMapper.class);

    @Mappings({
            @Mapping(source = "price", target = "salary", qualifiedByName = {"DoubleStringMapper", "stringByDouble"}),
            @Mapping(source = "ts", target = "dt", qualifiedByName = {"TsDtfMapper", "dtfByTs"})
    })
    EmpDTO toEmpDTO(EmpDAO empDAO);
//     @Mappings(
//             @Mapping(source = "price", target = "aa")
//     )
//     UserDTO toDTO(UserDAO userDAO);
}
