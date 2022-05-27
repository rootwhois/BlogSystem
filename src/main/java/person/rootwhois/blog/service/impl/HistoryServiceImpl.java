package person.rootwhois.blog.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import person.rootwhois.blog.dao.AdminDao;
import person.rootwhois.blog.dao.HistoryDao;
import person.rootwhois.blog.entity.Admin;
import person.rootwhois.blog.entity.History;
import person.rootwhois.blog.mapstruct.HistoryConver;
import person.rootwhois.blog.service.HistoryService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryDao, History> implements HistoryService {

    @Autowired
    HistoryDao historyDao;

    @Autowired
    AdminDao adminDao;

    @Resource
    HistoryConver historyConver;

    @Override
    public Page<History> listHistories(Integer currentPage, Integer pageSize, String keyword) {
        Page<History> page = new Page<>(currentPage, pageSize);
        QueryWrapper<History> wrapper = new QueryWrapper<History>()
                .and(i -> i.like("operator", keyword).or().like("operator", keyword)
                        .or()
                        .like("operate_method", keyword)
                        .or()
                        .like("operate_type", keyword)
                        .or()
                        .like("params", keyword)
                        .or()
                        .like("operate_result_status", keyword)
                        .or()
                        .like("operate_result", keyword)
                        .or()
                        .like("operate_ip", keyword)
                )
                .orderByDesc("id", "update_time", "create_time");
        historyDao.selectPage(page, wrapper);
        page.convert(history -> historyConver.entityToVo(history));
        return page;
    }

    @Override
    public Page<History> listSuccess(Integer currentPage, Integer pageSize) {
        Page<History> page = new Page<>(currentPage, pageSize);
        QueryWrapper<History> wrapper = new QueryWrapper<History>()
                .eq("operate_result_status", "成功")
                .orderByDesc("id", "update_time", "create_time");
        historyDao.selectPage(page, wrapper);
        page.convert(history -> historyConver.entityToVo(history));
        return page;
    }

    @Override
    public Map<String, Object> lastLogin(String userId) {
        HashMap<String, Object> map = new HashMap<>();
        String userName = adminDao.selectOne(new QueryWrapper<Admin>().eq("user_id", userId)).getUserName();
        QueryWrapper<History> wrapper = new QueryWrapper<History>()
                .eq("operator", userName)
                .eq("operate_type", "管理员登录")
                .orderByDesc("create_time");
        List<History> list = historyDao.selectList(wrapper);
        if (list.size() <= 1) {
            map.put("lastLoginTime", "首次登录");
            map.put("lastLoginIp", "首次登录");
        } else {
            History history = list.get(1);
            map.put("lastLoginTime", DateUtil.formatLocalDateTime(history.getCreateTime()));
            map.put("lastLoginIp", history.getOperateIp());
        }
        return map;
    }
}
