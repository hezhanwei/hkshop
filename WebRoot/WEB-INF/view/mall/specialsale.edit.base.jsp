<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<link href="${STATIC_URL}/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
  
<section class="hbox stretch">
	
        <section class="vbox">
            <header class="b-b header">
                <p class="h4"> <c:choose> <c:when test="${goodsSpecialsale!=null}">编辑</c:when><c:otherwise>添加</c:otherwise></c:choose></p>
            </header>
            <section class="scrollable wrapper w-f">
                <form  enctype="multipart/form-data" class="form-horizontal" id="edit_form" 
                <c:if test="${goodsSpecialsale!=null}">action="${BASE_URL}/goodsSpecialsale/edit"</c:if>
                <c:if test="${goodsSpecialsale==null}">action="${BASE_URL}/goodsSpecialsale/add""</c:if>
                	<%-- action=<c:choose> 
	                	<c:when test="${goodsSpecialsale!=null}">"${BASE_URL}/goodsSpecialsale/edit"</c:when>
	                	<c:otherwise>"${BASE_URL}/goodsSpecialsale/add"</c:otherwise>
                	</c:choose>  --%>
                	method="post">
                	
                   	<div class="form-group">
						<label class="col-sm-3 control-label"><font class="red">* </font>选择分类</label>
						<div class="col-sm-8">
						    <select class="input-sm form-control inline col-def-3" id="classify" name="classify">
				                <option value="">请选择</option>
				                <c:forEach items="${categoryArr }" var="category">
				                	<option value="${category.classifyId }"
									<c:if test="${goodsSpecialsale.classifyId eq category.classifyId }">
											selected="selected"
									</c:if>>${category.classifyName }</option>
				                </c:forEach>
				            </select>
						</div>
					</div><div class="line line-dashed line-lg pull-in"></div>

                    <div class="form-group">
                        <label for="filepath" class="col-sm-3 control-label"><font class="red">* </font>图片</label>
                            <div class="col-sm-7">
					  		   <c:if test="${goodsSpecialsale.filepath !=null and goodsSpecialsale.filepath!=''}">
					  		   	
					  		   	<a href="${IMG_URL}${goodsSpecialsale.filepath}" >
							  		<img alt="" src="${IMG_URL}${goodsSpecialsale.filepath}" border="0" style="height: 80px; width: 100;">
					  		  	</a>
					  		  	<div style="margin-top: 20px"></div>
					  		  	
								<input type="file" name="businessImageFile" id="businessImageFile" value="${goodsSpecialsale.filepath}"
								 class="filestyle" data-icon="false"data-buttonText="上传图片"
								  data-classbutton="btn btn-default" 
								  data-classinput="form-control inline input-s" />
							  
								</c:if>
								<c:if test="${goodsSpecialsale.filepath ==null or goodsSpecialsale.filepath==''}">
									<input type="file" name="businessImageFile" id="businessImageFile" />
								</c:if>
                             </div>
                      </div>

                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group">
                        <label for="sort" class="col-sm-3 control-label"><font class="red">* </font>排序</label>
                            <div class="col-sm-2">
                                <input type="text" class="form-control" id="sort" name="sort" value="${goodsRecommend.sort}" 
                                onkeydown="if(event.keyCode==13)return false;" placeholder="数值越小越靠前" />
                             </div>
                     </div>
                    
                    <div class="line line-dashed line-lg pull-in"></div>

                    <div class="form-group m-b-xs">
                        <label for="beginTime" class="col-sm-3 control-label"><font class="red">* </font>开始时间</label>
                        <div class="col-sm-4">
                            <input type="text" class="input-sm input-s datetimepicker-input form-control" id="beginTime" name="beginTime" 
                            value="<fmt:formatDate value="${goodsSpecialsale.beginTime}" pattern="yyyy-MM-dd HH:mm"/>" placeholder="请选择开始时间时间" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>

                    <div class="form-group m-b-xs">
                        <label for="endTime" class="col-sm-3 control-label"><font class="red">* </font>结束时间</label>
                        <div class="col-sm-4">
                            <input type="text" class="input-sm input-s datetimepicker-input form-control" id="endTime" name="endTime" 
                            value="<fmt:formatDate value="${goodsSpecialsale.endTime}" pattern="yyyy-MM-dd HH:mm"/>" placeholder="请选择结束时间时间" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>

                    <input type="hidden" name="specialsaleId" value="${goodsSpecialsale.specialsaleId}" />
                    <input type="hidden" name="status" value="00" />
                    <input type="hidden" name="classifyId" id="classifyId"  />
                    <input type="hidden" name="classifyName" id="classifyName" />
                </form>
            </section>
        
            <footer class="footer b-t bg-white-only">
                <div class="m-t-sm">
                    <button type="button" data_submit_type="submit_save_back" class="btn btn-s-md btn-primary btn-sm input-submit">保存</button>
                    <c:if test="${goodsSpecialsale==null}"><button type="button" data_submit_type="submit_save_continue" class="btn btn-s-md btn-primary btn-sm input-submit">保存并继续添加</button></c:if>
                    <button type="button" data_submit_type="submit_cancel" class="btn btn-danger btn-sm input-submit">取消</button>
                    <span id="edit_notice"></span>
                </div>
            </footer>
        </section>
</section>
<script type="text/javascript">
	$(function(){
		
		$('#classifyId').val($(this).find("option:selected").val());
		$('#classifyName').val($(this).find("option:selected").text());
	})
</script>
<!-- / modal - 编辑-->