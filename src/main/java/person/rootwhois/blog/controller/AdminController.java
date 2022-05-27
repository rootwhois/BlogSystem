package person.rootwhois.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import person.rootwhois.blog.annotation.LogAnno;
import person.rootwhois.blog.common.dto.LoginDto;
import person.rootwhois.blog.common.result.Result;
import person.rootwhois.blog.entity.Admin;
import person.rootwhois.blog.mapstruct.AdminConver;
import person.rootwhois.blog.service.AdminService;
import person.rootwhois.blog.util.JwtUtils;
import person.rootwhois.blog.vo.AdminVo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Resource
    AdminConver adminConver;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    @LogAnno(operateType = "管理员登录", operator = "#loginDto.username", sendEmail = true)
    public Result<AdminVo> login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("user_name", loginDto.getUsername()));
        if (null == admin) {
            return Result.failed("用户名或密码不正确");
        }

        if(!admin.getUserPassword().equals(loginDto.getPassword())){
            return Result.failed("用户名或密码不正确");
        }
        String jwt = jwtUtils.generateToken(admin.getUserId());

        AdminVo adminVo = adminConver.entityToVo(admin);

        response.setHeader(jwtUtils.getHeader(), jwt);
        response.setHeader("Access-Control-Expose-Headers", jwtUtils.getHeader());

        return Result.ok(adminVo);
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result<Object> logout() {
        SecurityUtils.getSubject().logout();
        return Result.ok("注销成功");
    }

    @RequiresAuthentication
    @GetMapping("/info")
    @Cacheable(value="admin", key="#root.methodName + '_' + #root.args")
    public Result<Admin> info(@RequestHeader("Authorization") String token) {
        String userId = jwtUtils.getClaimByToken(token).getSubject();
        return Result.ok(adminService.getById(userId));
    }

    @RequiresAuthentication
    @PutMapping("/update")
    @Caching(evict = {
            @CacheEvict(value="admin", allEntries = true),
    })
    @LogAnno(operateType = "管理员更新个人信息", isToken = true, operator = "#token", sendEmail = true)
    public Result<Boolean> update(@RequestHeader("Authorization") String token,
                                 @Validated @RequestBody Admin admin) {
        boolean flag;
        String userId = jwtUtils.getClaimByToken(token).getSubject();
        admin.setUserId(Integer.parseInt(userId));
        flag = adminService.updateById(admin);
        return Result.update(flag);
    }

    @RequiresAuthentication
    @PutMapping("/updatePassword")
    @Caching(evict = {
            @CacheEvict(value="admin", allEntries = true),
    })
    @LogAnno(operateType = "管理员更新密码", isToken = true, operator = "#token", sendEmail = true)
    public Result<Boolean> updatePassword(@RequestHeader("Authorization") String token,
                                         @RequestBody HashMap<String, String> map) {
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        String userId = jwtUtils.getClaimByToken(token).getSubject();
        Admin admin = adminService.getById(userId);
        boolean flag = adminService.updatePassword(admin, oldPassword, newPassword);
        return Result.update(flag);
    }

}
