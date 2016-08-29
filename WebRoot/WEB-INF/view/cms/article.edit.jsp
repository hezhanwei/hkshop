<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../wrapper.prefix.jsp"/>

<link href="${STATIC_URL}/plugins/datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
<link href="${STATIC_URL}/plugins/jquery-fileupload/6.9.7/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css" />
<link href="${STATIC_URL}/plugins/jquery-prettyphoto/3.1.5/css/prettyPhoto.css" rel="stylesheet" type="text/css" />
  
<section class="hbox stretch">
    <aside class="aside-md bg-white b-r">
        <section class="vbox">
            <header class="b-b header">
                <p class="h4"> <c:choose> <c:when test="${cmsArticle!=null}">编辑</c:when><c:otherwise>添加</c:otherwise></c:choose></p>
            </header>
                
            <section class="scrollable wrapper w-f">
                <form class="form-horizontal" id="edit_form" action=<c:choose> <c:when test="${cmsArticle!=null}">"${BASE_URL}/cmsArticle/edit"</c:when><c:otherwise>"${BASE_URL}/cmsArticle/add"</c:otherwise></c:choose> method="post">
                    <div class="form-group">
                        <label for="author" class="col-sm-3 control-label"><font class="red">* </font>作者</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="author" name="author" value="${cmsArticle.author}" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入" />
                             </div>
                      </div>

                    <div class="form-group">
                        <label for="title" class="col-sm-3 control-label">标题</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="title" name="title" value="${cmsArticle.title}" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入" />
                             </div>
                      </div>

                    <div class="form-group">
                        <label for="titleCode" class="col-sm-3 control-label"><font class="red">* </font>标题标识</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="titleCode" name="titleCode" value="${cmsArticle.titleCode}" onkeydown="if(event.keyCode==13)return false;" placeholder="请输入" />
                             </div>
                      </div>
					
                    <div class="form-group">
                        <label for="content" class="col-sm-3 control-label">内容编辑：</label><br/>
                             <%-- <div class="col-sm-12">
								<textarea name="content" id="editor" rows="10" class="ckeditor">${cmsArticle.content}</textarea>
							</div> --%>
						<div class="col-sm-9">
							<textarea  id="editor1" rows="10" name="content"
								class="form-control fck-editor">${cmsArticle.content}</textarea>
						</div>
					</div>
  
                    <input type="hidden" name="articleId" value="${cmsArticle.articleId}" />

                </form>
            </section>
        
            <footer class="footer b-t bg-white-only">
                <div class="m-t-sm">
                    <button type="button" data_submit_type="submit_save_back" class="btn btn-s-md btn-primary btn-sm input-submit">保存</button>
                    <c:if test="${cmsArticle==null}"><button type="button" data_submit_type="submit_save_continue" class="btn btn-s-md btn-primary btn-sm input-submit">保存并继续添加</button></c:if>
                    <button type="button" data_submit_type="submit_cancel" class="btn btn-danger btn-sm input-submit">取消</button>
                    <span id="edit_notice"></span>
                </div>
            </footer>
        </section>
    </aside>
</section>

<jsp:include page="../wrapper.suffix.jsp"/>
<script src="${STATIC_URL}/plugins/ckeditor/4.4.1/ckeditor.js" type="text/javascript"></script>
<script src="${STATIC_URL}/plugins/datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<script src="${STATIC_URL}/plugins/datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${STATIC_URL}/modules/cms/js/article.edit.js" type="text/javascript"></script>

<script type="text/javascript">
	
	$(function(){
	
		var ckeditor1;
		var config = {
			extraPlugins : 'codesnippet',
			codeSnippet_theme : 'monokai_sublime',
			height : 500
		};
		if (typeof CKEDITOR == 'object') {
			if (CKEDITOR.instances['editor1']) {
				CKEDITOR.remove(CKEDITOR.instances['editor1']);
			}
			ckeditor1 = CKEDITOR.replace('editor1', config);
			//ckeditor1.setData(cmsArticle.content);
		}
		
		
	})
</script>
<!-- / modal - 编辑-->