package person.rootwhois.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import person.rootwhois.blog.entity.Sort;
import person.rootwhois.blog.vo.SortVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
public interface SortService extends IService<Sort> {

    Page<Sort> listSorts(Integer currentPage, Integer pageSize, String keyword);

    Page<Sort> listSortsForEdit(Integer currentPage, Integer pageSize, String keyword);

    List<SortVo> listAll();

    SortVo getOneById(Integer sortId);

    boolean add(Sort sort);

    boolean edit(Sort sort);

    boolean delete(Integer sortId);
}
