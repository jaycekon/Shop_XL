<%@ page import="com.Shop.Model.Address" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Model.Area" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/2 0002
  Time: 下午 3:18
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
    <h1>我的收货地址</h1>
</header>

<%@include file="../footer.jsp" %>

<section class="ui-container">
    <%
        List<Address> addresses =(List<Address>) request.getAttribute("addresses");
    %>
    <ul class="ui-list-active">
        <%
            for(Address address:addresses){

        %>
        <li class="addressInfo blockInfo ui-border-tb addressList">
            <%--<input type="hidden" value="<%=address.getId()%>" class="addressId"/>--%>
            <p>姓名：<%=address.getUsername()%></p>
            <p>电话：<%=address.getPhone()%></p>
                <%
                    if(address.getA()!=null){
                        Area city = address.getA().getArea();
                        Area s = city.getArea();
                %>
            <p>地址：<%=s.getName()+city.getName()+address.getA().getName()+address.getAddress()%></p>
                <%
                    }else{
                        %>
                <p>地址：<%=address.getAddress()%></p>

                <%
                    }
                %>
            <button class="icon iconfont RTIcon" onclick="window.location.href='<%=request.getContextPath()%>/editAddress/<%=address.getId()%>?flagt=0'">&#xe603;</button>
            <button class="icon iconfont ui-icon-delete" onclick="window.location.href='<%=request.getContextPath()%>/deleteAddress/<%=address.getId()%>'"></button>
        </li>


        <%
            }
        %>
    </ul>

    <div class="ui-btn-wrap">
        <button class="ui-btn-lg productBtn" onclick="window.location.href='<%=request.getContextPath()%>/addAddress/0'">
            新增收货地址
        </button>
    </div>


</section>


<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<%--<script>--%>
<%--alert('ty');--%>
<%--$(document).on('tap','.addressList',function(){--%>
<%--alert("123")--%>
<%--/*地址的id*/--%>
<%--var id = parseInt( $(this).find('.addressId').val() );--%>
<%--$.ajax({--%>
<%--url: '<%=request.getContextPath()%>/myAddress',--%>
<%--type: 'POST',--%>
<%--data: 'id='+id--%>
<%--});--%>
<%--});--%>
<%--</script>--%>

</body>
</html>