<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
				<h1 class="h3 mb-2 text-gray-800">
                    <spring:message code="board.notice.title"></spring:message>
                </h1>
				<!-- Page Heading -->
                    

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">DataTables Example</h6>
                        </div>
                        <div class="card-body">
                        	<form method="get" class="form-inline mb-4">
	                            <div class="col-auto">
							      <select class="custom-select mr-sm-2" name="kind" id="searchKind">
							        <option value="k1">제목</option>
							        <option value="k2">내용</option>
							        <option value="k3">작성자</option> 
							      </select>
							    </div>
							  <div class="form-group">
							    <input type="text" class="form-control" name="search" id="search" placeholder="Search" aria-label="Recipient's username" aria-describedby="button-addon2">
							  </div>
							  <button type="submit" class="btn btn-primary mx-2">Search</button>
							</form>
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>Num</th>
                                            <th>Title</th>
                                            <th>User</th>
                                            <th>Date</th>
                                            <th>Hit</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach items="${list}" var="vo">
                                    		<tr>
                                    			<td>${vo.boardNum}</td>
                                    			<td><a href="./detail?boardNum=${vo.boardNum}">${vo.boardTitle}</a></td>
                                    			<td>${vo.userName}</td>
                                    			<td>${vo.boardDate}</td>
                                    			<td>${vo.boardHit}</td>
                                    		</tr>
                                    	</c:forEach>
                                    </tbody>
                                    
                                </table>
                            </div>
	                           <div class="row justify-content-between">
								<nav aria-label="Page navigation example col-md-6">
								  <ul class="pagination">
								    <li class="page-item">
								      <a class="page-link" href="./list?page=${pager.start-1}&kind=${pager.kind}&search=${pager.search}" aria-label="Previous">
								        <span aria-hidden="true">&laquo;</span>
								      </a>
								    </li>
								    <c:forEach begin="${pager.start}" end="${pager.end}" var="i">
								    <li class="page-item"><a class="page-link" href="./list?page=${i}&kind=${pager.kind}&search=${pager.search}">${i}</a></li>
								    </c:forEach>
								    <li class="page-item">
								      <a class="page-link" href="./list?page=${pager.next?pager.end+1:pager.end}&kind=${pager.kind}&search=${pager.search}" aria-label="Next">
								        <span aria-hidden="true">&raquo;</span>
								      </a>
								    </li>
								  </ul>
								</nav>							
								
								<sec:authorize access="hasRole('ADMIN')">
									<div style="display: flex; justify-content: end;">
										<a href="./add" class="btn btn-primary mb-2">글쓰기</a>
									</div>
								</sec:authorize>
							</div>	
                        </div>
                    </div>
                    
                   <!-- contents 영역 끝 -->
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