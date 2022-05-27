package person.rootwhois.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import person.rootwhois.blog.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import person.rootwhois.blog.vo.TagVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
public interface TagService extends IService<Tag> {

    Page<Tag> listTags(Integer currentPage, Integer pageSize, String keyword);

    Page<Tag> listTagsForEdit(Integer currentPage, Integer pageSize, String keyword);

    List<TagVo> listAll();

    TagVo getOneById(Integer tagId);

    boolean addTag(Tag tag);

    boolean editTag(Tag tag);

    boolean deleteTag(Integer tagId);
}
