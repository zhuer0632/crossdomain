<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#assign path="${request.getContextPath()}">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>logon</title>
		<link href="${path}/css/global.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${path}/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="${path}/js/cookie.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
			<#if  cookie??>
		   	
		   	// var _frm = document.createElement("iframe");
		 	// _frm.style.display="none";
		 	// _frm.src="http://192.168.1.215:8080/xj/l/checkLogon.do?cookie=${cookie}";
		 	// document.body.appendChild(_frm); 		
			 
			// alert("直接从服务器端："+getCookieByKey("JSESSIONID"));
			 
			</#if>
			
			<#if  hascookie??>
				alert(getCookieByKey("JSESSIONID"));
			</#if>
	

			});
		</script>
	</head>
	<body>
		${path}
		<br />
		登录页面
		<br />

		<div>

			用新疆项目的登录做测试【测试二级域名能否自动传递cookie】-结果-可以
			<a href="http://b.bbb.com:8080/xj/adminCom/listComMgrtoAudit.do"
				target="_blank">215 xj中的受限页面</a>

			<br>

				<form action="${path}/PriceSys/login_xj.do" method="post">
					<input name="nickname" value="admin" />
					<br />
					<input name="password" value="admin888" />
					<br />
					<input type="submit" value="提交" />

				</form>
		</div>

		<div>

			用新疆项目的登录做测试【测试跨域登录】
			<a href="http://192.168.1.215:8080/xj/adminCom/listComMgrtoAudit.do"
				target="_blank">215 xj中的受限页面</a>

			<br>
				<form action="${path}/PriceSys/login_xj_2.do" method="post">
					<input name="nickname" value="admin" />
					<br />
					<input name="password" value="admin888" />
					<br />
					<input type="submit" value="提交" />
					<BR>
						${cookie} 
				</form>
		</div>


		<div>
			登录 财价系统的 网址是：
			<a href="http://xj.gldjc.com/info_prices/22145/trend" target="_blank">查看登录后的网址</a>
			<form action="${path}/PriceSys/login.do" method="post">
				<input name="nickname" value="zhushaolong321 " />
				<br />
				<input name="password" value="zhushaolong321" />
				<br />
				<input type="submit" value="提交" />
			</form>
		</div>

		<div>

			注销
			<form action="http://xj.gldjc.com/api/users/signout" method="get">
				<input name="auth_token" value="昵称" />
				<br />
				<input type="submit" value="提交" />
			</form>
		</div>

		<div>
			验证
			<form action="http://xj.gldjc.com/api/users/check" method="get">
				<input name="nickname" value="昵称" />
				<br />
				<input type="submit" value="提交" />
			</form>
		</div>

		<div>
			注册
			<br>
				<form action="http://xj.gldjc.com/api/users/reg" method="post">
					昵称
					<input name="nickname" value="zhushaolong_123" />
					必须存在且不能重复[字母开头4-16个英文字母或下划线]
					<br />
					密码
					<input name="password" value="密码" />
					不限制
					<br />
					确认
					<input name="password_confirmation" value="确认密码" />
					不限制
					<br />
					邮箱
					<input name="email" value="邮箱" />
					必须存在且不能重复
					<br />
					名称
					<input name="name" value="名称" />
					不限制
					<br />
					手机
					<input name="mobile_phone" value="18610838555" />
					必须存在且不能重复
					<br />
					公司
					<input name="company_name" value="ccccc" />
					不限制
					<br />
					<input type="submit" value="提交" />
				</form>
		</div>

		<div>


			// 已经存在的账号信息如下
			<br>
				// nickname zhushaolong321 <br>
					// password zhushaolong321 <br>
						// password_confirmation zhushaolong321<br>
							// email zhushaolong111@qq.com<br>
								// name 张非非<br>
									// mobile_phone 18610838666<br>
										// company_name 北京和展科技有限公司<br>



											//
											关于登录名、手机号、email不能重复的验证都走一个接口，参数不一样而已(参数为：nickname、mobile_phone、email)<br>
												// // 登录名、手机号、email必须存在且不能重复<br>
													// 登录名：必须字母开头在4到16个英文字母或下划线组成，不能用中文",:with =><br>
														// /^[a-zA-Z][a-zA-Z0-9_]{3,15}$/, :if => Proc.new {|x|
														x.new_record? }<br>
															//
															email格式：符合正则/^[0-9a-z][_.0-9a-z-]{0,31}@([0-9a-z][0-9a-z-]{0,30}[0-9a-z]\.){1,4}[a-z]{2,4}$/i<br>
																// 手机号格式：符合正则/^[0-9]{11}$/<br>
																	// 两次密码必须一致!<br>
		</div>


	</body>
</html>
