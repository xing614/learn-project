使用shiro做角色权限控制
ShiroConfiguration.java：shiro启动时候的初始化工作，比如哪些是需要认证，哪些不需要认证；缓存配置设置；shiro权限数据在页面展示时整合需要的模板套件配置，等等。

ShiroRealm.java：shiro权限认证的具体实现代码，因为shiro本身只提供拦截路由，而具体如何数据源则由用户自己提供，不同的项目不同的要求，要不要加缓存登陆验证次数，要不要密码加密设置其他具体方式，这些都由用户自己决定，而shiro只提供给用户权限验证的格式接口，通过用户提供的数据源shrio判断要不要给具体用户授权请求路径的判断。 
ShiroRealm 涉及到以下点： 
principal:主体，就是登陆的当前用户类型的数据实体 
credentials：凭证，用户的密码，具体加密方式用户自己实现，什么都不做就是原文

Roles：用户拥有的角色标识（角色名称，admin,account,customer_service），字符串格式列表:用户拥有多个角色的可能
Permissions：用户拥有的权限标识（每个权限唯一标识，比如主键或者权限唯一标识编码），字符串格式列表：用户拥有多个权限的可能

需要确认前后端通信的json标准，自己设置：
"data":{
	"code":0,
	"data":{
		"token":"32ed078a-e0de-420d-8cdd-5dca3f628cf0",
		"status":200,
		"message":"登录成功"
		},
	"msg":"请求成功"
	}



整体：
{
	"data":{
		"code":0,
		"data":{
			"token":"32ed078a-e0de-420d-8cdd-5dca3f628cf0",
			"status":200,
			"message":"登录成功"
			},
		"msg":"请求成功"
		},
	"status":200,
	"statusText":"",
	"headers":{
		"content-type":"application/json;charset=UTF-8"
		},
	"config":{
		"transformRequest":{},
		"transformResponse":{},
		"timeout":0,
		"xsrfCookieName":"XSRF-TOKEN",
		"xsrfHeaderName":"X-XSRF-TOKEN",
		"maxContentLength":-1,
		"headers":{
			"Accept":"application/json, 
			text/plain, 
			*/*",
			"Content-Type":"application/json;charset=utf-8"
		},
		"method":"post",
		"url":"http://127.0.0.1:8081/ajaxLogin",
		"data":"{\"username\":\"admin\",\"password\":\"123456\"}",
		"params":null,
		"baseURL":"http://127.0.0.1:8081",
		"withCredentials":false
	},
	"request":{}
}