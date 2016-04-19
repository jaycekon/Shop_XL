<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<c:set var="contextPath" value="<%=request.getContextPath()%>"></c:set>
<!doctype html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1" />
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script src="${contextPath}/app/frontStage/lib/js/zepto.min.js"></script>
		<title>微信支付</title>
	</head>
	<script type="text/javascript">
		<%
			Map<String,Object> payMap =(Map<String,Object>)request.getAttribute("payMap");
			System.out.println("paySign:"+payMap.get("paySign"));
			System.out.println("Package:"+payMap.get("Package"));
			System.out.println("nonceStr:"+payMap.get("nonceStr"));
			System.out.println("timestamp:"+payMap.get("timestamp"));
		%>
		$(function(){
			var urlStr = "<%=request.getContextPath()%>/weixin/signature?url="+location.href.split('#')[0];
			//alert("Before Call:"+urlStr);
			$.ajax({
				method: "GET",
				url: urlStr,
				success:function(data,status,jqXHR){
					var result=data;
					var appId=result.appId;
					var signature = result.signature;
					var timeStamp = result.timeStamp;
				    var nonceStr = result.nonceStr;		 
				    wx.config({
				        debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				        appId: appId, // 必填，公众号的唯一标识
				        timestamp:timeStamp , // 必填，生成签名的时间戳
				        nonceStr: nonceStr, // 必填，生成签名的随机串
				        signature: signature,// 必填，签名，见附录1
				        jsApiList: ['chooseWXPay'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				    });
				    wx.ready(function(){
				        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
				        wx.chooseWXPay({
				            timestamp: "<%=payMap.get("timestamp")%>", // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
				            nonceStr:"<%=payMap.get("nonceStr")%>", // 支付签名随机串，不长于 32 位
				            package: "<%=payMap.get("Package")%>", // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
				            signType: "MD5", // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
				            paySign: "<%=payMap.get("paySign")%>", // 支付签名
				            complete: function (res) {
				                // 支付成功后的回调函数
				                	   <%--window.location.href="<%=request.getContextPath()%>/myCount/0";--%>
				            }
				        }); 
				    });
				}
			}); // end ajax   
		})
	</script>
	<body>
	</body>
</html>