<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
<head>
<title>My Things - Book Search Result</title>
<meta charset="UTF-8">
<link rel="shortcut icon" href="https://blogs.cerner.com/favicon.ico">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/main.css">
</head>

<body style="width: 100%">
	<!--; background-color: #0d94d2;-->
	<div class="main">
		<div id="navbar" class="content">
			<div class="navbar_left">
				<a href="."><img id="menu_icon" class="navbar_item icon"
					src="${pageContext.request.contextPath}/resources/images/hamburgermenu.png"></a>
				<span id="navbar_title" class="navbar_item font"><strong>Book
						Search Result</strong></span>
			</div>
			<div class="navbar_right">
				<form id="scanForm" class="navbar_item" action="result"
					method="POST" enctype="multipart/form-data">
					<img
						src="${pageContext.request.contextPath}/resources/images/barscan.png"
						class="icon" onclick="selectImage()"> <input id="scanBtn"
						type="file" name="file" onchange="loadImage()" accept="image/*">
				</form>
				<a id="search_icon" class="navbar_item" href="search"> <img
					class="icon"
					src="${pageContext.request.contextPath}/resources/images/search.png">
				</a>
			</div>
		</div>

		<div id="mainContent" class="content">
			<div id="bookInfo">
				<c:forEach items="${bookResults}" var="book">
					<div id="bookResult" class="bookResult">
						<div id="bookTitle">
							<strong>${book.title}</strong>
						</div>
						<div id="bookAuthor">${book.author}</div>
						<img id="coverImg" src="${book.imageUrl}">
						<button id="addBookBtn" class="font" onclick="popup()">Add
							book to collection</button>
					</div>
				</c:forEach>
			</div>
		</div>
    ${whatever}
	</div>
	<script>
		function popup() {
			alert("Let's pretend that did something! :)");
		}
	</script>
	<script type="text/javascript">
		function loadImage() {
			document.getElementById("scanForm").submit();
		};
	</script>
	<script type="text/javascript">
		function selectImage() {
			document.getElementById("scanBtn").click();
		};
	</script>
</body>

</html>
