x<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../wrapper.prefix.jsp"/>

<section class="hbox stretch">
    <aside class="aside-md bg-white b-r">
        <section class="vbox">
            <header class="b-b header">
                <p class="h4">添加设置条目</p>
            </header>
            
            <section class="scrollable wrapper panel w-f">
                <form class="form-horizontal" action="${BASE_URL}/trendadmin/setting/add" method="post" id="edit_form">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">分组(group_name)</label>
                        <div class="col-sm-4">
                            <select name="group_name" class="input-sm form-control inline">
                                <option value="">默认</option>
                            </select>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label"><font class="red">* </font>参数名(name)</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="name" name="name" value="" placeholder="请输入参数名" />
                            <span class="help-block m-b-none">参数名，比如，可填写：max_favorite</span>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group">
                        <label for="label_name" class="col-sm-2 control-label"><font class="red">* </font>标签名称(label_name)</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="label_name" name="label_name" value="" placeholder="请输入标签名称" />
                            <span class="help-block m-b-none">标签名称，比如，可填写：会员收获地址记录上限</span>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group">
                        <label class="col-sm-2 control-label"><font class="red">* </font>标签类型(val_inputtype)</label>
                        <div class="col-sm-4">
                            <select id="val_inputtype" name="val_inputtype" class="input-sm form-control inline">
                                <option value="text">文本(text)</option>
                                <option value="textarea">文本域(textarea)</option>
                                <option value="select">下拉选择(select)</option>
                                <option value="radio">单选(radio)</option>
                                <option value="checkbox">多选(checkbox)</option>
                                <option value="file">图片(file)</option>
                            </select>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div id="for_val_inputtype" class="default-hidden">
	                    <div class="form-group">
	                        <label for="val_options" class="col-sm-2 control-label"><font class="red">* </font>可选值(val_options)</label>
	                        <div class="col-sm-4">
	                            <textarea id="val_options" name="val_options" rows="2" class="form-control" placeholder="请输入可选值"></textarea>
	                            <span class="help-block m-b-none">参数可选值，多个以半角逗号分隔（形如：正序:ASC,倒序:DESC）</span>
	                        </div>
	                    </div>
	                    <div class="line line-dashed line-lg pull-in"></div>
                    </div>
                    
                    <div class="form-group">
                        <label for="val" class="col-sm-2 control-label">默认值(val_default)</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="val" name="val" value="" placeholder="请输入默认值" />
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group">
                        <label for="placeholder" class="col-sm-2 control-label">输入框内部提示文字(placeholder)</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="placeholder" name="placeholder" value="" placeholder="请输入输入框内部提示文字" />
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group">
                        <label for="note" class="col-sm-2 control-label">参数说明(note)</label>
                        <div class="col-sm-4">
                            <textarea id="note" name="note" rows="2" class="form-control" placeholder="请输入参数说明"></textarea>
                        </div>
                    </div>
                    <div class="line line-dashed line-lg pull-in"></div>
                    
                    <div class="form-group">
                        <label for="sort_order" class="col-sm-2 control-label">序号(sort_order)</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="sort_order" name="sort_order" value="" placeholder="请输入序号" />
                        </div>
                    </div>
                    
                    <input type="hidden" name="setting_id" id="setting_id" value="0" />
                </form>
            </section>
            
            <footer class="footer b-t bg-white-only">
                <div class="m-t-sm">
                    <button type="button" data_submit_type="submit_save_back" class="btn btn-s-md btn-primary btn-sm input-submit">保存</button>
                    {if ! $setting_id}<button type="button" data_submit_type="submit_save_continue" class="btn btn-s-md btn-primary btn-sm input-submit">保存并继续添加</button>{/if}
                    <button type="button" data_submit_type="submit_cancel" class="btn btn-danger btn-sm input-submit">取消</button>
                    <span id="edit_notice"></span>
                </div>
            </footer>
        </section>
    </aside>
</section>

<jsp:include page="../wrapper.suffix.jsp"/>
<script src="${STATIC_URL}/modules/trend/js/setting.edit.js"></script>