<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../wrapper.prefix.jsp"/>

<link href="${STATIC_URL}/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
  
<section class="hbox stretch">
    <aside class="aside-md bg-white b-r">
        <section class="vbox">
            <header class="b-b header">
                <p class="h4"> <c:choose> <c:when test="${goodsClassify!=null}">编辑</c:when><c:otherwise>添加</c:otherwise></c:choose></p>
            </header>
                
            <section class="scrollable wrapper w-f">
                <form class="form-horizontal" id="edit_form" action=<c:choose> <c:when test="${goodsClassify!=null}">"${BASE_URL}/goodsClassify/edit"</c:when><c:otherwise>"${BASE_URL}/goodsClassify/add"</c:otherwise></c:choose> method="post">
                    <div class="form-group">
                        <label for="classifyName" class="col-sm-3 control-label"><font class="red">* </font>分类名称</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="classifyName" name="classifyName" value="${goodsClassify.classifyName}" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入" />
                             </div>
                      </div>

                    <div class="form-group">
                        <label for="classifySort" class="col-sm-3 control-label"><font class="red">* </font>排序</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="classifySort" name="classifySort" value="${goodsClassify.classifySort}" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入" />
                             </div>
                      </div>

                    <div class="line line-dashed line-lg pull-in"></div>

                    <input type="hidden" name="classifyId" value="${goodsClassify.classifyId}" />

                </form>
            </section>
        
            <footer class="footer b-t bg-white-only">
                <div class="m-t-sm">
                    <button type="button" data_submit_type="submit_save_back" class="btn btn-s-md btn-primary btn-sm input-submit">保存</button>
                    <c:if test="${goodsClassify==null}"><button type="button" data_submit_type="submit_save_continue" class="btn btn-s-md btn-primary btn-sm input-submit">保存并继续添加</button></c:if>
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
<script src="${STATIC_URL}/modules/goods/js/classify.edit.js" type="text/javascript"></script>
<!-- / modal - 编辑-->