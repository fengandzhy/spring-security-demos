<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>dba</title>
</head>
<body>
	Dear <strong>${user}</strong>, Welcome to DBA Page.
    <a href="<c:url value="/logout" />">Logout</a>
</body>
</html>
