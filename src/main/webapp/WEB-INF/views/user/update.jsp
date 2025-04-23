<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<c:import url="/WEB-INF/views/templates/header.jsp"></c:import>
</head>
<body id="page-top">
	<div id="wrapper">
		<c:import url="/WEB-INF/views/templates/sidebar.jsp"></c:import>
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
			<c:import url="/WEB-INF/views/templates/topbar.jsp"></c:import>
				<div class="container-fluid" style="display: flex; justify-content: center;">
					<!-- contents -->
                    <div class="col-lg-7">
                        <div class="p-5">
                            <div class="text-center">
                                <h1 class="h4 text-gray-900 mb-4">정보 수정</h1>
                            </div>
                            <sec:authorize access="isAuthenticated()">
                            	<sec:authentication property="Principal" var="vo"/>
	                            <form:form action="" modelAttribute="userVO" method="post" cssClass="user" enctype="multipart/form-data">
	                                <div class="form-group">
	                                    <input type="file" class="form-control-file" id="exampleFormControlFile1" name="attach">
	                                </div>
	                                <div class="form-group row" style="margin-top: 8px; margin-bottom: 8px;">
	                                 
	                                    <div class="col-sm-6">
	                                        <form:input class="form-control form-control-user" id="name" path="name"  value="${vo.name}"></form:input>
	                                        <div style="margin-top: 8px;">
		                                        <form:errors path="name"></form:errors>
	                                        </div>
	                                    </div>
	                                </div>
	                                
	                    
	                                <div class="form-group" style="margin-top: 8px; margin-bottom: 16px;">
	                                    <form:input class="form-control form-control-user" id="phone"
	                                    value="${vo.phone}" path="phone"></form:input>
											<div style="margin-top: 8px;">
	                                        	<form:errors path="phone"></form:errors>
	                                        </div>
	                                </div>
	                                <div class="form-group" style="margin-top: 8px; margin-bottom: 16px;">
	                                    <form:input class="form-control form-control-user" id="email"
	                                    value="${vo.email}" path="email"></form:input>
	                                        <div style="margin-top: 8px;">
	                                        	<form:errors path="email"></form:errors>
	                                        </div>
	                                </div>
	                                <button type="submit" class="btn btn-primary btn-user btn-block">
	                                    저장
									</button>
	                            </form:form>
                            </sec:authorize>
                        </div>
                    </div>
				</div>
			</div>
			<!-- End Content -->
			  
			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content Wrapper -->
			<c:import url="/WEB-INF/views/templates/footer.jsp"></c:import>
	</div>
	<!-- End Wrapper -->

</body>
</html>