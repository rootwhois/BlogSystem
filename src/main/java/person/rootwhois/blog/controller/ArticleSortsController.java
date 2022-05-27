package person.rootwhois.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import person.rootwhois.blog.common.result.Result;
import person.rootwhois.blog.entity.ArticleSorts;
import person.rootwhois.blog.mapstruct.ArticleSortsConver;
import person.rootwhois.blog.service.ArticleSortsService;
import person.rootwhois.blog.vo.ArticleSortsVo;

import javax.annotation.Resource;
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
@RequestMapping("/api/article-sorts")
public class ArticleSortsController {

    @Autowired
    ArticleSortsService articleSortsService;

    @Resource
    ArticleSortsConver articleSortsConver;

    @GetMapping("/one")
    @Cacheable(value="articleSorts", key="#root.methodName + '_' + #root.args")
    public Result<List<ArticleSortsVo>> list(@RequestParam("id") Integer id) {
        List<ArticleSorts> list = articleSortsService.list(new QueryWrapper<ArticleSorts>().eq("sort_id", id));
        List<ArticleSortsVo> articleSortsVos = articleSortsConver.entityListToVoList(list);
        return Result.ok(articleSortsVos);
    }

}
