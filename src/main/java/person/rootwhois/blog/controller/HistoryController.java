package person.rootwhois.blog.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import person.rootwhois.blog.common.result.Result;
import person.rootwhois.blog.entity.History;
import person.rootwhois.blog.mapstruct.HistoryConver;
import person.rootwhois.blog.service.HistoryService;
import person.rootwhois.blog.util.JwtUtils;

import java.util.Map;

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
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @Autowired
    HistoryConver historyConver;

    @Autowired
    JwtUtils jwtUtils;

    @RequiresAuthentication
    @GetMapping("/list")
    public Result<Page<History>> list(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "") String keyword) {
        return Result.ok(historyService.listHistories(currentPage, pageSize, keyword));
    }

    @RequiresAuthentication
    @GetMapping("/listSuccess")
    public Result<Page<History>> listSuccess(@RequestParam(defaultValue = "1") Integer currentPage,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.ok(historyService.listSuccess(currentPage, pageSize));
    }

    @RequiresAuthentication
    @GetMapping("lastLogin")
    public Result<Map<String, Object>> lastLogin(@RequestHeader("Authorization") String token) {
        String userId = jwtUtils.getClaimByToken(token).getSubject();
        return Result.ok(historyService.lastLogin(userId));
    }
}
