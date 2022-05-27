package person.rootwhois.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import person.rootwhois.blog.dao.AdminDao;
import person.rootwhois.blog.entity.Admin;
import person.rootwhois.blog.service.AdminService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminDao, Admin> implements AdminService {

    @Autowired
    AdminDao adminDao;

    @Override
    public boolean updatePassword(Admin admin, String oldPassword, String newPassword) {
        if(!admin.getUserPassword().equals(oldPassword)){
            return false;
        }
        admin.setUserPassword(newPassword);
        return adminDao.updateById(admin) > 0;
    }
}
