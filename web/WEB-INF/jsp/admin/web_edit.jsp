<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap4.0.min.css">
    <script src="${pageContext.request.contextPath}/static/js/jquery.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap4.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
<%@include file="common/header.jsp" %>
<div style="position: relative;top: 10%">
    <c:if test="${!empty succ}">
        <div class="alert alert-success" role="alert">
                ${succ}
        </div>
    </c:if>
    <c:if test="${!empty error}">
        <div class="alert alert-danger" role="alert">
                ${error}
        </div>
    </c:if>
</div>
<div class="container">
    <form action="${pageContext.request.contextPath}/admin/web/edit/do">
        <input type="hidden" value="${web.webId}" name="id">
        <div class="form-group">
            <label for="name">网站名</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="网站名" value="${web.webName}"
                   required>
        </div>
        <div class="form-group">
            <label for="domain">网站名</label>
            <input type="text" class="form-control" id="domain" name="domain" placeholder="网址" value="${web.webDomain}"
                   required>
        </div>
        <input type="submit"/>
        <input type="reset" value="重置"/>
    </form>
</div>
</body>
</html>