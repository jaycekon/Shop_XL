<%@ page import="com.Shop.Model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Shop.Util.Page" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/15 0015
  Time: 下午 3:28
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

</head>
<body>

<header class="ui-header ui-header-stable ui-border-b">
    <h1>店家列表</h1>
</header>

<footer class="ui-footer ui-footer-stable ui-border-t">
    <ul class="ui-tiled">
        <li class="footerItem"><i class="ui-icon-personal" onclick="window.location.href='<%=request.getContextPath()%>/roleCenter'"></i><div>角色中心</div></li>
    </ul>
</footer>

<section class="ui-container">

    <!-- [[店家列表 -->
    <table class="table ui-table ui-border ui-border-tb">
        <thead>
        <tr>
            <th>头像</th>
            <th class="textLeftTd">昵称</th>
        </tr>
        </thead>
        <tbody>
        <%
            Page<User> pages =(Page<User>)request.getAttribute("page");
            List<User> users = pages.getList();
            for(User user:users){
        %>
        <tr>
            <td class="imgTd">
                <img src="<%=user.getImg()%>" alt="">
            </td>
            <td class="textLeftTd"><%=user.getUsername()%></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table><!-- 店家列表]] -->

    <input id="recordNum" hidden="hidden" value="<%=pages.getBeginIndex()%>" />
    <!-- [[分页-->
    <div class="paging">
        <%
            if(pages.getBeginIndex()!=0){
        %>
        <span class="prev btn btn-primary" id="prevPage" >上一页</span>
        <%
            }

            if((pages.getBeginIndex()+10) < pages.getTotalCount()){
        %>

        <span class="next btn btn-primary" id="nextPage">下一页</span>
        <%
            }
        %>
    </div>
</section>

<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/zepto.min.js"></script>
<script src="<%=request.getContextPath()%>/app/frontStage/lib/js/frozen.js"></script>
<script class="demo-script">
    $(".ui-dialog").dialog("show");

    var num = parseInt( $('#recordNum').val() );
    $("#prevPage").click(function(){
        var suffix = num-10;
// 返回url
        var url = handleURL(suffix);
// 跳转url
        window.location.href = url;
    });

    $("#nextPage").click(function(){
        var suffix = num+10;
// 返回url
        var url = handleURL(suffix);
// 跳转url
        window.location.href = url;
    });

    // 处理url的函数
    function handleURL(suffix){    // suffix 后缀 为int类型
        var pathname = window.location.pathname.split('/');
        if(pathname.length==2){
            pathname = pathname.concat(suffix);
        }else{
            pathname.pop();
            pathname = pathname.concat(suffix);
        }
        return url = pathname.join('/');
    }
</script>

</body>
</html>
