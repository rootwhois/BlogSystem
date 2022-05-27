package person.rootwhois.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import person.rootwhois.blog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
public interface CommentService extends IService<Comment> {

    Page<Comment> listRecent(Integer currentPage, Integer pageSize);

    Page<Comment> listAll(Integer currentPage, Integer pageSize, String keyword, String articleId);

    Page<Comment> listByArticleId(Integer currentPage, Integer pageSize, Integer articleId);

    boolean add(Comment comment, String remoteAddr);

    boolean reply(String userId, String remoteAddr, Comment comment);
}
