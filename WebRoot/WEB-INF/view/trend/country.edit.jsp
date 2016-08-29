<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../wrapper.prefix.jsp"/>

<link href="${STATIC_URL}/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
  
<section class="hbox stretch">
    <aside class="aside-md bg-white b-r">
        <section class="vbox">
            <header class="b-b header">
                <p class="h4"> <c:choose> <c:when test="${trendCountry!=null}">编辑</c:when><c:otherwise>添加</c:otherwise></c:choose></p>
            </header>
                
            <section class="scrollable wrapper w-f">
                <form enctype="multipart/form-data" class="form-horizontal" id="edit_form" action=<c:choose> <c:when test="${trendCountry!=null}">"${BASE_URL}/trendCountry/edit"</c:when><c:otherwise>"${BASE_URL}/trendCountry/add"</c:otherwise></c:choose> method="post">
                    <div class="form-group">
                        <label for="countryName" class="col-sm-3 control-label">国家名称</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="countryName" name="countryName" value="${trendCountry.countryName}" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入" />
                             </div>
                      </div>

                    <div class="form-group">
                        <label for="countrySort" class="col-sm-3 control-label"><font class="red">* </font>排序</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="countrySort" name="countrySort" value="${trendCountry.countrySort}" onkeydown="if(event.keyCode==13)return false;" placeholder="数值越小越靠前" />
                             </div>
                      </div>

				    <div class="form-group">
                        <label for="filepath" class="col-sm-3 control-label"><font class="red">* </font>图片</label>
                            <div class="col-sm-7">
					  		   <c:if test="${trendCountry.filepath !=null and trendCountry.filepath!=''}">
					  		   <a href="${IMG_URL}${trendCountry.filepath}" >
							  	<img alt="" src="${IMG_URL}${trendCountry.filepath}" border="0" style="height: 80px; width: 100;">
					  		  </a>
					  		  <div style="margin-top: 20px"></div>
								<input type="file" name="businessImageFile" id="businessImageFile" value="${trendCountry.filepath}"
								 class="filestyle" data-icon="false"data-buttonText="上传图片"
								  data-classbutton="btn btn-default" 
								  data-classinput="form-control inline input-s" />
							  
								</c:if>
								<c:if test="${trendCountry.filepath ==null or trendCountry.filepath==''}">
									<input type="file" name="businessImageFile" id="businessImageFile" />
								</c:if>
                             </div>
                     </div>


                    <div class="line line-dashed line-lg pull-in"></div>

                    <input type="hidden" name="countryId" value="${trendCountry.countryId}" />

                </form>
            </section>
        
            <footer class="footer b-t bg-white-only">
                <div class="m-t-sm">
                    <button type="button" data_submit_type="submit_save_back" class="btn btn-s-md btn-primary btn-sm input-submit">保存</button>
                    <c:if test="${trendCountry==null}"><button type="button" data_submit_type="submit_save_continue" class="btn btn-s-md btn-primary btn-sm input-submit">保存并继续添加</button></c:if>
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
<script src="${STATIC_URL}/modules/trend/js/country.edit.js" type="text/javascript"></script>
<!-- / modal - 编辑-->