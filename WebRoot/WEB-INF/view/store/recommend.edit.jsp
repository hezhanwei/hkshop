<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../wrapper.prefix.jsp"/>

<link href="${STATIC_URL}/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
  
<section class="hbox stretch">
    <aside class="aside-md bg-white b-r">
        <section class="vbox">
            <header class="b-b header">
                <p class="h4"> <c:choose> <c:when test="${storeRecommend!=null}">编辑</c:when><c:otherwise>添加</c:otherwise></c:choose></p>
            </header>
                
            <section class="scrollable wrapper w-f">
                <form class="form-horizontal" id="edit_form" action=<c:choose> <c:when test="${storeRecommend!=null}">"${BASE_URL}/storeRecommend/edit"</c:when><c:otherwise>"${BASE_URL}/storeRecommend/add"</c:otherwise></c:choose> method="post">

                    <div class="form-group">
                        <label for="storeName" class="col-sm-3 control-label"><font class="red">* </font>店铺名称</label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="storeName" name="storeName" value="${storeRecommend.storeName}" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入" />
                             </div>
                      </div>

                    <div class="form-group">
                        <label for="recommendSort" class="col-sm-3 control-label"><font class="red">* </font>排序</label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" id="recommendSort" name="recommendSort" value="${storeRecommend.recommendSort}" onkeydown="if(event.keyCode==13)return false;" placeholder="数值越小越靠前" />
                             </div>
                      </div>


			    	<div class="form-group">
						<label class="col-sm-3 control-label"><font class="red">* </font>选择状态</label>
						<div class="col-sm-8">
						    <select class="input-sm form-control inline col-def-3" id="recommendStatus" name="recommendStatus">
				                <option value="">请选择</option>
				                <option value="00">保存</option>
				                <option value="11">发布</option>
				                <option value="22">取消</option>
				            </select>
						</div>
					</div><div class="line line-dashed line-lg pull-in"></div>
					
                    <input type="hidden" name="recommendId" value="${storeRecommend.recommendId}" />

                </form>
            </section>
        
            <footer class="footer b-t bg-white-only">
                <div class="m-t-sm">
                    <button type="button" data_submit_type="submit_save_back" class="btn btn-s-md btn-primary btn-sm input-submit">保存</button>
                    <c:if test="${storeRecommend==null}"><button type="button" data_submit_type="submit_save_continue" class="btn btn-s-md btn-primary btn-sm input-submit">保存并继续添加</button></c:if>
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
<script src="${STATIC_URL}/modules/store/js/recommend.edit.js" type="text/javascript"></script>
<script>
	$(function(){
		var status = '${storeRecommend.recommendStatus}';
		 $("#recommendStatus").find("option").each(function(){
			 if($(this).val() === status){
			      $(this).attr('selected', 'selected');
			   }
		 })
	})
</script>
<!-- / modal - 编辑-->