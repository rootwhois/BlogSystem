package person.rootwhois.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import person.rootwhois.blog.dao.AdminDao;
import person.rootwhois.blog.dao.ArticleDao;
import person.rootwhois.blog.dao.CommentDao;
import person.rootwhois.blog.entity.Admin;
import person.rootwhois.blog.entity.Comment;
import person.rootwhois.blog.mapstruct.CommentConver;
import person.rootwhois.blog.service.CommentService;
import person.rootwhois.blog.service.MailService;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    ArticleDao articleDao;

    @Autowired
    AdminDao adminDao;

    @Autowired
    MailService mailService;

    @Resource
    CommentConver commentConver;

    @Override
    public Page<Comment> listRecent(Integer currentPage, Integer pageSize) {
        Page<Comment> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("comment_id", "create_time");
        commentDao.selectPage(page, wrapper);
        page.convert(comment -> commentConver.entityToVo(comment));
        return page;
    }

    @Override
    public Page<Comment> listAll(Integer currentPage, Integer pageSize, String keyword, String articleId) {
        Page<Comment> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Comment> wrapper = new QueryWrapper<Comment>()
                .orderByDesc("comment_id", "create_time");
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.and(i -> i.like("comment_content", keyword)
                    .or()
                    .like("comment_nickname", keyword));
        }

        if (articleId != null && !articleId.isEmpty()) {
            wrapper.eq("comment_article_id", Integer.parseInt(articleId));
        }

        return commentDao.selectPage(page, wrapper);
    }

    @Override
    public Page<Comment> listByArticleId(Integer currentPage, Integer pageSize, Integer articleId) {
        Page<Comment> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Comment> wrapper = new QueryWrapper<Comment>()
                .eq("comment_status", 0)
                .eq("comment_article_id", articleId)
                .orderByDesc("comment_id", "create_time");
        commentDao.selectPage(page, wrapper);
        page.convert(comment -> commentConver.entityToVo(comment));
        return page;
    }

    @Override
    public boolean add(Comment comment, String remoteAddr) {
        if (null == comment.getCommentArticleId()
                || null == comment.getCommentNickname() || comment.getCommentNickname().isEmpty()
                || null == comment.getCommentContent() || comment.getCommentContent().isEmpty()
                || null == comment.getCommentEmail() || comment.getCommentEmail().isEmpty()) {
            return false;
        }
        comment.setCommentNickname(comment.getCommentNickname().trim());
        comment.setCommentContent(comment.getCommentContent().trim());
        comment.setCommentEmail(comment.getCommentEmail().trim());
        QueryWrapper<Admin> wrapper = new QueryWrapper<Admin>()
                .eq("user_nickname", comment.getCommentNickname())
                .or()
                .eq("user_name", comment.getCommentNickname());
        if (null == adminDao.selectOne(wrapper)) {
            comment.setCommentIp(remoteAddr);
            if (commentDao.insert(comment) > 0) {
                mailService.sendCommentMail(comment);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean reply(String userId, String remoteAddr, Comment reply) {
        Admin admin = adminDao.selectById(userId);
        reply.setCommentUserId(admin.getUserId());
        reply.setCommentNickname(admin.getUserNickname());
        reply.setCommentEmail(admin.getUserEmail());
        reply.setCommentIp(remoteAddr);
        reply.setCommentStatus(0);
        if (commentDao.insert(reply) > 0) {
            Comment comment = commentDao.selectById(reply.getCommentSubId());
            mailService.sendReplyMail(reply, comment);
            return true;
        }
        return false;
    }

}
