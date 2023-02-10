$(function(){
	// file upload
	$('table').on('click', '#btnFileAdd', function(){
        var fileInput = document.getElementById("file_input");
        var files = fileInput.files;
        
        var template = "<tr class='file-row'>";
        template += "<th scope='row' class='th2'>파일선택</th>"
        template += "<td class='align-l'>";
        template += "<input type='file' id='file_input' name='files' class='ip sr1 st1 w4'> ";
        template += '<a id="btnFileAdd" href="#" class="btn sr2 st4">추가</a>';
        template += "<a href='#' onclick='return false;' class='btn sr2 st6 delete-file' data-seq='" + files.length+1 + "'>삭제</a>";
        template += "</td>";
        template += "</tr>";
        
        $('table tbody').append(template);
    });
	
	// file delete
	$('table').on('click', '.delete-file', function(){
    	var $fileRow = $(this).parent().parent();
    	$fileRow.remove();
    });
});