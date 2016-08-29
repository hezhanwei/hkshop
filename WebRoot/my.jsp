<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

<link rel="stylesheet" href="${STATIC_URL}/plugins/bootstrap/3.1.0/css/bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="${STATIC_URL}/css/animate.css" type="text/css"/>
<link rel="stylesheet" href="${STATIC_URL}/css/font-awesome.min.css" type="text/css"/>
<link rel="stylesheet" href="${STATIC_URL}/css/apps.css" type="text/css"/>
<link rel="stylesheet" href="${STATIC_URL}/plugins/fuelux/fuelux.css" type="text/css"/>
<link rel="stylesheet" href="${STATIC_URL}/css/reset.css" type="text/css"/>
<link rel="stylesheet" href="${STATIC_URL}/plugins/jquery-pnotify/pnotify.custom.min.css" type="text/css"/>
<link rel="stylesheet" href="${STATIC_URL}/css/sessionTimeout.css" type="text/css"/>

<link href="${STATIC_URL}/plugins/jquery-fileupload/6.9.7/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css" />
<link href="${STATIC_URL}/plugins/jquery-prettyphoto/3.1.5/css/prettyPhoto.css" rel="stylesheet" type="text/css" />
</head>
<body>
	
	<section class="edit-map wrapper default-hidden" id="edit_content">
	<div class="form-group">
		<div class="col-sm-12">
			<textarea name="body" id="editor1" rows="10" class="form-control fck-editor">${content.body}</textarea>
		</div>
	</div>
</section>
	
</body>

<script src="${STATIC_URL}/plugins/file-input/bootstrap-filestyle.min.js"></script>
<script src="${STATIC_URL}/plugins/ckeditor/4.4.1/ckeditor.js" type="text/javascript"></script>
<script src="${STATIC_URL}/plugins/jquery-fileupload/6.9.7/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
<script src="${STATIC_URL}/plugins/jquery-fileupload/6.9.7/js/jquery.iframe-transport.js" type="text/javascript"></script>
<script src="${STATIC_URL}/plugins/jquery-fileupload/6.9.7/js/jquery.fileupload.js" type="text/javascript"></script>
<script src="${STATIC_URL}/plugins/jquery-prettyphoto/3.1.5/js/jquery.prettyPhoto.js" type="text/javascript"></script>
<script src="${STATIC_URL}/plugins/sortable/jquery.sortable.js" type="text/javascript"></script>
</html>