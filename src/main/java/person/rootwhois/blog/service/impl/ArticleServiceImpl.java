package person.rootwhois.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import person.rootwhois.blog.dao.ArticleDao;
import person.rootwhois.blog.dao.ArticleSortsDao;
import person.rootwhois.blog.dao.ArticleTagsDao;
import person.rootwhois.blog.dao.CommentDao;
import person.rootwhois.blog.entity.*;
import person.rootwhois.blog.mapstruct.ArticleConver;
import person.rootwhois.blog.service.ArticleService;
import person.rootwhois.blog.vo.ArticleVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

    @Autowired
    ArticleDao articleDao;

    @Autowired
    ArticleSortsDao articleSortsDao;

    @Autowired
    ArticleTagsDao articleTagsDao;

    @Autowired
    CommentDao commentDao;

    @Resource
    ArticleConver articleConver;

    @Override
    public Page<Article> listArticles(Integer currentPage, Integer pageSize, String query) {

        Page<Article> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>()
                .and(i -> i.like("article_content", query)
                        .or()
                        .like("article_title", query)
                        .or()
                        .like("article_description", query))
                .eq("article_status", 0)
                .orderByDesc("article_top", "article_id", "create_time");

        articleDao.selectPage(page, wrapper);
        page.convert(article -> {

            QueryWrapper<ArticleSorts> articleSortsQueryWrapper = new QueryWrapper<>();
            articleSortsQueryWrapper.eq("article_id", article.getArticleId());
            List<ArticleSorts> articleSortsList = articleSortsDao.selectList(articleSortsQueryWrapper);
            ArrayList<Integer> articleSrots = new ArrayList<>();
            articleSortsList.forEach(articleSort -> articleSrots.add(articleSort.getSortId()));

            QueryWrapper<ArticleTags> articleTagsQueryWrapper = new QueryWrapper<>();
            articleTagsQueryWrapper.eq("article_id", article.getArticleId());
            List<ArticleTags> articleTagsList = articleTagsDao.selectList(articleTagsQueryWrapper);
            ArrayList<Integer> articleTags = new ArrayList<>();
            articleTagsList.forEach(articleTag -> articleTags.add(articleTag.getTagId()));

            QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<Comment>().eq("comment_article_id", article.getArticleId());
            Long count = commentDao.selectCount(commentQueryWrapper);

            return articleConver.entityToVo(article, articleSrots, articleTags, count);
        });
        return page;
    }

    @Override
    public Page<Article> listRecentArticles(Integer currentPage, Integer pageSize) {

        Page<Article> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>()
                .eq("article_status", 0)
                .orderByDesc("article_id", "create_time");

        articleDao.selectPage(page, wrapper);
        page.convert(article -> {

            QueryWrapper<ArticleSorts> articleSortsQueryWrapper = new QueryWrapper<>();
            articleSortsQueryWrapper.eq("article_id", article.getArticleId());
            List<ArticleSorts> articleSortsList = articleSortsDao.selectList(articleSortsQueryWrapper);
            ArrayList<Integer> articleSrots = new ArrayList<>();
            articleSortsList.forEach(articleSort -> articleSrots.add(articleSort.getSortId()));

            QueryWrapper<ArticleTags> articleTagsQueryWrapper = new QueryWrapper<>();
            articleTagsQueryWrapper.eq("article_id", article.getArticleId());
            List<ArticleTags> articleTagsList = articleTagsDao.selectList(articleTagsQueryWrapper);
            ArrayList<Integer> articleTags = new ArrayList<>();
            articleTagsList.forEach(articleTag -> articleTags.add(articleTag.getTagId()));

            QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<Comment>().eq("comment_article_id", article.getArticleId());
            Long count = commentDao.selectCount(commentQueryWrapper);

            return articleConver.entityToVo(article, articleSrots, articleTags, count);
        });
        return page;
    }

    @Override
    public ArticleVo getArticle(Integer id) {

        QueryWrapper<Article> wrapper = new QueryWrapper<Article>()
                .eq("article_id", id)
                .eq("article_status", 0)
                .orderByDesc("article_top", "create_time");

        Article article = articleDao.selectOne(wrapper);

        if (null == article) {
            return null;
        }

        QueryWrapper<ArticleSorts> articleSortsQueryWrapper = new QueryWrapper<>();
        articleSortsQueryWrapper.eq("article_id", id);
        List<ArticleSorts> articleSortsList = articleSortsDao.selectList(articleSortsQueryWrapper);
        ArrayList<Integer> articleSrots = new ArrayList<>();
        articleSortsList.forEach(articleSort -> articleSrots.add(articleSort.getSortId()));

        QueryWrapper<ArticleTags> articleTagsQueryWrapper = new QueryWrapper<>();
        articleTagsQueryWrapper.eq("article_id", id);
        List<ArticleTags> articleTagsList = articleTagsDao.selectList(articleTagsQueryWrapper);
        ArrayList<Integer> articleTags = new ArrayList<>();
        articleTagsList.forEach(articleTag -> articleTags.add(articleTag.getTagId()));

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<Comment>().eq("comment_article_id", article.getArticleId());
        Long count = commentDao.selectCount(commentQueryWrapper);

        return articleConver.entityToVo(article, articleSrots, articleTags, count);
    }

    @Override
    public Map<String, Object> getArticleForEdit(Integer id) {

        QueryWrapper<Article> wrapper = new QueryWrapper<Article>()
                .eq("article_id", id)
                .eq("article_status", 0)
                .orderByDesc("article_top", "create_time");

        Article article = articleDao.selectOne(wrapper);

        if (null == article) {
            return null;
        }

        QueryWrapper<ArticleSorts> articleSortsQueryWrapper = new QueryWrapper<>();
        articleSortsQueryWrapper.eq("article_id", id);
        List<ArticleSorts> articleSortsList = articleSortsDao.selectList(articleSortsQueryWrapper);
        ArrayList<Integer> articleSrots = new ArrayList<>();
        articleSortsList.forEach(articleSort -> articleSrots.add(articleSort.getSortId()));

        QueryWrapper<ArticleTags> articleTagsQueryWrapper = new QueryWrapper<>();
        articleTagsQueryWrapper.eq("article_id", id);
        List<ArticleTags> articleTagsList = articleTagsDao.selectList(articleTagsQueryWrapper);
        ArrayList<Integer> articleTags = new ArrayList<>();
        articleTagsList.forEach(articleTag -> articleTags.add(articleTag.getTagId()));
        Map<String, Object> map = new HashMap<>(3);
        map.put("article", article);
        map.put("articleSorts", articleSrots);
        map.put("articleTags", articleTags);
        return map;
    }

    @Override
    public Page<Article> listBySortId(Integer currentPage, Integer pageSize, Integer sortId) {
        Page<Article> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>()
                .eq("article_status", 0)
                .inSql("article_id",
                        "select article_id \n" +
                                "            from article_sorts \n" +
                                "            where sort_id = " + sortId);
        articleDao.selectPage(page, wrapper);
        page.convert(article -> articleConver.entityToVo(article, null, null, null));
        return page;
    }

    @Override
    public Page<Article> listByTagId(Integer currentPage, Integer pageSize, Integer tagId) {
        Page<Article> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>()
                .eq("article_status", 0)
                .inSql("article_id",
                        "            select article_id\n" +
                                "            from article_tags \n" +
                                "            where tag_id = " + tagId);
        articleDao.selectPage(page, wrapper);
        page.convert(article -> articleConver.entityToVo(article, null, null, null));
        return page;
    }

    @Override
    public Page<Article> listAllArticles(Integer currentPage, Integer pageSize, String query, String type, String id) {

        Page<Article> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(query)) {
            wrapper.and(i -> i.like("article_content", query)
                    .or()
                    .like("article_title", query)
                    .or()
                    .like("article_description", query));
        }
        if (StringUtils.isNotBlank(type)) {
            if ("listBySortId".equals(type)) {
                wrapper.inSql("article_id",
                        "select article_id \n" +
                                "            from article_sorts \n" +
                                "            where sort_id = " + Integer.parseInt(id));
            } else if ("listByTagId".equals(type)) {
                wrapper.inSql("article_id",
                        "            select article_id\n" +
                                "            from article_tags \n" +
                                "            where tag_id = " + Integer.parseInt(id));
            }
        }
        wrapper.orderByDesc("article_top", "article_id", "create_time");
        articleDao.selectPage(page, wrapper);
        page.convert(article -> {

            QueryWrapper<ArticleSorts> articleSortsQueryWrapper = new QueryWrapper<>();
            articleSortsQueryWrapper.eq("article_id", article.getArticleId());
            List<ArticleSorts> articleSortsList = articleSortsDao.selectList(articleSortsQueryWrapper);
            ArrayList<Integer> articleSrots = new ArrayList<>();
            articleSortsList.forEach(articleSort -> articleSrots.add(articleSort.getSortId()));

            QueryWrapper<ArticleTags> articleTagsQueryWrapper = new QueryWrapper<>();
            articleTagsQueryWrapper.eq("article_id", article.getArticleId());
            List<ArticleTags> articleTagsList = articleTagsDao.selectList(articleTagsQueryWrapper);
            ArrayList<Integer> articleTags = new ArrayList<>();
            articleTagsList.forEach(articleTag -> articleTags.add(articleTag.getTagId()));

            QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<Comment>().eq("comment_article_id", article.getArticleId());
            Long count = commentDao.selectCount(commentQueryWrapper);
            return articleConver.entityToVo(article, articleSrots, articleTags, count);
        });
        return page;
    }

    @Override
    public boolean editArticle(Admin admin, Article article, List<Integer> articleSorts, List<Integer> articleTags) {
        article.setArticleUserId(admin.getUserId());
        int count;

        if (null == article.getArticleId()) {
            count = articleDao.insert(article);
        } else {
            count = articleDao.updateById(article);

            List<ArticleSorts> articleSortsList = articleSortsDao.selectList(new QueryWrapper<ArticleSorts>().eq("article_id", article.getArticleId()));
            articleSortsList.forEach(articleSort -> {
                if (!articleSorts.contains(articleSort.getSortId())) {
                    articleSortsDao.deleteById(articleSort.getId());
                } else {
                    articleSorts.remove(articleSort.getSortId());
                }
            });

            List<ArticleTags> articleTagsList = articleTagsDao.selectList(new QueryWrapper<ArticleTags>().eq("article_id", article.getArticleId()));
            articleTagsList.forEach(articleTag -> {
                if (!articleTags.contains(articleTag.getTagId())) {
                    articleTagsDao.deleteById(articleTag.getId());
                } else {
                    articleTags.remove(articleTag.getTagId());
                }
            });
        }

        if (articleSorts != null && articleSorts.size() > 0) {
            articleSorts.forEach(articleSort -> {
                ArticleSorts articleSort1 = new ArticleSorts();
                articleSort1.setArticleId(article.getArticleId());
                articleSort1.setSortId(articleSort);
                articleSortsDao.insert(articleSort1);
            });
        }

        if (articleTags != null && articleTags.size() > 0) {
            articleTags.forEach(articleTag -> {
                ArticleTags articleTag1 = new ArticleTags();
                articleTag1.setArticleId(article.getArticleId());
                articleTag1.setTagId(articleTag);
                articleTagsDao.insert(articleTag1);
            });
        }

        return count != 0;
    }

    @Override
    public boolean deleteArticle(Admin admin, Integer articleId) {
        Article article = articleDao.selectById(articleId);
        if (article == null) {
            return false;
        }
        if (!article.getArticleUserId().equals(admin.getUserId())) {
            return false;
        }
        if (articleDao.deleteById(articleId) != 0) {
            articleSortsDao.delete(new QueryWrapper<ArticleSorts>().eq("article_id", articleId));
            articleTagsDao.delete(new QueryWrapper<ArticleTags>().eq("article_id", articleId));
            commentDao.delete(new QueryWrapper<Comment>().eq("comment_article_id", articleId));
            return true;
        }
        return false;
    }
}
