package person.rootwhois.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import person.rootwhois.blog.entity.History;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
public interface HistoryService extends IService<History> {

    Page<History> listHistories(Integer currentPage, Integer pageSize, String keyword);

    Page<History> listSuccess(Integer currentPage, Integer pageSize);

    Map<String, Object> lastLogin(String userId);
}
