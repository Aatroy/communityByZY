function checkpost()
{
	if(form1.userName.value=="")
	{
		alert("请输入用户名");
		form1.userName.focus();
		return false;
	}
	if(form1.password.value=="")
	{
		alert("请输入密码");
		form1.password.focus();
		return false;
	}
	if(form1.confirm.value=="")
	{
		alert("请再次输入密码");
		form1.confirm.focus();
		return false;
	}
	if(form1.Email.value==""||form1.telephone.value =="")
	{
		alert("邮箱和手机号为找回密码的凭证，请填写!");
		form1.Email.focus();
		return false;
	}
	 alert("注册完成。若您没有跳转到登录页，请关注网页信息！");
}
function checkUserName() {
	var div = document.getElementById("divUser");
	div.innerHTML = "";
	var name = document.form1.userName.value;
	if(name.length > 15 || name.length < 4 )
	{
		div.innerHTML = "<font color=red>请输入5~15位长度的用户名</font>";
		return false;
	}
	return true;
}
function checkPassword() {
	var div = document.getElementById("divPwd");
	div.innerHTML = "";
	var password = document.form1.password.value;
	if(password.length < 6 || password.length > 18)
	{
		div.innerHTML = "<font color=red>请输入长度在6 ~ 18位的密码</font>";
		return false;
	}
	return true;
}

function checkConfirm() {
	var div = document.getElementById("divCfm");
	div.innerHTML = "";
	var confirm = document.form1.confirm.value;
	var password = document.form1.password.value;
	if ( confirm != password ) {
		div.innerHTML = "<font color=red>两次输入的密码不一致</font>";
		return false;
	}
	else {
		div.innerHTML = "<font color=#90ee90>密码一致</font>";
		return true;
	}
}

function checkFullName() {
	var div = document.getElementById("divFull");
	div.innerHTML = "";
	var fullName = document.form1.fullName.value;
	if(fullName.length < 2 || fullName.length > 4){
		div.innerHTML = "<font color = red> 请输入正确长度的名字</font>";
		return false;
	}
	return  true;
}
function  checkTel() {
    var div = document.getElementById("divTel");
	div.innerHTML = "";
	var tel = document.form1.telephone.value;
	if(tel.length!=11 || tel[0]!='1') {
		div.innerHTML = "<font color = red> 请输入正确的手机号码</font>"
		return false;
	}
	return true;
}
function checkEmail(){
	var div = document.getElementById("divEmail");
	div.innerHTML = "";
	var email = document.form1.Email.value;
	var sw = email.indexOf("@", 0);
	var sw1 = email.indexOf(".", 0);
	var tt = sw1 - sw;
	if (email.length == 0) {
		div.innerHTML = "<font color=red>电子邮件不能为空</font>";
		document.form1.text4.focus();
		return false;
	}
	if (email.indexOf("@", 0) == -1) {
		div.innerHTML = "<font color=red>电子邮件格式不正确，必须包含@符号！</font>";
		document.form1.text4.select();
		return false;
	}
	if (email.indexOf(".", 0) == -1) {
		div.innerHTML = "<font color=red>电子邮件格式不正确，必须包含.符号！</font>";
		document.form1.text4.select();
		return false;
	}
	if (tt == 1) {
		div.innerHTML = "<font color=red>邮件格式不对。@和.间应有其余字符！</font>";
		document.form1.text4.select();
		return false;
	}
	if (sw > sw1) {
		div.innerHTML = "<font color=red>电子邮件格式不正确，@符号必须在.之前</font>";
		document.form1.text4.select();
		return false;
	}
	else {
		return true;
	}
	return ture;
}