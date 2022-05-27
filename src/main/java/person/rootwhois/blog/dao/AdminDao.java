package person.rootwhois.blog.dao;

import person.rootwhois.blog.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
@Mapper
public interface AdminDao extends BaseMapper<Admin> {

}
