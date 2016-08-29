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
	                <header class="panel-heading">推荐表详情</header>
		            <table class="table table-striped2 m-b-none text-sm">
                        <tbody>
                        
                            <tr>  
                                <td class="col-sm-2">推荐ID：</td>
                                <td class="col-sm-4">${goodsRecommend.recommendId}</td>
                                <td class="col-sm-2">商品sku：</td>
                                <td class="col-sm-4">${goodsRecommend.goodsSku}</td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">商品名称：</td>
                                <td class="col-sm-4">${goodsRecommend.goodsName}</td>
                                <td class="col-sm-2">商品销售价格：</td>
                                <td class="col-sm-4">${goodsRecommend.goodsPrice}</td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">商品图片路径：</td>
                                <td class="col-sm-4">${goodsRecommend.filepath}</td>
                                <td class="col-sm-2">推荐类型：</td>
                                <td class="col-sm-4">${goodsRecommend.recommendType}</td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">创建人：</td>
                                <td class="col-sm-4">${goodsRecommend.createBy}</td>
                                <td class="col-sm-2">：</td>
                                <td class="col-sm-4">
                                    <fmt:formatDate value="${goodsRecommend.ctime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">开始时间：</td>
                                <td class="col-sm-4">
                                    <fmt:formatDate value="${goodsRecommend.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                                <td class="col-sm-2">截止时间：</td>
                                <td class="col-sm-4">
                                    <fmt:formatDate value="${goodsRecommend.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">跳转url：</td>
                                <td class="col-sm-4">${goodsRecommend.urlLink}</td>
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
<script src="${STATIC_URL}/modules/mall/js/recommend.detail.js" type="text/javascript"></script>
