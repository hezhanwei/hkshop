<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../wrapper.prefix.jsp"/>

<section class="hbox stretch">
    <aside class="aside-md bg-white b-r">
        <section class="vbox">
            <header class="b-b header">
                <p class="h4"><c:choose><c:when test="${group!=null}">编辑分组</c:when><c:otherwise>添加分组</c:otherwise></c:choose></p>
            </header>
            
            <section class="scrollable wrapper w-f">
                <form class="form-horizontal" id="editForm" action="${BASE_URL}/casuserGroup/add" method="post">
                    <div class="form-group">
                        <label for="groupname" class="col-sm-2 control-label"><font class="red">* </font>分组名称</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="groupName" name="groupName" value="${group.groupName}" placeholder="请输入分组名称" />
                        </div>
                    </div>
                    
                    <input type="hidden" name="userGroupId" value="${group.userGroupId}" />
                </form>
            </section>
            
            <footer class="footer b-t bg-white-only">
                <div class="m-t-sm">
                    <button type="button" id="submit_save_back" class="btn btn-s-md btn-primary btn-sm input-submit">保存</button>
                    <c:if test="${group ==null }"><button type="button" id="submit_save_continue" class="btn btn-s-md btn-primary btn-sm input-submit">保存并继续添加</button></c:if>
                    <button type="button" id="submit_cancel" class="btn btn-danger btn-sm input-submit">取消</button>
                    <span id="editNotice"></span>
                </div>
            </footer>
        </section>
    </aside>
</section>

<jsp:include page="../wrapper.suffix.jsp"/>
<script src="${STATIC_URL}/modules/cas/js/group.edit.js" type="text/javascript"></script>