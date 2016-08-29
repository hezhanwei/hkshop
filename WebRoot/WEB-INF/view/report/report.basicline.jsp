<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../wrapper.prefix.jsp"/>

<section class="hbox stretch">
    <table class="table table-striped m-b-none datagrid" id="container">
     </table>
</section>
            
<jsp:include page="../wrapper.suffix.jsp"/>
<script type="text/javascript">
    /* var type = 'column'; */
    var type = 'line';
    var categories = ${categories};
    var title = ${title};
    var series = ${series};
</script>

<script src="${STATIC_URL}/modules/report/js/report.basicline.js"></script>
