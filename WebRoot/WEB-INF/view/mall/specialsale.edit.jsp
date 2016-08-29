<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../wrapper.prefix.jsp"/>

<link href="${STATIC_URL}/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
  
<section class="hbox stretch">
	
<!-- 	 <aside class="aside-md bg-white b-r" id="subNav">
        <section class="vbox">
            <header class="b-b header"><p class="h4">特卖分类</p></header>
            <section class="scrollable w-f">
                <ul class="nav nav-pills nav-stacked no-radius">
                    <li class="b-b m-t-none-reset nav-map active" id="nav_base">
                        <a href="javascript:;">
                            <i class="fa fa-chevron-right pull-right m-t-xs text-xs icon-muted"></i>
                            <i class="fa fa-fw fa-ellipsis-v"></i>
                           	 商品分类
                        </a>
                    </li>
                </ul>
            </section>
        </section>
    </aside> -->

    <aside class="aside-md bg-white b-r">
        <section class="vbox">
            <header class="b-b header">
                <p class="h4"> <c:choose> <c:when test="${goodsSpecialsale!=null}">编辑</c:when><c:otherwise>添加</c:otherwise></c:choose></p>
            </header>
                
            <section class="scrollable wrapper w-f">
                <form  enctype="multipart/form-data" class="form-horizontal" id="edit_form" action=
                <c:choose> <c:when test="${goodsSpecialsale!=null}">"${BASE_URL}/goodsSpecialsale/edit"</c:when>
                <c:otherwise>"${BASE_URL}/goodsSpecialsale/add"</c:otherwise>
                </c:choose> method="post">
                	
                   	<div class="form-group">
						<label class="col-sm-3 control-label"><font class="red">* </font>选择分类</label>
						<div class="col-sm-8">
						    <select class="input-sm form-control inline col-def-3" id="classify" name="classify">
				                <option value="">请选择</option>
				                
				                	 <c:if test="${categoryArr !=null }">
					                	 <c:forEach
	               							items="${categoryArr }"
					                	  var="category">
					                	<option value="${category.classifyId }"
										<c:if test="${goodsSpecialsale.classifyId eq category.classifyId }">
												selected="selected"
										</c:if>>${category.classifyName }</option>
					                	</c:forEach>
				                	 </c:if>
               						 <c:if test="${categoryArr ==null }">
               						 	<c:forEach
	               							items="${countryArr }"
					                	  var="category">
					                	<option value="${category.countryId }"
										<c:if test="${goodsSpecialsale.classifyId eq category.countryId }">
												selected="selected"
										</c:if>>${category.countryName }</option>
					                	</c:forEach>
               						 </c:if>
				                	
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
                    <input type="hidden" name="status" value="${classify_type }" />
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
    </aside>
</section>

<jsp:include page="../wrapper.suffix.jsp"/>
<script src="${STATIC_URL}/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="${STATIC_URL}/plugins/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${STATIC_URL}/modules/mall/js/specialsale.edit.js" type="text/javascript"></script>
<script>
	/* $(function(){
		var arr = '${categoryArr }'
		if(arr==null){
			$('#status').val('00');
		}else{
			$('#status').val('11');
		}
	}) */
</script>
<!-- / modal - 编辑-->