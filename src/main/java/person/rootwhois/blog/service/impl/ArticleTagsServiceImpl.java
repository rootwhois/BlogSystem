package person.rootwhois.blog.service.impl;

import person.rootwhois.blog.entity.ArticleTags;
import person.rootwhois.blog.dao.ArticleTagsDao;
import person.rootwhois.blog.service.ArticleTagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈广生
 * @since 2022-01-05
 */
@Service
public class ArticleTagsServiceImpl extends ServiceImpl<ArticleTagsDao, ArticleTags> implements ArticleTagsService {

}
