<%@ page import="com.Shop.Model.Areas" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.Shop.Util.Page" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/4/13 0013
  Time: 下午 8:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>百城万店-大列区表</title>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/app/backStage/css/userManager.css">

</head>
<body>

<div class="wrapper">

    <%@ include file="../header.jsp"%>

    <!-- [[页面主体 -->
    <div class="mainBody container-fluid">

        <div class="row">

            <!-- [[ 左边导航 -->
            <%@ include file="../left.jsp"%>
            <!-- 左边导航]] -->

            <!-- [[右边操作页面 -->
            <div class="content col-md-10 userManager-port">
                <div class="tab">
                    <a href="<%=request.getContextPath()%>/myCrod"><button class="btn btn-primary">生成二维码</button></a>
                </div>
                <div class="table-responsive">
                    <table class="table table-striped table-bordered table-hover">
                        <caption>大区列表</caption>
                        <thead>
                        <tr>
                            <td>头像</td>
                            <td class="textLeft">昵称</td>
                            <td>关注时间</td>
                            <td class="textRight">总收益佣金（元）</td>
                            <td class="textRight">可支配佣金（元）</td>
                            <td class="textRight">待收益佣金（元）</td>
                        </tr>
                        </thead>
                        <%
                            String model = "yyyy-MM-dd HH:mm:ss";
                            SimpleDateFormat format = new SimpleDateFormat(model);
                            Page<Areas> pages = (Page<Areas>)request.getAttribute("page");
                            List<Areas> areas =pages.getList();
                        %>
                        <tbody>
                        <%
                            for(Areas a:areas){
                        %>
                        <tr>
                            <td class="imgTd">
                                <img src="<%=a.getImg()%>" alt="">
                            </td>
                            <td class="textLeft"><%=a.getName()%></td>
                            <td><%
                                if(a.getDate()!=null){
                                    out.println(format.format(a.getDate()));
                                }
                            %></td>
                            <td class="textRight"><%=a.getTotalCommission()%></td>
                            <td class="textRight"><%=a.getExitCommission()%></td>
                            <td class="textRight"><%=a.getWaitCommission()%></td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>

                <!-- [[分页-->
                <div class="paging">
                    <%
                        if(pages.getBeginIndex()!=0){
                    %>
                    <span class="prev btn btn-primary" onclick="window.location.href='<%=request.getContextPath()%>/listAreas/<%=(pages.getBeginIndex()-10)%>'">上一页</span>
                    <%
                        }

                        if((pages.getBeginIndex()+10) < pages.getTotalCount()){
                    %>

                    <span class="next btn btn-primary"  onclick="window.location.href='<%=request.getContextPath()%>/listAreas/<%=(pages.getBeginIndex()+10)%>'">下一页</span>
                    <%
                        }
                    %>
                </div>
                <!-- 分页]]-->
            </div>
            <!-- 右边操作页面]] -->
        </div>

    </div>
    <!-- 页面主体]] -->
</div>
<!-- wrapper]] -->

<script src="<%=request.getContextPath()%>/app/backStage/lib/jquery/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/lib/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/app/backStage/js/common.js"></script>

</body>
</html>