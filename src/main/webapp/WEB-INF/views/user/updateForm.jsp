<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp"%>
<div class="container">
    <div class="container-join">
        <h2>회원 정보</h2>
        <form>
            <input type="hidden" id="id" value="${principal.user.id}">
            <label for="username">Username</label><input type="username" name="username" id="username" value="${principal.user.username}" autocomplete="off" readonly style="background-color: #e2e2e2;">
            <label for="email">Email</label><input type="email" name="email" id="email" value="${principal.user.email}" autocomplete="off" readonly style="background-color: #e2e2e2;">
            <c:if test="${empty principal.user.oauth}">
                <label for="password">Password</label><input type="password" name="password" id="password" placeholder="Enter password" autocomplete="off">
            </c:if>
        </form>
    </div>
    <button id="btn-update">회원정보 수정하기</button>

</div>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>
