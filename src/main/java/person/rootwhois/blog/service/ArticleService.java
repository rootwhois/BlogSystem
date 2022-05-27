package person.rootwhois.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import person.rootwhois.blog.entity.Admin;
import person.rootwhois.blog.entity.Article;
import person.rootwhois.blog.vo.ArticleVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
public interface ArticleService extends IService<Article> {

    Page<Article> listArticles(Integer currentPage, Integer pageSize, String query);

    Page<Article> listRecentArticles(Integer currentPage, Integer pageSize);

    ArticleVo getArticle(Integer id);

    Map<String, Object> getArticleForEdit(Integer id);

    Page<Article> listBySortId(Integer currentPage, Integer pageSize, Integer sortId);

    Page<Article> listByTagId(Integer currentPage, Integer pageSize, Integer tagId);

    Page<Article> listAllArticles(Integer currentPage, Integer pageSize, String query, String type, String id);

    boolean editArticle(Admin admin, Article article, List<Integer> articleSorts, List<Integer> articleTags);

    boolean deleteArticle(Admin admin, Integer articleId);
}
