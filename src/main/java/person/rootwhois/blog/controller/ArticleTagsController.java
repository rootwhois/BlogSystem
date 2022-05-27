package person.rootwhois.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import person.rootwhois.blog.common.result.Result;
import person.rootwhois.blog.entity.ArticleTags;
import person.rootwhois.blog.mapstruct.ArticleTagsConver;
import person.rootwhois.blog.service.ArticleTagsService;
import person.rootwhois.blog.vo.ArticleTagsVo;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
@CrossOrigin
@RestController
@RequestMapping("/api/article-tags")
public class ArticleTagsController {

    @Autowired
    ArticleTagsService articleTagsService;

    @Autowired
    ArticleTagsConver articleTagsConver;

    @GetMapping("/one")
    @Cacheable(value="articleTags", key="#root.methodName + '_' + #root.args")
    public Result<List<ArticleTagsVo>> list(@RequestParam("id") Integer id) {
        List<ArticleTags> list = articleTagsService.list(new QueryWrapper<ArticleTags>().eq("tag_id", id));
        List<ArticleTagsVo> articleTagsVos = articleTagsConver.entityListToVoList(list);
        return Result.ok(articleTagsVos);
    }


}
