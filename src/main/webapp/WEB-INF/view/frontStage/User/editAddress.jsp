<%@ page import="com.Shop.Model.Address" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/7 0007
  Time: 上午 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta name="format-detection" content="telephone=no, email=no">
    <title>百城万店倾销网</title>

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/lib/css/frozen.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/css/index.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/css/address.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/frontStage/font/iconfont.css">

</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <i class="ui-icon-return" onclick="history.back()"></i>
    <h1>编辑收货地址</h1>
</header>

<%@include file="../footer.jsp"%>

<section class="ui-container">
<%
    Address address= (Address)request.getAttribute("address");
%>
    <div class="ui-form ui-border-t">
        <form action="<%=request.getContextPath()%>/editAddress" method = "post">
            <div class="ui-form-item ui-form-item-show ui-border-b">
                <input type="hidden" name="id" value="<%=address.getId()%>"/>
                <label for="#">姓名</label>
                <input type="text" name ="name" value="<%=address.getUsername()%>" placeholder="填写联系人姓名">
            </div>
            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label for="#">联系方式</label>
                <input type="text" name ="phone" value="<%=address.getPhone()%>" placeholder="填写联系人电话">
            </div>
            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label>省市</label>
                <input type="text" value="" name ="<%=address.getArea()%>" placeholder="填写收货省城市">
            </div>

            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label for="#">详细地址</label>
                <input type="text" value="<%=address.getAddress()%>" name ="address" placeholder="填写详细收货地址">
            </div>
            <div class="ui-form-item ui-form-item-checkbox ui-border-b">
                <label class="ui-checkbox">
                    <input type="checkbox" name="flag" value="1">
                </label>
                <p>设为默认收货地址</p>
            </div>


            <div class="ui-btn-wrap">
                <input type="submit" class="ui-btn-lg productBtn">

                </input>
                <input type ="reset" class="ui-btn-lg">

                </input>
            </div>
        </form>
    </div>




</section>


<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/js/index.js"></script>

</body>
</html>