<!DOCTYPE html>
<html>
<title>Crucible Aggregator</title>
<head>
<link rel="shortcut icon" href="https://blogs.cerner.com/favicon.ico">
</head>
<body style="width: 100%; background-color: #0d94d2;">
<div style="max-width: 960px; 
    overflow: auto;
    position: absolute;
    top:0;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 10;

    margin: auto;
    background-color: white;">
<table style="height: 100%; width: 100%; padding: 2em"><tr><td>
<div style="position: relative; top: 0; bottom: 0; padding: 10">
<table>
  <tr>
    <td style="vertical-align: center; align: center;"><a href="/crucible-aggregator"><img src="https://www.cerner.com/-/media/cerner-media-folder/home-page/cerner_rgb_standard_-horizontal.png" width="250px"></a></td>
    <td style="font-size: xx-large; vertical-align: center; padding: 10px; align: center;"><a href="/crucible-aggregator" style="color: inherit; text-decoration:none;"><b>Crucible Review Aggregator</b></a></td>
  </tr>
</table>
<h2>${headerText}</h2>
<p>${reviewInformation}</p>

<h2>${githubHeader}</h2>
<p>${githubIssues}</p>

</div></td></tr>

<tr><td style="height: 100%; vertical-align: bottom;">
<div style="overflow: auto; position: relative; right: 0; bottom: 0; left: 0; text-align: right;">
<p style="font-size: 75%">Created by <a href="mailto:aaron.mcginn@cerner.com">Aaron McGinn</a> &copy; 2017 Cerner Corporation | ${hitCount}
</div>
</td></tr></table>

</div>
${script}
</body>
</html>