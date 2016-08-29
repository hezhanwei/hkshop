<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="../wrapper.prefix.jsp"/>

<section class="hbox stretch">
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
	                <header class="panel-heading">首页banner表详情</header>
		            <table class="table table-striped2 m-b-none text-sm">
                        <tbody>
                        
                            <tr>  
                                <td class="col-sm-2">首页banner表ID：</td>
                                <td class="col-sm-4">${cmsBanner.bannerId}</td>
                                <td class="col-sm-2">名称：</td>
                                <td class="col-sm-4">${cmsBanner.bannerName}</td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">跳转地址：</td>
                                <td class="col-sm-4">${cmsBanner.forwardUrl}</td>
                                <td class="col-sm-2">关联trend_attachment表：</td>
                                <td class="col-sm-4">${cmsBanner.attachmentid}</td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">状态：</td>
                                <td class="col-sm-4">${cmsBanner.status}</td>
                                <td class="col-sm-2">终端类型：</td>
                                <td class="col-sm-4">${cmsBanner.terminalType}</td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">排序使用：</td>
                                <td class="col-sm-4">${cmsBanner.bannerSort}</td>
                                <td class="col-sm-2">上架时间：</td>
                                <td class="col-sm-4">
                                    <fmt:formatDate value="${cmsBanner.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">下架时间：</td>
                                <td class="col-sm-4">
                                    <fmt:formatDate value="${cmsBanner.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td class="col-sm-2"></td>
                                <td class="col-sm-4"></td>
                            </tr>
                         
                        </tbody>
                    </table>
                </section>
            </section>
        </section>
    </aside>
</section>

<jsp:include page="../wrapper.suffix.jsp"/>
<script src="${STATIC_URL}/modules/cms/js/banner.detail.js" type="text/javascript"></script>
