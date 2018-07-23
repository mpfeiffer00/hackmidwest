<!DOCTYPE html>

<html>
    <head>
        <title>My Things</title>
        <meta charset="UTF-8">
        <link rel="shortcut icon" href="https://blogs.cerner.com/favicon.ico">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
  </head>

    <body>
      <div class="main">
        <div id="navbar" class="content">
          <div class="navbar_left">
            <a href=""><img id="menu_icon" class="navbar_item icon" src="${pageContext.request.contextPath}/resources/images/hamburgermenu.png"></a>
            <span id="navbar_title" class="navbar_item font"><strong>My Things</strong></span>
          </div>
        <div class="navbar_right">
          <form id="scanForm" class="navbar_item" action="result" method="POST" enctype="multipart/form-data">
                    <img src="${pageContext.request.contextPath}/resources/images/barscan.png" class="icon" onclick="selectImage()">
                    <input id="scanBtn" type="file" name="file" onchange="loadImage()" accept="image/*" >
                </form>
          <a id="search_icon" class="navbar_item" href="search">
                    <img class="icon" src="${pageContext.request.contextPath}/resources/images/search.png">
                </a>
        </div>
      </div>

        <div id="mainContent" class="content">
                  <div style=" display: flex; margin: auto; padding: 2em">
                      <div class="rectangle font">Books</div>
                      <div class="rectangle font">Tools</div>
                  </div>
                  <div style=" display: flex; vertical-align: middle; margin: auto; padding: 2em">
                    <div class="rectangle font">Kitchenware</div>
                    <div class="rectangle font">Miscellaneous</div>
                </div>
          </div>
      </div>
      <script type="text/javascript">
        function loadImage(){
    	     document.getElementById("scanForm").submit();
    	};
      </script>
      <script type="text/javascript">
        function selectImage(){
             document.getElementById("scanBtn").click();
        };
      </script>
  </body>
</html>
