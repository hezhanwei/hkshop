<%@ page contentType="text/html;charset=UTF-8" %>

<div class="modal-dialog" id="modal_content">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="modal_title">反馈详情</h4>
        </div>
        <div class="modal-body">
            <form class="form-horizontal" action="/gearadmin/verify/edit" method="post" id="edit_form">
                <div class="form-group">
                    <label class="col-sm-3 control-label">反馈用户：</label>
                    <div class="col-sm-7">
                        <p class="form-control-static">${feedback.realname}</p>
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-sm-3 control-label">反馈内容：</label>
                    <div class="col-sm-7">
	                    <textarea rows="5" class="form-control" disabled="disabled" style="resize: none;">${feedback.body}
	                    </textarea>
	                </div>
                </div>
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" id="submit_cancel" class="btn btn-danger btn-sm input-submit" data-dismiss="modal">取消</button>
        </div>
    </div>
</div>