package person.rootwhois.blog.controller;


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
import person.rootwhois.blog.entity.Tag;
import person.rootwhois.blog.service.TagService;
import person.rootwhois.blog.vo.TagVo;

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
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/list")
    @Cacheable(value="tag", key="#root.methodName + '_' + #root.args")
    public Result<Page<Tag>> list(@RequestParam(defaultValue = "1") Integer currentPage,
                                  @RequestParam(defaultValue = "5") Integer pageSize,
                                  @RequestParam(defaultValue = "") String keyword) {
        return Result.ok(tagService.listTags(currentPage, pageSize, keyword));
    }

    @RequiresAuthentication
    @GetMapping("/listForEdit")
    @Cacheable(value="tag", key="#root.methodName + '_' + #root.args")
    public Result<Page<Tag>> listForEdit(@RequestParam(defaultValue = "1") Integer currentPage,
                                  @RequestParam(defaultValue = "5") Integer pageSize,
                                  @RequestParam(defaultValue = "") String keyword) {
        return Result.ok(tagService.listTagsForEdit(currentPage, pageSize, keyword));
    }

    @GetMapping("/listAll")
    @Cacheable(value="tag", key="#root.methodName + '_' + #root.args")
    public Result<List<TagVo>> listAll() {
        return Result.ok(tagService.listAll());
    }

    @GetMapping("/one")
    @Cacheable(value="tag", key="#root.methodName + '_' + #root.args")
    public Result<TagVo> getOne(@RequestParam("tagId") Integer tagId) {
        return Result.ok(tagService.getOneById(tagId));
    }

    @RequiresAuthentication
    @PostMapping("/add")
    @Caching(evict = {
            @CacheEvict(value="tag", allEntries = true),
            @CacheEvict(value="web", allEntries = true)
    })
    @Transactional
    @LogAnno(operateType = "添加标签", isToken = true, operator = "#token")
    public Result<Boolean> add(@RequestHeader("Authorization") String token,
                               @RequestBody Tag tag) {
        return Result.save(tagService.addTag(tag));
    }

    @RequiresAuthentication
    @PutMapping("/edit")
    @Caching(evict = {
            @CacheEvict(value="tag", allEntries = true),
            @CacheEvict(value="web", allEntries = true)
    })
    @Transactional
    @LogAnno(operateType = "编辑标签", isToken = true, operator = "#token")
    public Result<Boolean> edit(@RequestHeader("Authorization") String token,
                                @RequestBody Tag tag) {
        return Result.update(tagService.editTag(tag));
    }

    @RequiresAuthentication
    @DeleteMapping("/delete")
    @Caching(evict = {
            @CacheEvict(value="tag", allEntries = true),
            @CacheEvict(value="web", allEntries = true)
    })
    @Transactional
    @LogAnno(operateType = "删除标签", isToken = true, operator = "#token")
    public Result<Boolean> delete(@RequestHeader("Authorization") String token,
                                  @RequestParam("tagId") Integer tagId) {
        return Result.delete(tagService.deleteTag(tagId));
    }
}
