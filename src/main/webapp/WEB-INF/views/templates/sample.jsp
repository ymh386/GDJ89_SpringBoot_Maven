<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="/WEB-INF/views/templates/header.jsp"></c:import>
</head>
<body id="page-top">
	
	<!-- Page Wrapper -->
    <div id="wrapper">
    	<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
		    <!-- Main Content -->
		    <div id="content">
				<c:import url="/WEB-INF/views//templates/topbar.jsp"></c:import>
				<div class="container-fluid">
				<!-- contents 영역 -->
				<h1>Sample</h1>
				
			
					
				</div>
			</div>
			<!-- End of Main Content -->
			<!-- Footer -->
            <c:import url="/WEB-INF/views//templates/foot.jsp"></c:import>
		</div>
		<!-- End of Content Wrapper -->
    </div>
    <!-- End of Page Wrapper -->
	
	
	<c:import url="/WEB-INF/views//templates/footer.jsp"></c:import>
</body>
</html>