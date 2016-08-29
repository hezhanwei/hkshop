<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../wrapper.prefix.jsp"/>

<section class="hbox stretch">
    <aside class="aside-md bg-white b-r">
        <section class="vbox">
            <header class="b-b header">
                <p class="h4"> <c:choose> <c:when test="${category!=null}">编辑分类标签</c:when><c:otherwise>添加分类标签</c:otherwise></c:choose></p>
            </header>
                
            <section class="scrollable wrapper w-f">
                <form class="form-horizontal" id="edit_form" action=
                <c:choose> <c:when test="${category!=null}">"${BASE_URL}/grouponCategory/edit"</c:when><c:otherwise>"${BASE_URL}/grouponCategory/add"</c:otherwise></c:choose>
                method="post">
                    <div class="form-group">
                        <label for="title" class="col-sm-2 control-label"><font class="red">* </font>标签名称</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="title" name="title" value="${category.title}" placeholder="请输入标签名称" maxlength="20" />
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group m-b-xs">
				        <label for="parentId" class="col-sm-2 control-label">上级标签</label>
				        <div class="col-sm-4">
				            <select class="form-control" id="parentId" name="parentId">
				                <option value="0" <c:if test="${category.parentId == 0}">selected</c:if>>无</option>
				                <c:forEach items="${categorys}" var="c">
				                    <option value="${c.categoryId}"
				                    <c:if test="${category.parentId == c.categoryId}"> selected</c:if>>${c.title}</option>
				                </c:forEach>
				            </select>
				        </div>
				    </div>
				    <div class="line line-dashed line-lg pull-in"></div>
				    
				    <div class="form-group m-b-xs">
                        <label for="sortOrder" class="col-sm-2 control-label"><font class="red">* </font>排序</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="sortOrder" name="sortOrder" value="${category.sortOrder}" placeholder="请输入排序号" maxlength="20" />
                            
                        </div>
                        
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <input type="hidden" id="categoryId"  name="categoryId" value="${category.categoryId}" />
                </form>
            </section>
            
            <footer class="footer b-t bg-white-only">
                <div class="m-t-sm">
                    <button type="button" data_submit_type="submit_save_back" class="btn btn-s-md btn-primary btn-sm input-submit">保存</button>
                    <c:if test="${category ==null}"><button type="button" data_submit_type="submit_save_continue" class="btn btn-s-md btn-primary btn-sm input-submit">保存并继续添加</button></c:if>
                    <button type="button" data_submit_type="submit_cancel" class="btn btn-danger btn-sm input-submit">取消</button>
                    <span id="edit_notice"></span>
                </div>
            </footer>
        </section>
    </aside>
</section>

<jsp:include page="../wrapper.suffix.jsp"/>
<script src="${STATIC_URL}/modules/groupon/js/category.edit.js" type="text/javascript"></script>