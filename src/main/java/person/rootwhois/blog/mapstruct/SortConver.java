package person.rootwhois.blog.mapstruct;

import org.mapstruct.Mapper;
import person.rootwhois.blog.entity.Sort;
import person.rootwhois.blog.vo.SortVo;

/**
 * @Author: 陈广生
 * @Date: 2022/01/09/1:14 PM
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface SortConver {

    SortVo entityToVo(Sort sort, Long articleCount);
}
