document.getElementById("submit").onclick = function(){
	var username = document.getElementById('username').value;
	var password = document.getElementById('password').value;
	
	var xmlhttp;
	if (window.XMLHttpRequest){
		xmlhttp = new XMLHttpRequest();
	}else{
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xmlhttp.open("POST", "Login", true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send("username="+username+"&password="+password);
	xmlhttp.onreadystatechange = function(){
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
			var responseContent = xmlhttp.responseText;
			alert(responseContent);
			if (responseContent != "Failed") {
				sessionStorage.setItem("username", username);
				sessionStorage.setItem("imageURL", responseContent);
				alert(sessionStorage.getItem("username") + " " + sessionStorage.getItem("imageURL"));
				location.href = "board.html";
			}
			else {
				document.getElementById("login-message").innerHTML = "用户名或密码不正确.";
			}
		}else if (xmlhttp.readyState == 4 && xmlhttp.status != 200){
			alert("");
		}
	};
};

document.getElementById("signup").onclick = function(){
	location.href = "signup.html";
};