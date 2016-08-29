<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="../wrapper.prefix.jsp"/>

<link href="${STATIC_URL}/plugins/datepicker/css/datepicker3.css" rel="stylesheet" type="text/css"/>

<input type="hidden" name="couponTypeVal" value="${couponTO.coupon.couponType}">
<input type="hidden" name="isExchangeVal" value="${couponTO.coupon.isExchange}">
<section class="hbox stretch">
    <aside class="aside-md bg-white b-r">
        <section class="vbox">
            <header class="b-b header">
                <p class="h4"> <c:choose> <c:when test="${couponTO!=null}">编辑优惠券</c:when><c:otherwise>添加优惠券</c:otherwise></c:choose></p>
            </header>
                
            <section class="scrollable wrapper w-f">
                <form class="form-horizontal" id="edit_form" action=
                	<c:choose> <c:when test="${couponTO!=null}">"${BASE_URL}/couponCategory/edit"</c:when><c:otherwise>"${BASE_URL}/couponCategory/add"</c:otherwise></c:choose>
                	 method="post">
                    <div class="form-group">
                        <label for="couponName" class="col-sm-2 control-label"><font class="red">* </font>优惠券名称</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="couponName" name="coupon.couponName" value="${couponTO.coupon.couponName}" placeholder="请输入优惠券名称" maxlength="20" />
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group m-b-xs">
				        <label for="total" class="col-sm-2 control-label"><font class="red">* </font>用户券生成数量</label>
				        <div class="col-sm-4">
				            <input type="text" class="form-control" id="total" name="coupon.total" value="${couponTO.coupon.total}" placeholder="请输入用户券生成数量" maxlength="20" />
				        </div>
				    </div>
				    <div class="line line-dashed line-lg pull-in"></div>
				    
				    <div class="form-group m-b-xs">
                        <label for="validStime" class="col-sm-2 control-label"><font class="red">* </font>有效开始时间</label>
                        <div class="col-sm-4">
                            <input type="text" class="input-sm input-s datepicker-input form-control" id="validStime" name="coupon.validStime"
                            value="<fmt:formatDate value="${couponTO.coupon.validStime}" pattern="yyyy-MM-dd"/>" placeholder="请选择有效开始时间" readonly="readonly"/>
                            
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group m-b-xs">
                        <label for="validEtime" class="col-sm-2 control-label"><font class="red">* </font>有效结束时间</label>
                        <div class="col-sm-4">
                            <input type="text" class="input-sm input-s datepicker-input form-control" id="validEtime" name="coupon.validEtime"
                            value="<fmt:formatDate value="${couponTO.coupon.validEtime}" pattern="yyyy-MM-dd"/>" placeholder="请选择有效结束时间" readonly="readonly"/>
                            
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group m-b-xs">
                        <label for="grantStime" class="col-sm-2 control-label"><font class="red">* </font>发放开始时间</label>
                        <div class="col-sm-4">
                            <input type="text" class="input-sm input-s datepicker-input form-control" id="grantStime" name="coupon.grantStime" 
                            value="<fmt:formatDate value="${couponTO.coupon.grantStime}" pattern="yyyy-MM-dd"/>" placeholder="请选择发放开始时间" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group m-b-xs">
                        <label for="grantEtime" class="col-sm-2 control-label"><font class="red">* </font>发放结束时间</label>
                        <div class="col-sm-4">
                            <input type="text" class="input-sm input-s datepicker-input form-control" id="grantEtime" name="coupon.grantEtime" 
                            value="<fmt:formatDate value="${couponTO.coupon.grantEtime}" pattern="yyyy-MM-dd"/>" placeholder="请选择发放结束时间" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
				    
                    <div class="form-group m-b-xs">
                        <label for="couponType" class="col-sm-2 control-label"><font class="red">* </font>优惠券类型</label>
                        <div class="col-sm-4">
                            <label class="checkbox-inline p-left-0">
                                <input type="radio" name="coupon.couponType"  value="0" <c:if test="${couponTO.coupon.couponType == 0}">checked=""</c:if> />&nbsp;全场
                            </label>
                            <label class="checkbox-inline">
                                <input type="radio" name="coupon.couponType"  value="1" <c:if test="${couponTO.coupon.couponType == 1}">checked=""</c:if> />&nbsp;满减
                            </label>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group m-b-xs full-minus">
	                    <label class="col-sm-2 control-label"><font class="red">* </font>商品分类</label>
	                    <div class="col-sm-6">
	                        <select name="relation.relationContent" id="relationContent" class="form-control">
                                <c:forEach items="${categories}" var="v">
                                    ${v.strPadding}${v.categoryName}
                                    <option value="${v.categoryId}" <c:if test='${v.categoryId==couponTO.relation.relationContent}'>selected</c:if>>${v.strPadding}${v.categoryName}</option>
                                </c:forEach>
	                        </select>
	                    </div>
	                </div>
	                <div class="line line-dashed line-lg pull-in full-minus"></div>
                    
                    <div class="form-group m-b-xs full-minus">
                        <label for="basicPrice" class="col-sm-2 control-label"><font class="red">* </font>基本要求金额</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="basicPrice" name="relation.basicPrice" value="${couponTO.relation.basicPrice}" placeholder="请输入基本要求金额" maxlength="20" />
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in full-minus"></div>
                    
                    
                    <div class="form-group m-b-xs">
                        <label for="faceValue" class="col-sm-2 control-label"><font class="red">* </font>优惠金额</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="faceValue" name="coupon.faceValue" value="${couponTO.coupon.faceValue}" placeholder="请输入优惠金额" maxlength="20" />
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    
                    
                    
                    <div class="form-group m-b-xs">
                        <label for="isExchange" class="col-sm-2 control-label"><font class="red">* </font>是否允许积分兑换</label>
                        <div class="col-sm-4">
                            <label class="checkbox-inline p-left-0">
                                <input type="radio" name="coupon.isExchange"  value="0" <c:if test="${couponTO.coupon.isExchange == 0}">checked=""</c:if> />&nbsp;允许
                            </label>
                            <label class="checkbox-inline">
                                <input type="radio" name="coupon.isExchange"  value="1" <c:if test="${couponTO.coupon.isExchange == 1}">checked=""</c:if> />&nbsp;不允许
                            </label>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group m-b-xs need-point">
                        <label for="couponPoint" class="col-sm-2 control-label">兑换所需积分</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="couponPoint" name="coupon.couponPoint" value="${couponTO.coupon.couponPoint}" placeholder="请输入兑换所需积分" maxlength="20" />
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in need-point"></div>
				    
				    <div class="form-group m-b-xs">
                        <label for="rule" class="col-sm-2 control-label"><font class="red">* </font>使用规则</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="rule" name="coupon.rule" value="${couponTO.coupon.rule}" placeholder="请输入使用规则" maxlength="20" />
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group m-b-xs">
                        <label for="body" class="col-sm-2 control-label"><font class="red">* </font>使用详情</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="body" name="coupon.body" value="${couponTO.coupon.body}" placeholder="请输入使用详情" maxlength="20" />
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
				    
                    <input type="hidden" id="couponId"  name="coupon.couponId" value="${couponTO.coupon.couponId}" />
                </form>
            </section>
            
            <footer class="footer b-t bg-white-only">
                <div class="m-t-sm">
                    <button type="button" data_submit_type="submit_save_back" class="btn btn-s-md btn-primary btn-sm input-submit">保存</button>
                    <c:if test="${coupon ==null}"><button type="button" data_submit_type="submit_save_continue" class="btn btn-s-md btn-primary btn-sm input-submit">保存并继续添加</button></c:if>
                    <button type="button" data_submit_type="submit_cancel" class="btn btn-danger btn-sm input-submit">取消</button>
                    <span id="edit_notice"></span>
                </div>
            </footer>
        </section>
    </aside>
</section>

<jsp:include page="../wrapper.suffix.jsp"/>
<script src="${STATIC_URL}/plugins/datepicker/js/bootstrap-datepicker.js"></script>
<script src="${STATIC_URL}/plugins/datepicker/js/locales/bootstrap-datepicker.zh-CN.time.js" charset="UTF-8"></script>
<script src="${STATIC_URL}/modules/coupon/js/category.edit.js" type="text/javascript"></script>