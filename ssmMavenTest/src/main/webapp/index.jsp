<%@ page contentType="text/html; charset=utf-8"%>
<!doctype html>
<html>
	<head>
		<title>获取图片验证码</title>
		<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery-3.3.1.min.js"></script>
	</head>
	<body>
	    <form action="##" method='post'>
	            <input type="hidden" id="userId" name="userId" value=""/> 
                <div class="form-group">
                    <div class="email controls">
                        <input type="text" name='loginName' id="loginName" placeholder="用户名" value="" class='form-control'/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="pw controls">
                        <input type="password" autocomplete="off" id="pwd" name="pwd" placeholder="密码" class='form-control'/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="email controls">
                        <input id="validateCode" onblur="checkImg(this.value)" name="validateCode" type="text" class="form-control" placeholder="输入验证码"/>   
                    </div>
                    <span class="y_yzimg"><img id="codeValidateImg"  onClick="javascript:flushValidateCode();"/></span>
                    <p class="y_change"><a href="javascript:flushValidateCode();"  >换一张</a></p>
                </div>

                <div class="form-group">
                    <span class="text-danger"></span>
                </div>

                <div class="submit">
                    <div class="remember">

                                <input type="checkbox" name="remember" value="1" class='icheck-me' data-skin="square" data-color="blue" id="remember">

                        <label for="remember">记住我</label>
                    </div>
                    <input type="button" value="登录" onclick="javascript:submitForm();" class='btn btn-primary'>
                </div>
		</form>
		<script type="text/javascript">
		$(document).ready(function() {
		     flushValidateCode();//进入页面就刷新生成验证码
		   });
		
		/* 刷新生成验证码 */
		function flushValidateCode(){
		var validateImgObject = document.getElementById("codeValidateImg");
		validateImgObject.src = "${pageContext.request.contextPath }/getSysManageLoginCode?time=" + new Date();
		}
		/*校验验证码输入是否正确*/
		function checkImg(code){
		    var url = "${pageContext.request.contextPath}/checkimagecode";
		    $.get(url,{"validateCode":code},function(data){
		        if(data=="ok"){
		    		//成功后可以跳转，一种办法是使用window.location.href="",但是是get方法不安全，另一种是写隐藏form表单
		            alert("ok!")
		        }else{
		            alert("error!")
		            flushValidateCode();
		        }
		    })
		}
		
		</script>
	</body>
</html>