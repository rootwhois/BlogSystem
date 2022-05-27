package person.rootwhois.blog.mapstruct;

import org.mapstruct.Mapper;
import person.rootwhois.blog.entity.Web;
import person.rootwhois.blog.vo.WebVo;

/**
 * @Author: 陈广生
 * @Date: 2022/01/09/1:14 PM
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface WebConver {

    WebVo entityToVo(Web web);
}
