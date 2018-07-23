<!DOCTYPE html>

<html>
<head>
<title>My Things</title>
<meta charset="UTF-8">
<link rel="shortcut icon" href="https://blogs.cerner.com/favicon.ico">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/main.css">
</head>

<body>
	<div class="main">
		<div id="navbar" class="content">
			<div class="navbar_left">
				<a href="."><img id="menu_icon" class="navbar_item icon"
					src="${pageContext.request.contextPath}/resources/images/hamburgermenu.png"></a>
				<span id="navbar_title" class="navbar_item font"><strong>My
						Things</strong></span>
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
			<span id="search" class="font">
				<form id="searchForm" action="result">
					<label for="searchInput">Search Books: </label>
					<div>
						<input id="searchInput" name="q" type="search" required autofocus>
						<input id="searchBtn" type="image"
							src="${pageContext.request.contextPath}/resources/images/search.png"
							alt="Search">
					</div>
				</form>
			</span>
			<p id="isbnInfo" class="font">ISBN Search information: A unique
				identifier for all books since 1970. The 10 or 13 digit number can
				be found on the back cover near the barcode or on the page
				containing the publisher and copyright information.</p>
		</div>
	</div>
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