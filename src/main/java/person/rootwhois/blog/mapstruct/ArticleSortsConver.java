package person.rootwhois.blog.mapstruct;

import org.mapstruct.Mapper;
import person.rootwhois.blog.entity.ArticleSorts;
import person.rootwhois.blog.vo.ArticleSortsVo;

import java.util.List;

/**
 * @Author: 陈广生
 * @Date: 2022/01/09/1:14 PM
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface ArticleSortsConver {

    ArticleSortsVo entityToVo(ArticleSorts articleSorts);
    List<ArticleSortsVo> entityListToVoList(List<ArticleSorts> articleSorts);
}
