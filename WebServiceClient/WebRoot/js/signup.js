var default_img = "img/add.png";
var username;
var imageURL;

(function(){
	
	urlPrepare();
	
	$("#mask-file1").click(function(){
		$("#image1").trigger("click");
	});
	
	$("#image1").change(function(e){
		var file = document.getElementById("image1");
        if(file.files &&file.files[0]){
        	$("a#mask-file1 img").attr("src", window.URL.createObjectURL(file.files[0]));
        }
    });
	
	$("a#mask-file1 img").load(function(){
        refreshImgRegion();
    });
	
	refreshImgRegion();
	
	$("#submit").click(function(){
		alert($("#username").value);
		var image1 = $("#image1").val();
		if (image1 == undefined || image1 == ""){
			$("#image1").value = "default";
		}
		document.getElementById("form-username").value = document.getElementById("username").value;
		document.getElementById("form-password").value = document.getElementById("password").value;
		$("#form").submit();
		
	});
	
	$("#upload_target").load(function(){
    	var responseData = this.contentDocument.body.textContent || 
    	this.contentWindow.document.body.textContent;
    	if (responseData != "Failed"){
    		sessionStorage.setItem("username", username);
			sessionStorage.setItem("imageURL", responseData);
			alert(sessionStorage.getItem("username") + " " + sessionStorage.getItem("imageURL"));
			location.href = "board.html";
    	}
    });
})();

function urlPrepare(){
	$("#mask-file1 img").attr("src", default_img);
	$("#mask-file2 img").attr("src", default_img);
	$("#image1").attr("type", "file");
	$("#image2").attr("type", "file");
}

function refreshImgRegion(){
	var divHeight = $("#img-container").height();
    var img1 = $("a#mask-file1 img");
    var img2 = $("a#mask-file2 img");
    var inImg = $("div#img-container div img");

    img1.css("margin-top", (divHeight - img1.height())/2+"px");
    img1.css("margin-bottom", (divHeight - img1.height())/2+"px");
    img2.css("margin-top", (divHeight - img2.height())/2+"px");
    img2.css("margin-bottom", (divHeight - img2.height())/2+"px");
    inImg.css("padding-top", (divHeight - inImg.height())/2+"px");
    inImg.css("padding-bottom", (divHeight - inImg.height())/2+"px");
}

document.getElementById("login").onclick = function(){
	location.href = "login.html";
};

/*
document.getElementByID('submit').onclick = function(){
	document.getElementById("form-username").value = document.getElementById("username").value;
	document.getElementById("form-password").value = document.getElementById("password").value;
	var form = document.getElementById("form");
	form.submit;
}
*/