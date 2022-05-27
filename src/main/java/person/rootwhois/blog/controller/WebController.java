package person.rootwhois.blog.controller;


import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import person.rootwhois.blog.annotation.LogAnno;
import person.rootwhois.blog.common.result.Result;
import person.rootwhois.blog.entity.Admin;
import person.rootwhois.blog.entity.Web;
import person.rootwhois.blog.service.AdminService;
import person.rootwhois.blog.service.WebService;
import person.rootwhois.blog.util.JwtUtils;
import person.rootwhois.blog.vo.InfoVo;

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
@RequestMapping("/api/web")
public class WebController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    WebService webService;

    @Autowired
    AdminService adminService;

    @GetMapping("/info")
    @Cacheable(value="web", key="#root.methodName + '_' + #root.args")
    public Result<InfoVo> info() {
        return Result.ok(webService.getInfo());
    }

    @RequiresAuthentication
    @GetMapping("/webInfo")
    public Result<Web> webInfo(@RequestHeader("Authorization") String token) {
        String userId = jwtUtils.getClaimByToken(token).getSubject();
        return Result.ok(webService.getWebInfo(userId));
    }

    @RequiresAuthentication
    @PutMapping("/edit")
    @CacheEvict(value="web", allEntries = true)
    @Transactional
    @LogAnno(operateType = "管理员更新网站信息", isToken = true, operator = "#token", sendEmail = true)
    public Result<Boolean> edit(@RequestHeader("Authorization") String token,
                         @RequestBody Web web) {
        String userId = jwtUtils.getClaimByToken(token).getSubject();
        Admin admin = adminService.getById(userId);
        return Result.update(webService.edit(admin, web));
    }

}
