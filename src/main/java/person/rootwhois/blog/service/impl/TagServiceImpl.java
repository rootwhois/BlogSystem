package person.rootwhois.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import person.rootwhois.blog.dao.ArticleTagsDao;
import person.rootwhois.blog.dao.TagDao;
import person.rootwhois.blog.entity.ArticleTags;
import person.rootwhois.blog.entity.Tag;
import person.rootwhois.blog.mapstruct.TagConver;
import person.rootwhois.blog.service.TagService;
import person.rootwhois.blog.vo.TagVo;

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
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {

    @Autowired
    TagDao tagDao;

    @Autowired
    ArticleTagsDao articleTagsDao;

    @Resource
    TagConver tagConver;


    @Override
    public Page<Tag> listTags(Integer currentPage, Integer pageSize, String keyword) {
        Page<Tag> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.like("tag_name", keyword);
        tagDao.selectPage(page, wrapper);
        page.convert(tag -> {
            QueryWrapper<ArticleTags> queryWrapper = new QueryWrapper<ArticleTags>()
                    .eq("tag_id", tag.getTagId());
            Long count = articleTagsDao.selectCount(queryWrapper);
            return tagConver.entityToVo(tag, count);
        });
        return page;
    }

    @Override
    public Page<Tag> listTagsForEdit(Integer currentPage, Integer pageSize, String keyword) {
        Page<Tag> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.like("tag_name", keyword);
        tagDao.selectPage(page, wrapper);
        return page;
    }

    @Override
    public List<TagVo> listAll() {
        List<Tag> list = tagDao.selectList(null);
        ArrayList<TagVo> tagVos = new ArrayList<>();
        list.forEach(tag -> {
            QueryWrapper<ArticleTags> queryWrapper = new QueryWrapper<ArticleTags>()
                    .eq("tag_id", tag.getTagId());
            Long count = articleTagsDao.selectCount(queryWrapper);
            tagVos.add(tagConver.entityToVo(tag, count));
        });
        return tagVos;
    }

    @Override
    public TagVo getOneById(Integer tagId) {
        QueryWrapper<ArticleTags> queryWrapper = new QueryWrapper<ArticleTags>()
                .eq("tag_id", tagId);
        Long count = articleTagsDao.selectCount(queryWrapper);
        return tagConver.entityToVo(tagDao.selectOne(new QueryWrapper<Tag>().eq("tag_id", tagId)), count);
    }

    @Override
    public boolean addTag(Tag tag) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<Tag>()
                .eq("tag_name", tag.getTagName());
        if (tagDao.selectCount(wrapper) == 0) {
            return tagDao.insert(tag) > 0;
        }
        return false;
    }

    @Override
    public boolean editTag(Tag tag) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<Tag>()
                .eq("tag_name", tag.getTagName());
        if (tagDao.selectCount(wrapper) == 0) {
            return tagDao.updateById(tag) > 0;
        }
        return false;
    }

    @Override
    public boolean deleteTag(Integer tagId) {
        QueryWrapper<ArticleTags> wrapper = new QueryWrapper<ArticleTags>()
                .eq("tag_id", tagId);
        if (articleTagsDao.selectList(wrapper).size() == 0) {
            return tagDao.deleteById(tagId) > 0;
        }
        return false;
    }


}
