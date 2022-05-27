package person.rootwhois.blog.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import person.rootwhois.blog.annotation.LogAnno;
import person.rootwhois.blog.common.result.Result;
import person.rootwhois.blog.entity.Admin;
import person.rootwhois.blog.entity.Article;
import person.rootwhois.blog.service.AdminService;
import person.rootwhois.blog.service.ArticleService;
import person.rootwhois.blog.util.JwtUtils;
import person.rootwhois.blog.vo.ArticleVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
@CrossOrigin
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    AdminService adminService;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/list")
    @Cacheable(value = "article", key = "#root.methodName + '_' + #root.args")
    public Result<Page<Article>> list(@RequestParam(defaultValue = "1") Integer currentPage,
                                      @RequestParam(defaultValue = "5") Integer pageSize,
                                      @RequestParam(value = "query", defaultValue = "") String query) {
        Page<Article> page = articleService.listArticles(currentPage, pageSize, query);
        return Result.ok(page);
    }

    @GetMapping("/listRecent")
    @Cacheable(value = "article", key = "#root.methodName + '_' + #root.args")
    public Result<Page<Article>> listRecent(@RequestParam(defaultValue = "1") Integer currentPage,
                                            @RequestParam(defaultValue = "5") Integer pageSize) {
        Page<Article> page = articleService.listRecentArticles(currentPage, pageSize);
        return Result.ok(page);
    }

    @RequiresAuthentication
    @GetMapping("/listAll")
    public Result<Page<Article>> listAll(@RequestParam(defaultValue = "1") Integer currentPage,
                                         @RequestParam(defaultValue = "5") Integer pageSize,
                                         @RequestParam(value = "query", defaultValue = "") String query,
                                         @RequestParam(value = "type", defaultValue = "") String type,
                                         @RequestParam(value = "id", defaultValue = "") String id) {
        Page<Article> page = articleService.listAllArticles(currentPage, pageSize, query, type, id);
        return Result.ok(page);
    }

    @GetMapping("/one")
    @Cacheable(value = "article", key = "#root.methodName + '_' + #root.args")
    public Result<ArticleVo> one(@RequestParam Integer id) {
        return Result.ok(articleService.getArticle(id));
    }

    @RequiresAuthentication
    @GetMapping("/oneForEdit")
    @Cacheable(value = "article", key = "#root.methodName + '_' + #root.args")
    public Result<Map<String, Object>> oneForEdit(@RequestParam Integer id) {
        return Result.ok(articleService.getArticleForEdit(id));
    }

    @GetMapping("/listBySortId")
    @Cacheable(value = "article", key = "#root.methodName + '_' + #root.args")
    public Result<Page<Article>> listBySortId(@RequestParam(defaultValue = "1") Integer currentPage,
                                              @RequestParam(defaultValue = "5") Integer pageSize,
                                              @RequestParam(value = "sortId", defaultValue = "1") Integer sortId) {
        return Result.ok(articleService.listBySortId(currentPage, pageSize, sortId));
    }

    @GetMapping("/listByTagId")
    @Cacheable(value = "article", key = "#root.methodName + '_' + #root.args")
    public Result<Page<Article>> listByTagId(@RequestParam(defaultValue = "1") Integer currentPage,
                                             @RequestParam(defaultValue = "5") Integer pageSize,
                                             @RequestParam(value = "tagId", defaultValue = "1") Integer tagId) {
        return Result.ok(articleService.listByTagId(currentPage, pageSize, tagId));
    }

    @RequiresAuthentication
    @PostMapping("/editArticle")
    @Transactional
    @LogAnno(operateType = "添加或更新文章", isToken = true, operator = "#token", sendEmail = true)
    @Caching(evict = {
            @CacheEvict(value = "article", allEntries = true),
            @CacheEvict(value = "articleTags", allEntries = true),
            @CacheEvict(value = "articleSorts", allEntries = true),
            @CacheEvict(value="sort", allEntries = true),
            @CacheEvict(value="tag", allEntries = true),
            @CacheEvict(value = "web", allEntries = true),
    })
    public Result<Boolean> editArticle(@RequestHeader("Authorization") String token,
                                       @RequestBody String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        Article article = jsonObject.getObject("article", Article.class);
        boolean isSave = null == article.getArticleId() ;
        List articleSorts = jsonObject.getObject("articleSorts", List.class);
        List articleTags = jsonObject.getObject("articleTags", List.class);

        String userId = jwtUtils.getClaimByToken(token).getSubject();
        Admin admin = adminService.getById(userId);
        article.setArticleUserId(Integer.parseInt(userId));
        boolean flag = articleService.editArticle(admin, article, articleSorts, articleTags);
        return isSave ? Result.save(flag) : Result.update(flag);
    }

    @RequiresAuthentication
    @DeleteMapping("/delete")
    @LogAnno(operateType = "删除文章", isToken = true, operator = "#token", sendEmail = true)
    @Caching(evict = {
            @CacheEvict(value = "article", allEntries = true),
            @CacheEvict(value = "articleTags", allEntries = true),
            @CacheEvict(value = "articleSorts", allEntries = true),
            @CacheEvict(value="sort", allEntries = true),
            @CacheEvict(value="tag", allEntries = true),
            @CacheEvict(value = "comment", allEntries = true),
            @CacheEvict(value = "web", allEntries = true),
    })
    @Transactional
    public Result<Boolean> delete(@RequestHeader("Authorization") String token,
                                  @RequestParam("articleId") Integer articleId) {
        String userId = jwtUtils.getClaimByToken(token).getSubject();
        Admin admin = adminService.getById(userId);
        return Result.delete(articleService.deleteArticle(admin, articleId));
    }
}
