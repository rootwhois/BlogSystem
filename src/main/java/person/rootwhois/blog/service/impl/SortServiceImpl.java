package person.rootwhois.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import person.rootwhois.blog.dao.ArticleSortsDao;
import person.rootwhois.blog.dao.SortDao;
import person.rootwhois.blog.entity.ArticleSorts;
import person.rootwhois.blog.entity.Sort;
import person.rootwhois.blog.mapstruct.SortConver;
import person.rootwhois.blog.service.SortService;
import person.rootwhois.blog.vo.SortVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
@Service
public class SortServiceImpl extends ServiceImpl<SortDao, Sort> implements SortService {

    @Autowired
    SortDao sortDao;

    @Autowired
    ArticleSortsDao articleSortsDao;

    @Resource
    SortConver sortConver;


    @Override
    public Page<Sort> listSorts(Integer currentPage, Integer pageSize, String keyword) {
        Page<Sort> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Sort> wrapper = new QueryWrapper<Sort>()
                .like("sort_name", keyword);
        sortDao.selectPage(page, wrapper);
        page.convert(sort -> {
            QueryWrapper<ArticleSorts> queryWrapper = new QueryWrapper<ArticleSorts>()
                    .eq("sort_id", sort.getSortId());
            Long count = articleSortsDao.selectCount(queryWrapper);
            return sortConver.entityToVo(sort, count);
        });
        return page;
    }

    @Override
    public Page<Sort> listSortsForEdit(Integer currentPage, Integer pageSize, String keyword) {
        Page<Sort> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Sort> wrapper = new QueryWrapper<Sort>()
                .like("sort_name", keyword);
        sortDao.selectPage(page, wrapper);
        return page;
    }

    @Override
    public List<SortVo> listAll() {
        List<Sort> list = sortDao.selectList(null);
        ArrayList<SortVo> sortVos = new ArrayList<>();
        list.forEach(sort -> {
            QueryWrapper<ArticleSorts> queryWrapper = new QueryWrapper<ArticleSorts>()
                    .eq("sort_id", sort.getSortId());
            Long count = articleSortsDao.selectCount(queryWrapper);
            sortVos.add(sortConver.entityToVo(sort, count));
        });
        return sortVos;
    }

    @Override
    public SortVo getOneById(Integer sortId) {
        QueryWrapper<ArticleSorts> queryWrapper = new QueryWrapper<ArticleSorts>()
                .eq("sort_id", sortId);
        Long count = articleSortsDao.selectCount(queryWrapper);
        return sortConver.entityToVo(sortDao.selectOne(new QueryWrapper<Sort>()
                .eq("sort_id", sortId)), count);
    }

    @Override
    public boolean add(Sort sort) {
        QueryWrapper<Sort> wrapper = new QueryWrapper<Sort>()
                .eq("sort_name", sort.getSortName());
        if (sortDao.selectCount(wrapper) == 0) {
            return sortDao.insert(sort) > 0;
        }
        return false;
    }

    @Override
    public boolean edit(Sort sort) {
        QueryWrapper<Sort> wrapper = new QueryWrapper<Sort>()
                .eq("sort_name", sort.getSortName());
        if (sortDao.selectCount(wrapper) == 0) {
            return sortDao.updateById(sort) > 0;
        }
        return false;
    }

    @Override
    public boolean delete(Integer sortId) {
        QueryWrapper<ArticleSorts> wrapper = new QueryWrapper<ArticleSorts>()
                .eq("sort_id", sortId);
        if (articleSortsDao.selectList(wrapper).size() == 0) {
            sortDao.deleteById(sortId);
            return true;
        }
        return false;
    }
}
