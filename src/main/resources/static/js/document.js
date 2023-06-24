 $(document).ready(function () {

            $("#fileIdError").hide();
            $("#fileObError").hide();

            var fileIdError = false;
            var fileObError = false;

            $("#fileId").keyup(function () {
                validate_fileId();
            });
            $('input[type="file"][name="fileOb"]').change(function () {
                validate_fileOb(this.files[0]);
            });

            function validate_fileId() {
                var val = $("#fileId").val();
                var exp = /^[1-9][0-9]*$/;

                if (val == '') {
                    $("#fileIdError").show();
                    $("#fileIdError").html("Enter <b>File Id</b>");
                    $("#fileIdError").css("color", "red");
                    fileIdError = false;
                } else if (!exp.test(val)) {
                    $("#fileIdError").show();
                    $("#fileIdError").html("Not a valid <b>File Id</b>");
                    $("#fileIdError").css("color", "red");
                    fileIdError = false;
                } else {
                    $("#fileIdError").hide();
                    fileIdError = true;
                }
                return fileIdError;
            }


            function validate_fileOb(ob) {
                //here ob contains all file details
                var fname = ob.name;
                var fsize = ob.size; //bytes 1024 = 1KB
                var fext = fname.substring(fname.lastIndexOf(".") + 1);
                //alert(fname);  alert(fsize); alert(fext);
                var allowedExt = ["jpg", "jpeg", "doc", "docx", "zip"];
                if ($.inArray(fext, allowedExt) == -1) {
                    $("#fileObError").show();
                    $("#fileObError").html("Choose <b>Valid File :" + allowedExt + " only</b>");
                    $("#fileObError").css("color", "red");
                    fileObError = false;
                } else if (fsize <= 1024 * 2) { //Min size 20KB
                    $("#fileObError").show();
                    $("#fileObError").html("<b>Size less then 20KB</b>");
                    $("#fileObError").css("color", "red");
                    fileObError = false;
                } else if (fsize > 1024 *1024* 10) { //10MB
                    $("#fileObError").show();
                    $("#fileObError").html("<b>Size exceeds 10MB</b>");
                    $("#fileObError").css("color", "red");
                    fileObError = false;
                } else {
                    $("#fileObError").hide();
                    fileObError = true;
                }
                return fileObError;
            }
            
            function validate_fileObRequeired(){
                var val = $("#fileOb").val();
                if (val =='') {
                    $("#fileObError").show();
                    $("#fileObError").html("Choose <b>One File</b>");
                    $("#fileObError").css("color", "red");
                    fileObError = false;
                }
                return fileObError;
            }

            $("#documentForm").submit(function () {
                validate_fileId();
                validate_fileObRequeired();
                if (fileIdError && fileObError)
                    return true;
                else
                    return false;
            });
        });