package person.rootwhois.blog.mapstruct;

import org.mapstruct.Mapper;
import person.rootwhois.blog.entity.Comment;
import person.rootwhois.blog.vo.CommentVo;

/**
 * @Author: 陈广生
 * @Date: 2022/01/09/1:14 PM
 * @Description:
 */
@Mapper(componentModel = "spring")
public interface CommentConver {

    CommentVo entityToVo(Comment comment);
}
