package person.rootwhois.blog.service.impl;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import person.rootwhois.blog.entity.Admin;
import person.rootwhois.blog.entity.Comment;
import person.rootwhois.blog.entity.Web;
import person.rootwhois.blog.service.AdminService;
import person.rootwhois.blog.service.MailService;
import person.rootwhois.blog.service.WebService;
import person.rootwhois.blog.util.HttpContextUtils;

import javax.mail.internet.MimeMessage;

/**
 * @Author: 陈广生
 * @Date: 2022/01/11/2:16 PM
 * @Description:
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    AdminService adminService;

    @Autowired
    WebService webService;

    /**
     * 系统通知
     */
    @Override
    @Async
    public void sendSystemNotifyMail(String ip, String operator, String operateType, String params, String operateResult) {
        Web web = webService.getById(1);
        Admin admin = adminService.getById(web.getWebUserId());

        String cityInfo = HttpContextUtils.getCityInfo(ip);
        String city = StringUtils.isNotEmpty(cityInfo) ? cityInfo : "未知地区";
        String s = operator  +
                " 于 " +
                DateUtil.now() +
                " 在 " +
                ip +
                "(" +
                city +
                ")" +
                " 进行了 「" +
                operateType +
                "」 操作。\n" +
                "请求参数：" +
                params +
                "\n" +
                "响应结果：" +
                operateResult;
        sendSimpleMail(admin.getUserEmail(),
                web.getWebName() + " 提示邮件",
                s);
    }

    /**
     * 评论通知
     */
    @Override
    @Async
    public void sendReplyMail(Comment reply, Comment comment) {
        Web web = webService.getById(1);

        // 发送邮件
        String s = comment.getCommentNickname() +
                "你好：\n" +
                "你于" +
                DateUtil.now() +
                " 在 " +
                web.getWebName() +
                "评论的内容已被回复，\n" +
                "回复内容：" +
                reply.getCommentContent()
                + "\n" +
                "详情可前往：" +
                web.getWebDomain() +
                "/article/" +
                comment.getCommentArticleId() +
                " 进行查看。 ";
        sendSimpleMail(comment.getCommentEmail(),
                web.getWebName() + " 评论回复提示邮件",
                s
        );
    }

    /**
     * 评论通知
     */
    @Override
    @Async
    public void sendCommentMail(Comment comment) {
        Web web = webService.getById(1);

        // 发送邮件
        String s = comment.getCommentNickname() +
                "你好：\n" +
                "你于" +
                DateUtil.now() +
                " 在 " +
                web.getWebName() +
                "评论成功，我会尽快回复你！\n" +
                "评论内容：" +
                comment.getCommentContent();
        sendSimpleMail(comment.getCommentEmail(),
                web.getWebName() + " 评论成功提示邮件",
                s
        );
    }

    /**
     * 发送普通文本邮件
     *
     * @param toAccount 收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    @Async
    public void sendSimpleMail(String toAccount, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailProperties.getUsername());
            message.setTo(toAccount);
            message.setSubject(subject);
            message.setText(content);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 发送HTML邮件
     *
     * @param toAccount 收件人
     * @param subject 主题
     * @param content 内容（可以包含<html>等标签）
     */
    @Override
    @Async
    public void sendHtmlMail(String toAccount, String subject, String content) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(mailProperties.getUsername());
            messageHelper.setTo(toAccount);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}