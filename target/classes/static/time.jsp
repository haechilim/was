<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Simple Date Example</title>
    <link href="favicon.ico" rel="icon" type="image/x-icon">
    <link href="css/notoserifkr.css" rel="stylesheet">
    <link href="css/blackhansans.css" rel="stylesheet">
    <link href="css/common.css" rel="stylesheet">
</head>
<body>
    <jsp:useBean id='clock' class='java.util.Date' />
    <p class="clock">
        <span><jsp:getProperty name="clock" property="hours"/></span>시
        <span><jsp:getProperty name="clock" property="minutes"/></span>분
        <span><jsp:getProperty name="clock" property="seconds"/></span>초
    </p>
</body>
</html>