package person.rootwhois.blog.service;

import person.rootwhois.blog.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
public interface AdminService extends IService<Admin> {

    boolean updatePassword(Admin admin, String oldPassword, String newPassword);
}
