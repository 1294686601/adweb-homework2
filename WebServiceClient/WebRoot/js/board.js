window.onload = function(){
	var display = document.getElementById("note");
	display.style.height = window.innerHeight - 230 +"px";

	var ds = document.getElementById("display");
	ds.style.height = (window.innerHeight - 230 - 75)+"px";
	ds.scrollTop = ds.scrollHeight;

	var write = document.getElementById("write");
	write.style.left = (window.innerWidth - 900)/2 + "px";
	
	init();
};

window.onresize = function(){
	var ds = document.getElementById("display");
	ds.style.height = (window.innerHeight - 230 - 75)+"px";

	var display = document.getElementById("note");
	display.style.height = window.innerHeight - 230 +"px";

	var write = document.getElementById("write");
	write.style.left = (window.innerWidth - 900)/2 + "px";
};

function init(){
	var xmlhttp;
	if (window.XMLHttpRequest){
		xmlhttp = new XMLHttpRequest();
	}else{
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xmlhttp.open("GET", "GetAllMessage", true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send();
	xmlhttp.onreadystatechange = function(){
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
			var responseContent = xmlhttp.responseText;
			addMessages(responseContent);
		}else if (xmlhttp.readyState == 4 && xmlhttp.status != 200){
			alert("连接出错，请检查网络！");
		}
	};
}

function addMessages(message){
	alert(message);
	var arr = eval("(" + message + ")");
	var l = arr.length;
	var s = "";
	for (var i = 0; i < l; i++){
		s += "<div class='noteItem'> ";
		s += "<img class='user-photo' src='" + arr[i].image + "'/> ";
		s += "<div class='blog-content'> <span class='blog-name'>" + ar[i].username +"</span> : ";
		s += "<span class='text-content' >" + arr[i].content +"</span>";
		s += "<div class='info'><span class='time'>"+ arr[i].time + "</span>";
		s += "<span class='agent'>来自网页</span>";
		s += "<span class='operation' onclick=deleteMethod(" + arr[i].messageID + ")>删除</span>";
		s += "</div>";
		s += "<div class='clear' ></div></div>";
	}
	document.getElementById("display").innerHTML = s;
}

document.getElementById("submit").onclick = function(){
	var promote = document.getElementById("promote").innerHTML;
	if(Number(promote) < 0){
		return ;
	}
	var text = document.getElementById("text");
	var content = text.value;
	text.value = "";
	content = content.replace(/\(F_(\d+).gif\)/g, '<img src="./img/faces/F_$1.gif">');
	var display = document.getElementById("display");
	display.scrollTop = display.scrollHeight;

	document.getElementById("promote").innerHTML = 140;
	
	/*
	var form = document.getElementById("addMessage");
	document.getElementById("message").value=content;
	form.submit();
	*/
	addMessage(content);
};

document.getElementById("text").oninput=function(){
	var text = document.getElementById("text");
	var remain = 140-text.value.length;
	document.getElementById("promote").innerHTML = remain;
};

document.getElementById("face").onclick = function(){
	var faceHtml = "<table>";
	for(var  i=0; i<4; i++){
		faceHtml += "<tr>";
		for(var j=0; j<10; j++){
			var id = i*10+j+1;
			if(id == 40){
				faceHtml += "<td>&nbsp;</td>";
				continue;
			}
			faceHtml += "<td id='F_"+id+".gif' background='img/faces/F_"+id+".gif' onclick='chooseFace(this.id)'>&nbsp;</td>";
		}
		faceHtml += "</tr>";
	}
	faceHtml += "</table>";
	var faceDiv = document.getElementById("faceDiv");
	faceDiv.innerHTML = faceHtml;
	faceDiv.style.display = "inherit";
	faceDiv.style.position = "absolute";
	faceDiv.style.top = "45px";
	faceDiv.style.left = "0px";
	faceDiv.style.background = "#eee";
};

function chooseFace(id){
	faceDiv.style.display = "none";
	var text = document.getElementById("text");
	text.value = text.value + "("+id+")";
	var remain = 140 - text.value.length;
	document.getElementById("promote").innerHTML = remain;
}

function deleteMethod(id){
	deleteMessage(id);
	/*
	document.getElementById("messageID").value = id;
	document.getElementById("deleteMessage").submit();
	*/
}

function addMessage(message){
	var username = sessionStorage.getItem("username");
	var iamgeURL = sessionStorage.getItem("imageURL");
	
	var xmlhttp;
	if (window.XMLHttpRequest){
		xmlhttp = new XMLHttpRequest();
	}else{
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xmlhttp.open("POST", "AddMessage", true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send("username="+username+"&imageURL="+imageURL+"&message="+message);
	xmlhttp.onreadystatechange = function(){
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
			var responseContent = xmlhttp.responseText;
			alert(responseContent);
			location.href = "board.html";
		}else if (xmlhttp.readyState == 4 && xmlhttp.status != 200){
			alert("连接出错，请检查网络！");
		}
	};
}

function deleteMessage(id){
	var xmlhttp;
	if (window.XMLHttpRequest){
		xmlhttp = new XMLHttpRequest();
	}else{
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xmlhttp.open("POST", "DeleteMessage", true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send("messageID="+id);
	xmlhttp.onreadystatechange = function(){
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
			var responseContent = xmlhttp.responseText;
			alert(responseContent);
			location.href = "board.html";
		}else if (xmlhttp.readyState == 4 && xmlhttp.status != 200){
			alert("连接出错，请检查网络！");
		}
	};
}