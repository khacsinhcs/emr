<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
	<h1>Page not found</h1>
 
	<c:choose>
		<c:when test="${empty username}">
		  <h2>This page does not exist. Please turn back!</h2>
		</c:when>
		<c:otherwise>
			<h2>Username: ${username} <br/>
				This page does not exist. Please turn back!
      		</h2>
		</c:otherwise>
	</c:choose>
 
</body>
</html>