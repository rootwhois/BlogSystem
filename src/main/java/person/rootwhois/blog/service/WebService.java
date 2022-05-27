package person.rootwhois.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import person.rootwhois.blog.entity.Admin;
import person.rootwhois.blog.entity.Web;
import person.rootwhois.blog.vo.InfoVo;
import person.rootwhois.blog.vo.WebVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
public interface WebService extends IService<Web> {

    InfoVo getInfo();

    Web getWebInfo(String userId);

    boolean edit(Admin admin, Web web);
}
