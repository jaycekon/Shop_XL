<%@ page import="com.Shop.Model.Address" %>
<%@ page import="com.Shop.Model.Area" %>
<%@ page import="java.util.List" %><%--
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
    <h1>新增收货地址</h1>
</header>

<%@include file="../footer.jsp"%>

<section class="ui-container">

    <div class="ui-form ui-border-t">
        <%
            List<Area> areas= (List<Area>)request.getAttribute("areas");
            Address address = (Address) request.getAttribute("address");
        %>
        <form action="<%=request.getContextPath()%>/editAddress" method = "post">
            <input type="hidden" name="id" value="<%=address.getId()%>"/>
            <input type="hidden" name="flagt" value ="<%=request.getAttribute("flagt")%>"/>
            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label for="#">姓名</label>
                <input type="text" name ="username" value="<%=address.getUsername()%>" placeholder="填写联系人姓名">
            </div>
            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label for="#">联系方式</label>
                <input type="text" name ="phone" value="<%=address.getPhone()%>" placeholder="填写联系人电话">
            </div>
            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label>省</label>
                <div class="ui-select">
                    <select name = "areaId" oninput="queryCity(this.value)">
                        <option value="">--请选择省份--</option>
                        <%
                            for(Area area:areas){
                        %>
                        <option name ="areaId" value ="<%=area.getId()%>"><%=area.getName()%></option>
                        <%
                            }
                        %>
                    </select>
                </div>
            </div>

            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label>市</label>
                <div class="ui-select">
                    <select class="compAddr forget_select" name="cityId" id="city" oninput="queryArea(this.value)" >
                    </select>
                </div>
            </div>

            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label>区</label>
                <div class="ui-select">
                    <select class="compAddr forget_select" name="area_id" id="area">
                    </select>
                </div>
            </div>



            <div class="ui-form-item ui-form-item-show ui-border-b">
                <label for="#">详细地址</label>
                <input type="text" value="<%=address.getAddress()%>" name ="address" placeholder="填写详细收货地址">
            </div>
            <div class="ui-form-item ui-form-item-checkbox ui-border-b">
                <label class="ui-checkbox">
                    <input type="checkbox" name = "flag" value="1" >
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

<script>
    function queryCity(obj){
        $("#provinceError").hide();
        $("#cityError").hide();
        var urlStr = "<%=request.getContextPath()%>/findCity?area_id="+obj;
        //alert("Before Call:"+urlStr);
        $.ajax({
            method: "GET",
            url: urlStr,
            success:function(data,status,jqXHR){
                //alert(data);
                $("#city").html("");
                for(var i=0;i<data.length;i++){
                    $("#city").append("<option value="+data[i].id+">"+data[i].name+"</option>");
                }
                queryArea(data[0].id);
            }
        }); // end ajax
    }


    function queryArea(obj){
        $("#provinceError").hide();
        $("#cityError").hide();
        var urlStr = "<%=request.getContextPath()%>/findCity?area_id="+obj;
        //alert("Before Call:"+urlStr);
        $.ajax({
            method: "GET",
            url: urlStr,
            success:function(data,status,jqXHR){
                //alert(data);
                $("#area").html("");
                for(var i=0;i<data.length;i++){
                    $("#area").append("<option value="+data[i].id+">"+data[i].name+"</option>");
                }

            }
        }); // end ajax
    }
</script>
</body>
</html>