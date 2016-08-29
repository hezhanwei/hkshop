<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="../wrapper.prefix.jsp"/>

<section class="hbox stretch">
	 <aside class="aside-md bg-white b-r" id="subNav">
        <section class="vbox">
            <header class="b-b header"><p class="h4">商品分类详情</p></header>
            <section class="scrollable w-f">
                <ul class="nav nav-pills nav-stacked no-radius">
                    <li class="b-b m-t-none-reset nav-map active" id="nav_base">
                        <a href="javascript:;">
                            <i class="fa fa-chevron-right pull-right m-t-xs text-xs icon-muted"></i>
                            <i class="fa fa-fw fa-ellipsis-v"></i>
                            分类详情
                        </a>
                    </li>
                </ul>
            </section>
        </section>
    </aside>
    <aside class="bg-white">
        <section class="vbox">
            <header class="header bg-white b-b clearfix">
                <div class="m-t-sm">
                    <a href="#subNav" data-toggle="class:hide" class="btn btn-sm btn-default active btn-nav-goods" btn_nav_goods_index="0">
                        <i class="fa fa-caret-right text fa-lg"></i>
                        <i class="fa fa-caret-left text-active fa-lg"></i>
                    </a>
                        <a href="javascript:;" class="btn btn-sm btn-default" id="button_cancel"><i class="fa fa-reply"></i> 返回</a>
                </div>
            </header>
              
            <section class="scrollable wrapper">
                <section class="panel panel-default portlet-item">
	                <header class="panel-heading">特卖分类详情</header>
		            <table class="table table-striped2 m-b-none text-sm">
                        <tbody>
                        
                            <tr>  
                                <td class="col-sm-2">分类名称：</td>
                                <td class="col-sm-4">${goodsSpecialsale.classifyName}</td>
                                <td class="col-sm-2">图片路径：</td>
                                <td class="col-sm-4">${goodsSpecialsale.filepath}</td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">状态类型：</td>
                                <td class="col-sm-4">
                                	<c:if test="${goodsSpecialsale.status eq 00}">商家申请</c:if>
                                	<c:if test="${goodsSpecialsale.status eq 11}">平台添加</c:if>
                               	</td>
                                <td class="col-sm-2">创建人：</td>
                                <td class="col-sm-4">${goodsSpecialsale.createBy}</td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">开始时间：</td>
                                <td class="col-sm-4">
                                    <fmt:formatDate value="${goodsSpecialsale.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                 <td class="col-sm-2">结束时间：</td>
                                <td class="col-sm-4">
                                    <fmt:formatDate value="${goodsSpecialsale.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </section>
            </section>
        </section>
    </aside>
</section>

<jsp:include page="../wrapper.suffix.jsp"/>
<script src="${STATIC_URL}/modules/mall/js/specialsale.detail.js" type="text/javascript"></script>
