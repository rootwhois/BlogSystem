package person.rootwhois.blog.mapstruct;

import org.mapstruct.Mapper;
import person.rootwhois.blog.entity.Article;
import person.rootwhois.blog.vo.ArticleVo;

import java.util.List;

/**
 * @Author: 陈广生
 * @Date: 2022/01/09/1:14 PM
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface ArticleConver {

    ArticleVo entityToVo(Article article, List<Integer> articleSorts, List<Integer> articleTags, Long commentCount);
}
