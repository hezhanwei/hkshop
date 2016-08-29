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
	                <header class="panel-heading">平台文章表详情</header>
		            <table class="table table-striped2 m-b-none text-sm">
                        <tbody>
                        
                            <tr>  
                                <td class="col-sm-2">文章ID：</td>
                                <td class="col-sm-4">${cmsArticle.articleId}</td>
                                <td class="col-sm-2">作者：</td>
                                <td class="col-sm-4">${cmsArticle.author}</td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">标题：</td>
                                <td class="col-sm-4">${cmsArticle.title}</td>
                                <td class="col-sm-2">标题标识：</td>
                                <td class="col-sm-4">${cmsArticle.titleCode}</td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">内容：</td>
                                <td class="col-sm-4">${cmsArticle.content}</td>
                                <td class="col-sm-2">类型：</td>
                                <td class="col-sm-4">${cmsArticle.type}</td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">状态：</td>
                                <td class="col-sm-4">${cmsArticle.status}</td>
                                <td class="col-sm-2">创建时间：</td>
                                <td class="col-sm-4">
                                    <fmt:formatDate value="${cmsArticle.ctime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">创建人：</td>
                                <td class="col-sm-4">${cmsArticle.createBy}</td>
                                <td class="col-sm-2">修改时间：</td>
                                <td class="col-sm-4">
                                    <fmt:formatDate value="${cmsArticle.mtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                </td>
                            </tr>
                            <tr>  
                                <td class="col-sm-2">修改人：</td>
                                <td class="col-sm-4">${cmsArticle.updateBy}</td>
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
<script src="${STATIC_URL}/modules/cms/js/article.detail.js" type="text/javascript"></script>
