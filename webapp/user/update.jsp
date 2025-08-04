<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="kr">
<head>
    <%@ include file="/template/header.jspf" %>
</head>
<body>
<%@ include file="/template/navigation_bar.jspf" %>

<div class="col-md-12">
    <div class="navbar-header">
        <a href="#" style="margin-left:15px;" class="navbar-btn btn btn-default btn-plus dropdown-toggle"
           data-toggle="dropdown"><i class="glyphicon glyphicon-home" style="color:#dd1111;"></i> Home <small><i
                class="glyphicon glyphicon-chevron-down"></i></small></a>
        <ul class="nav dropdown-menu">
            <li><a href="profile.jsp"><i class="glyphicon glyphicon-user" style="color:#1111dd;"></i> Profile</a>
            </li>
            <li class="nav-divider"></li>
            <li><a href="#"><i class="glyphicon glyphicon-cog" style="color:#dd1111;"></i> Settings</a></li>
        </ul>
    </div>
    <div class="collapse navbar-collapse" id="navbar-collapse2">
        <ul class="nav navbar-nav navbar-right">
            <li class="active"><a href="../index.jsp">Posts</a></li>
            <li><a href="login.jsp" role="button">로그인</a></li>
            <li><a href="form.jsp" role="button">회원가입</a></li>
            <li><a href="#" role="button">로그아웃</a></li>
            <li><a href="#" role="button">개인정보수정</a></li>
        </ul>
    </div>
</div>
</div>

<div class="container" id="main">
    <div class="col-md-6 col-md-offset-3">
        <div class="panel panel-default content-main">
            <form name="question" method="post" action="/user/update">
                <div class="form-group">
                    <label for="userId">사용자 아이디</label>
                    <input class="form-control" id="userId" name="userId" value="${user.userId}" readonly disabled>
                </div>
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                </div>
                <div class="form-group">
                    <label for="name">이름</label>
                    <input class="form-control" id="name" name="name" placeholder="Name" value="${user.name}">
                </div>
                <div class="form-group">
                    <label for="email">이메일</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Email"
                           value="${user.email}">
                </div>
                <button type="submit" class="btn btn-success clearfix pull-right">수정</button>
                <div class="clearfix"/>
            </form>
        </div>
    </div>
</div>

<%@ include file="/template/footer.jspf" %>
</body>
</html>