<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
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
				<div class="container-fluid">
					
					<h1>Chat List</h1>
							<div class="table-responsive">
								<table class="table table-bordered" id="dataTable" width="100%"
									cellspacing="0">
									<tbody>
										<c:forEach items="${list}" var="vo">
											<tr>
												<td>${vo.username}</td>
												<td>${vo.name}</td>
												<td>
													<div class="dropdown-list-image mr-3">
														<img width="30" height="30" class="rounded-circle" src="/files/user/${vo.fileName}"
															alt="...">
														<div class="status-indicator bg-warning"></div>
													</div>
												</td>
												<td>
													<div style="width:15px; height:15px">
														<span data-receiver-name="${vo.username}" class="receiver-name btn ${vo.status?'btn-primary':'btn-danger'} btn-circle" data-toggle="modal" data-target="#chat"></span>
													</div>
												</td>
												
											</tr>
										</c:forEach>

									</tbody>
								</table>
							</div>					
					
			
					
					
				</div>
			</div>
			<!-- End Content -->
			<c:import url="/WEB-INF/views/templates/foot.jsp"></c:import>
		</div>
		<!-- End Content-wrapper -->	
	</div>
	<!-- End wrapper -->

	
	
	<c:import url="/WEB-INF/views/templates/footer.jsp"></c:import>
	<!-- Chat Modal -->
	<sec:authentication property="name" var="username" />
	<div class="modal" tabindex="-1" id="chat" data-sender-name="${username}">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">Modal title</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        <div id="chat-body">
				
			</div>
		</div>
		<div class="modal-footer">
			<div>
			  <input type="hidden" id="receiver" value=""> <input type="text" id="message"><button id="send">Send</button>
			</div>
	        <!-- <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary">Save changes</button> -->
	      </div>
	    </div>
	  </div>
	</div>
	
	
	<script src="/js/chat/chat.js"></script>	
</body>
</html>