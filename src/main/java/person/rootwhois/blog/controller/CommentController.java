package person.rootwhois.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import person.rootwhois.blog.annotation.LogAnno;
import person.rootwhois.blog.common.result.ApiErrorCode;
import person.rootwhois.blog.common.result.Result;
import person.rootwhois.blog.entity.Comment;
import person.rootwhois.blog.service.CommentService;
import person.rootwhois.blog.util.JwtUtils;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping("/listRecent")
    @Cacheable(value="comment", key="#root.methodName + '_' + #root.args")
    public Result<Page<Comment>> listRecent(@RequestParam(defaultValue = "1") Integer currentPage,
                                                @RequestParam(defaultValue = "5") Integer pageSize) {
        return Result.ok(commentService.listRecent(currentPage, pageSize));
    }

    @RequiresAuthentication
    @GetMapping("listAll")
    public Result<Page<Comment>> listAll(@RequestParam(defaultValue = "1") Integer currentPage,
                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                         @RequestParam(defaultValue = "") String keyword,
                                         @RequestParam(defaultValue = "") String articleId) {
        return Result.ok(commentService.listAll(currentPage, pageSize, keyword, articleId));
    }

    @GetMapping("/listByArticleId")
    @Cacheable(value="comment", key="#root.methodName + '_' + #root.args")
    public Result<Page<Comment>> listByArticleId(@RequestParam(defaultValue = "1") Integer currentPage,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam("articleId") Integer articleId) {
        return Result.ok(commentService.listByArticleId(currentPage, pageSize, articleId));
    }

    @LogAnno(operateType = "发布评论", operator = "#comment.commentNickname", sendEmail = true, emailAddress = "#comment.commentEmail")
    @PostMapping("/add")
    @Caching(evict = {
            @CacheEvict(value="article", allEntries = true),
            @CacheEvict(value="comment", allEntries = true),
            @CacheEvict(value="web", allEntries = true)
    })
    @Transactional
    public Result<String> add(@Validated @RequestBody Comment comment, HttpServletRequest request) {
        String remoteAddr = request.getHeader("x-forwarded-for");
//        String remoteAddr = request.getRemoteAddr();
        boolean flag = commentService.add(comment, remoteAddr);
        return flag ? Result.ok("评论成功！") : Result.failed("评论失败！", ApiErrorCode.PRECONDITION_FAILED.getCode(), "评论失败！");
    }

    @RequiresAuthentication
    @DeleteMapping("/delete")
    @Caching(evict = {
            @CacheEvict(value="article", allEntries = true),
            @CacheEvict(value="comment", allEntries = true),
            @CacheEvict(value="web", allEntries = true)
    })
    @Transactional
    public Result<Boolean> delete(@RequestParam("commentId") Integer commentId) {
        return Result.delete(commentService.removeById(commentId));
    }

    @RequiresAuthentication
    @PostMapping("/reply")
    @Caching(evict = {
            @CacheEvict(value="article", allEntries = true),
            @CacheEvict(value="comment", allEntries = true),
            @CacheEvict(value="web", allEntries = true)
    })
    @Transactional
    public Result<String> reply(@RequestHeader("Authorization") String token,
                                HttpServletRequest request,
                                @Validated @RequestBody Comment comment) {
        String userId = jwtUtils.getClaimByToken(token).getSubject();
        String remoteAddr = request.getHeader("x-forwarded-for");
//        String remoteAddr = request.getRemoteAddr();
        boolean flag = commentService.reply(userId, remoteAddr, comment);
        return flag ? Result.ok("回复成功！") : Result.failed("回复失败！", ApiErrorCode.PRECONDITION_FAILED.getCode(), "回复失败！");
    }
}
