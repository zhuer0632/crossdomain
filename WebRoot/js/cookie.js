// ***********************
// cookie操作//
// *********************
function addCookie(objName,objValue){// 添加cookie
// alert("key:"+objName+" value:"+objValue);
var str = objName + "=" + escape(objValue);
document.cookie = str;
}

function delCookie(name){// 为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间
var date = new Date();
date.setTime(date.getTime() - 10000);
document.cookie = name + "=a; expires=" + date.toGMTString();
}


function getCookieByKey(objName){// 获取指定名称的cookie的值
var arrStr = document.cookie.split("; ");
	for(var i = 0;i < arrStr.length;i ++){
	var temp = arrStr[i].split("=");
	if(temp[0] == objName) return unescape(temp[1]);
	} 
}


function exitCookie(){// 判断cookie是否有值
var str = document.cookie;
if(str == ""){
return false;
}
return true;
}
function getAllCookies(){// 查询cookie中所有
return document.cookie;
 
}