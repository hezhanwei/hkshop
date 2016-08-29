<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="../wrapper.prefix.jsp"/>

<link href="${STATIC_URL}/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>

<section class="hbox stretch">
    <aside class="aside-md bg-white b-r">
        <section class="vbox">
            <header class="b-b header">
                <p class="h4"> <c:choose> <c:when test="${grab!=null}">编辑抢购活动</c:when><c:otherwise>添加抢购活动</c:otherwise></c:choose></p>
            </header>
                
            <section class="scrollable wrapper w-f">
                <form class="form-horizontal" id="edit_form" action=
                <c:choose> <c:when test="${grab!=null}">"${BASE_URL}/grouponGrab/edit"</c:when><c:otherwise>"${BASE_URL}/grouponGrab/add"</c:otherwise></c:choose> 
                method="post">
                    <div class="form-group">
                        <label for="title" class="col-sm-2 control-label"><font class="red">* </font>抢购名称</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="title" name="title" value="${grab.title}" placeholder="请输入抢购名称" maxlength="20" />
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
				    <div class="form-group m-b-xs">
                        <label for="startTime" class="col-sm-2 control-label"><font class="red">* </font>抢购开始时间</label>
                        <div class="col-sm-4">
                            <input type="text" class="input-sm input-s datetimepicker-input form-control" id="startTime" name="startTime"
                            value="<fmt:formatDate value="${grab.startTime}" pattern="yyyy-MM-dd hh:mm"/>" placeholder="请选择抢购开始时间" readonly="readonly"/>
                            
                        </div>
                        
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group m-b-xs">
                        <label for="endTime" class="col-sm-2 control-label"><font class="red">* </font>抢购结束时间</label>
                        <div class="col-sm-4">
                            <input type="text" class="input-sm input-s datetimepicker-input form-control" id="endTime" name="endTime" 
                            value="<fmt:formatDate value="${grab.endTime}" pattern="yyyy-MM-dd hh:mm"/>" placeholder="请选择抢购结束时间" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
				    
                    <div class="form-group m-b-xs">
				        <label for="inventorySum" class="col-sm-2 control-label"><font class="red">* </font>抢购总库存</label>
				        <div class="col-sm-4">
				            <input type="text" class="form-control" id="inventorySum" name="inventorySum" value="${grab.inventorySum}" placeholder="请输入抢购总库存" />
				        </div>
				    </div>
				    <div class="line line-dashed line-sm pull-in"></div>
				    
				    <div class="form-group m-b-xs">
                        <label for="inventory" class="col-sm-2 control-label"><font class="red">* </font>库存准量</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="inventory" name="inventory" value="${grab.inventory}" placeholder="请输入库存准量" />
                        </div>
                    </div>
                    <div class="line line-dashed line-sm pull-in"></div>
                    
                    <div class="form-group m-b-xs">
                        <label for="sku" class="col-sm-2 control-label"><font class="red">* </font>商品 sku</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="sku" name="sku" value="${grab.sku}" placeholder="请输入商品 sku" />
                        </div>
                    </div>
                    <div class="line line-dashed line-sm pull-in"></div>
                    
                    <div class="form-group m-b-xs">
                        <label for="goodsName" class="col-sm-2 control-label"><font class="red">* </font>商品名称</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="goodsName" name="goodsName" value="${grab.goodsName}" placeholder="请输入商品名称" />
                        </div>
                    </div>
                    <div class="line line-dashed line-sm pull-in"></div>
                    
                    <div class="form-group m-b-xs">
                        <label for="price" class="col-sm-2 control-label"><font class="red">* </font>抢购价格</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="price" name="price" value="${grab.price}" placeholder="请输入抢购价格" />
                        </div>
                    </div>
                    
                    <div class="form-group m-b-xs">
                        <label for="buynumber" class="col-sm-2 control-label">抢购数量</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="buynumber" name="buynumber" value="${grab.buynumber}" placeholder="请输入抢购数量" />
                        </div>
                    </div>
                    <div class="line line-dashed line-sm pull-in"></div>
                    
                    <div class="form-group m-b-xs">
                        <label class="col-sm-2 control-label"><font class="red">* </font>使用积分</label>
                        <div class="col-sm-4">
                            <label for="integral_enable" class="checkbox-inline p-left-0">
                                <input type="radio" name="integral" id="integral_enable" value="1" <c:if test="${grab.integral == 1}">checked</c:if> />&nbsp;可用
                            </label>
                            <label for="integral_disable" class="checkbox-inline">
                                <input type="radio" name="integral" id="integral_disable" value="0" <c:if test="${grab.integral == 0}">checked</c:if> />&nbsp;不可用
                            </label>
                        
                        </div>
                    </div>
                    <div class="line line-dashed line-sm pull-in"></div>
                    
                    <div class="form-group m-b-xs">
                        <label class="col-sm-2 control-label">抢购分类</label>
                        <c:set var="choosedArr" value="${fn:split(grab.categoryId, ',')}" />
                        <div class="col-sm-4">
                            <c:forEach items="${categorys}" var="category">
                                <label class="checkbox-inline">
	                                <input type="checkbox" name="categoryIds" id="categoryId" value="${category.categoryId}" 
	                                <c:forEach items="${choosedArr}" var="choosed">
	                                   <c:if test="${category.categoryId == choosed}"> checked</c:if>
	                                </c:forEach>/>${category.title}
                                </label>                     
                            </c:forEach>
                        </div>
                    </div>
                    <div class="line line-dashed line-sm pull-in"></div>
				    
                    
                    <input type="hidden" id="grabId"  name="grabId" value="${grab.bulkId}" />
                </form>
            </section>
            
            <footer class="footer b-t bg-white-only">
                <div class="m-t-sm">
                    <button type="button" data_submit_type="submit_save_back" class="btn btn-s-md btn-primary btn-sm input-submit">保存</button>
                    <c:if test="${grab ==null}"><button type="button" data_submit_type="submit_save_continue" class="btn btn-s-md btn-primary btn-sm input-submit">保存并继续添加</button></c:if>
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
<script src="${STATIC_URL}/modules/groupon/js/grab.edit.js" type="text/javascript"></script>