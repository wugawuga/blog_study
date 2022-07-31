<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../layout/header.jsp"%>
<div class="container">
    <div class="container-join">
        <h2>회원가입</h2>
        <form>
            <input type="username" name="username" id="username" placeholder="user name" autocomplete="off">
            <input type="email" name="email" id="email" placeholder="user email" autocomplete="off">
            <input type="password" name="password" id="password" placeholder="password" autocomplete="off">
        </form>

        <button id="btn-save">회원가입</button>
    </div>

</div>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp"%>
