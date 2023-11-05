<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="kr">
<head>
    <%@ include file="/include/header.jspf" %>
</head>
<body>

<%@ include file="/include/navigation.jspf" %>

<div class="container" id="main">
    <div class="col-md-12 col-sm-12 col-lg-12">
        <div class="panel panel-default">
            <header class="qna-header">
                <h2 class="qna-title">${question.title}</h2>
            </header>
            <div class="content-main">
                <article class="article">
                    <div class="article-header">
                        <div class="article-header-thumb">
                            <img src="https://graph.facebook.com/v2.3/100000059371774/picture"
                                 class="article-author-thumb" alt="">
                        </div>
                        <div class="article-header-text">
                            <a href="/users/profile?userId=${user.userId}"
                               class="article-author-name">${user.userId}</a>
                            <p class="article-header-time"> ${question.createdDate}</p>
                        </div>
                    </div>
                    <div class="article-doc">
                        <span style="white-space: pre-line">${question.contents}</span>
                    </div>
                    <div class="article-util">
                        <ul class="article-util-list">
                            <li>
                                <a class="link-modify-article" href="/qna/updateForm?questionId=${question.questionId}">수정</a>
                            </li>
                            <li>
                                <form class="form-delete" action="/qna/delete?questionId=${question.questionId}" method="POST">
                                    <input type="hidden" name="_method" value="DELETE">
                                    <button class="link-delete-article" type="submit">삭제</button>
                                </form>
                            </li>
                            <li>
                                <a class="link-modify-article" href="/">목록</a>
                            </li>
                        </ul>
                    </div>
                </article>

                <%-- comment --%>
                <div class="qna-comment">
                    <div class="qna-comment-slipp">
                        <p class="qna-comment-count"><strong>${question.countOfAnswer}</strong>개의 의견</p>
                        <div class="qna-comment-slipp-articles">

                            <c:forEach items="${answers}" var="answer" varStatus="status">
                                <article class="article" id="answer-1405">
                                    <div class="article-header">
                                        <div class="article-header-thumb">
                                            <img src="https://graph.facebook.com/v2.3/1324855987/picture"
                                                 class="article-author-thumb" alt="">
                                        </div>
                                        <div class="article-header-text">
                                            <a href="/users/profile?userId=${answer.writer}" class="article-author-name">${answer.writer}</a>
                                            <a class="article-header-time">
                                                ${answer.createdDate}
                                            </a>
                                        </div>
                                    </div>
                                    <div class="article-doc comment-doc">
                                        <span style="white-space: pre-line">${answer.contents}</span>
                                    </div>
                                    <div class="article-util">
                                        <ul class="article-util-list">
                                            <li>
                                                <a class="link-modify-article"
                                                   href="/questions/413/answers/1405/form">수정</a>
                                            </li>
                                            <li>
                                                <form class="form-delete" action="/questions/413/answers/1405"
                                                      method="POST">
                                                    <input type="hidden" name="_method" value="DELETE">
                                                    <button type="submit" class="link-delete-article">삭제</button>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                </article>
                            </c:forEach>

                            <form class="submit-write" method="post" action="/answers/create?questionId=${question.questionId}">
                                <div class="form-group" style="padding:14px;">
                                    <textarea class="form-control" id="contents" name="contents" placeholder="Enter answer"></textarea>
                                </div>
                                <button type="submit" class="btn btn-success clearfix pull-right">Post</button>
                                <div class="clearfix"/>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/include/footer.jspf" %>
</body>
</html>