package person.rootwhois.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import person.rootwhois.blog.entity.Article;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
@Mapper
public interface ArticleDao extends BaseMapper<Article> {
}
