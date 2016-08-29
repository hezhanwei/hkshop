<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../wrapper.prefix.jsp"/>

<link href="${STATIC_URL}/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
  
<section class="hbox stretch">
    <aside class="aside-md bg-white b-r">
        <section class="vbox">
            <header class="b-b header">
                <p class="h4"> <c:choose> <c:when test="${cmsBanner!=null}">编辑</c:when><c:otherwise>添加</c:otherwise></c:choose></p>
            </header>
                
            <section class="scrollable wrapper w-f">
                <form class="form-horizontal" id="edit_form" action=<c:choose> <c:when test="${cmsBanner!=null}">"${BASE_URL}/cmsBanner/edit"</c:when><c:otherwise>"${BASE_URL}/cmsBanner/add"</c:otherwise></c:choose> method="post">
                    <div class="form-group">
                        <label for="bannerName" class="col-sm-3 control-label"><font class="red">* </font>名称</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="bannerName" name="bannerName" value="${cmsBanner.bannerName}" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入" />
                             </div>
                      </div>

                    <div class="form-group">
                        <label for="forwardUrl" class="col-sm-3 control-label"><font class="red">* </font>跳转地址</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="forwardUrl" name="forwardUrl" value="${cmsBanner.forwardUrl}" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入" />
                             </div>
                      </div>

                    <div class="form-group">
                        <label for="attachmentid" class="col-sm-3 control-label"><font class="red">* </font>关联trend_attachment表</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="attachmentid" name="attachmentid" value="${cmsBanner.attachmentid}" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入" />
                             </div>
                      </div>

                    <div class="form-group">
                        <label for="status" class="col-sm-3 control-label"><font class="red">* </font>状态</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="status" name="status" value="${cmsBanner.status}" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入" />
                             </div>
                      </div>

                    <div class="form-group">
                        <label for="terminalType" class="col-sm-3 control-label"><font class="red">* </font>终端类型</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="terminalType" name="terminalType" value="${cmsBanner.terminalType}" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入" />
                             </div>
                      </div>

                    <div class="form-group">
                        <label for="bannerSort" class="col-sm-3 control-label"><font class="red">* </font>排序使用</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="bannerSort" name="bannerSort" value="${cmsBanner.bannerSort}" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入" />
                             </div>
                      </div>

                    <div class="form-group m-b-xs">
                        <label for="startTime" class="col-sm-3 control-label"><font class="red">* </font>上架时间</label>
                        <div class="col-sm-4">
                            <input type="text" class="input-sm input-s datetimepicker-input form-control" id="startTime" name="startTime" 
                            value="<fmt:formatDate value="${cmsBanner.startTime}" pattern="yyyy-MM-dd HH:mm"/>" placeholder="请选择上架时间时间" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>

                    <div class="form-group m-b-xs">
                        <label for="endTime" class="col-sm-3 control-label"><font class="red">* </font>下架时间</label>
                        <div class="col-sm-4">
                            <input type="text" class="input-sm input-s datetimepicker-input form-control" id="endTime" name="endTime" 
                            value="<fmt:formatDate value="${cmsBanner.endTime}" pattern="yyyy-MM-dd HH:mm"/>" placeholder="请选择下架时间时间" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>

                    <input type="hidden" name="bannerId" value="${cmsBanner.bannerId}" />

                </form>
            </section>
        
            <footer class="footer b-t bg-white-only">
                <div class="m-t-sm">
                    <button type="button" data_submit_type="submit_save_back" class="btn btn-s-md btn-primary btn-sm input-submit">保存</button>
                    <c:if test="${cmsBanner==null}"><button type="button" data_submit_type="submit_save_continue" class="btn btn-s-md btn-primary btn-sm input-submit">保存并继续添加</button></c:if>
                    <button type="button" data_submit_type="submit_cancel" class="btn btn-danger btn-sm input-submit">取消</button>
                    <span id="edit_notice"></span>
                </div>
            </footer>
        </section>
    </aside>
</section>

<jsp:include page="../wrapper.suffix.jsp"/>
<script src="${STATIC_URL}/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="${STATIC_URL}/plugins/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${STATIC_URL}/modules/cms/js/banner.edit.js" type="text/javascript"></script>
<!-- / modal - 编辑-->