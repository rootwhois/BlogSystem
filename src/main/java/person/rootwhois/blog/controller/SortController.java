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
import person.rootwhois.blog.entity.Sort;
import person.rootwhois.blog.service.SortService;
import person.rootwhois.blog.vo.SortVo;

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
@RequestMapping("/api/sort")
public class SortController {

    @Autowired
    SortService sortService;

    @GetMapping("/list")
    @Cacheable(value="sort", key="#root.methodName + '_' + #root.args")
    public Result<Page<Sort>> list(@RequestParam(defaultValue = "1") Integer currentPage,
                                   @RequestParam(defaultValue = "5") Integer pageSize,
                                   @RequestParam(defaultValue = "") String keyword) {
        Page<Sort> page = sortService.listSorts(currentPage, pageSize, keyword);
        return Result.ok(page);
    }

    @RequiresAuthentication
    @GetMapping("/listForEdit")
    @Cacheable(value="sort", key="#root.methodName + '_' + #root.args")
    public Result<Page<Sort>> listForEdit(@RequestParam(defaultValue = "1") Integer currentPage,
                                   @RequestParam(defaultValue = "5") Integer pageSize,
                                   @RequestParam(defaultValue = "") String keyword) {
        Page<Sort> page = sortService.listSortsForEdit(currentPage, pageSize, keyword);
        return Result.ok(page);
    }

    @GetMapping("/listAll")
    @Cacheable(value="sort", key="#root.methodName + '_' + #root.args")
    public Result<List<SortVo>> listAll() {
        List<SortVo> sortVos = sortService.listAll();
        return Result.ok(sortVos);
    }

    @GetMapping("/one")
    @Cacheable(value="sort", key="#root.methodName + '_' + #root.args")
    public Result<SortVo> getOne(@RequestParam Integer sortId) {
        return Result.ok(sortService.getOneById(sortId));
    }

    @RequiresAuthentication
    @PostMapping("/add")
    @Caching(evict = {
            @CacheEvict(value="sort", allEntries = true),
            @CacheEvict(value="web", allEntries = true)
    })
    @Transactional
    @LogAnno(operateType = "添加分类", isToken = true, operator = "#token")
    public Result<Boolean> add(@RequestHeader("Authorization") String token,
                               @RequestBody Sort sort) {
        return Result.save(sortService.add(sort));
    }

    @RequiresAuthentication
    @PutMapping("/edit")
    @Caching(evict = {
            @CacheEvict(value="sort", allEntries = true),
            @CacheEvict(value="web", allEntries = true)
    })
    @Transactional
    @LogAnno(operateType = "更新分类", isToken = true, operator = "#token")
    public Result<Boolean> edit(@RequestHeader("Authorization") String token,
                                @RequestBody Sort sort) {
        return Result.update(sortService.edit(sort));
    }

    @RequiresAuthentication
    @DeleteMapping("/delete")
    @Caching(evict = {
            @CacheEvict(value="sort", allEntries = true),
            @CacheEvict(value="web", allEntries = true)
    })
    @Transactional
    @LogAnno(operateType = "删除分类", isToken = true, operator = "#token")
    public Result<Boolean> delete(@RequestHeader("Authorization") String token,
                                  @RequestParam("sortId") Integer sortId) {
        return Result.delete(sortService.delete(sortId));
    }

}
