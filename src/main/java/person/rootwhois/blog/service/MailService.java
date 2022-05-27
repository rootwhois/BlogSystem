package person.rootwhois.blog.service;

import person.rootwhois.blog.entity.Comment;

/**
 * @Author: 陈广生
 * @Date: 2022/01/11/2:15 PM
 * @Description:
 */
public interface MailService {

    void sendSystemNotifyMail(String ip, String operator, String operateType, String params, String operateResult);

    void sendReplyMail(Comment reply, Comment comment);

    void sendCommentMail(Comment comment);

    /**
     * 发送普通文本邮件
     *
     * @param toAccount 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String toAccount, String subject, String content);
    /**
     * 发送HTML邮件
     *
     * @param toAccount 收件人
     * @param subject 主题
     * @param content 内容（可以包含<html>等标签）
     */
    void sendHtmlMail(String toAccount, String subject, String content);
}