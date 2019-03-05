<%@attribute name="id" required="true"%>
<input type="text" id="${id}" class="form-control" name="${id}"/>
<script>
    $("#${id}").datepicker({dateFormat: 'dd/mm/yy'});
</script>