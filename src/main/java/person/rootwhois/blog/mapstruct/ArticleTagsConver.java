package person.rootwhois.blog.mapstruct;

import org.mapstruct.Mapper;
import person.rootwhois.blog.entity.ArticleTags;
import person.rootwhois.blog.vo.ArticleTagsVo;

import java.util.List;

/**
 * @Author: 陈广生
 * @Date: 2022/01/09/1:14 PM
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface ArticleTagsConver {

    ArticleTagsVo entityToVo(ArticleTags articleTags);
    List<ArticleTagsVo> entityListToVoList(List<ArticleTags> articleTags);
}
