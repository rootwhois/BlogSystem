package person.rootwhois.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import person.rootwhois.blog.dao.*;
import person.rootwhois.blog.entity.Admin;
import person.rootwhois.blog.entity.Web;
import person.rootwhois.blog.mapstruct.WebConver;
import person.rootwhois.blog.service.WebService;
import person.rootwhois.blog.vo.InfoVo;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
@Service
public class WebServiceImpl extends ServiceImpl<WebDao, Web> implements WebService {

    @Autowired
    WebDao webDao;

    @Autowired
    AdminDao adminDao;

    @Autowired
    ArticleDao articleDao;

    @Autowired
    SortDao sortDao;

    @Autowired
    TagDao tagDao;

    @Autowired
    CommentDao commentDao;

    @Resource
    WebConver webConver;


    @Override
    public InfoVo getInfo() {
        InfoVo infoVo = new InfoVo();

        Web web = webDao.selectById(1);

        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.select("user_nickname", "user_avatar")
                .eq("user_id", web.getWebUserId());
        Admin admin = adminDao.selectOne(adminQueryWrapper);

        infoVo.setWebIsOpen(web.getWebIsOpen());
        infoVo.setWebDomain(web.getWebDomain());
        infoVo.setWebName(web.getWebName());
        infoVo.setUserNickName(admin.getUserNickname());
        infoVo.setUserAvatar(admin.getUserAvatar());
        infoVo.setArticleCount(articleDao.selectCount(null));
        infoVo.setSortCount(sortDao.selectCount(null));
        infoVo.setTagCount(tagDao.selectCount(null));
        infoVo.setCommentCount(commentDao.selectCount(null));
        return infoVo;
    }

    @Override
    public Web getWebInfo(String userId) {
        return webDao.selectById(userId);
    }

    @Override
    public boolean edit(Admin admin, Web web) {
        QueryWrapper<Web> wrapper = new QueryWrapper<Web>().eq("web_user_id", admin.getUserId());
        Web one = webDao.selectOne(wrapper);
        web.setWebId(one.getWebId());
        web.setWebUserId(one.getWebUserId());
        return webDao.updateById(web) > 0;
    }
}
