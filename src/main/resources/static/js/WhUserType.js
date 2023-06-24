        $(document).ready(function () {
            //1.hide span error
            $("#userTypeError").hide();
            $("#userCodeError").hide();
            $("#userEmailError").hide();
            $("#userContactError").hide();
            $("#userIdTypeError").hide();
            $("#userIdTypeError").hide();
            $("#idNumberError").hide();

            //2.define flag 
            var userTypeError = false;
            var userCodeError = false;
            var userEmailError = false;
            var userContactError = false;
            var userIdTypeError = false;
            var userIdTypeError=false;
            var idNumberError=false;
            
            //3/5 link event to inputs
            $('input[type="radio"][name="userType"]').change(function () {
                validate_userType();
                autoFillUserFor();
            });

            $("#userCode").keyup(function () {
                $("#userCode").val($("#userCode").val().toUpperCase());
                validate_userCode();
            });

            $("#userEmail").keyup(function () {
                validate_userEmail();
            });
            $("#userContact").keyup(function () {
                validate_userContact();
            });

            $("#userIdType").change(function () {
                validate_userIdType();
            });
            
            $("#userIdType").change(function(){
            	enableOthers();
            });
            
            $("#idNumber").keyup(function(){
            	validate_idNumber();
            });
            //4.define function
            //----------User Type---------//
            function validate_userType() {
                var len = $('input[type="radio"][name="userType"]:checked').length;
                if (len == 0) {
                    $("#userTypeError").html(" Choose <b>user Type </b>");
                    $("#userTypeError").css("color", "red");
                    $("#userTypeError").show();
                    userTypeError = false;
                } else {
                    $("#userTypeError").hide();
                    userTypeError = true;
                }
                return userTypeError;
            }
            
            //-------------Auto Fill---//
            function  autoFillUserFor(){
            	var val=$('input[type="radio"][name="userType"]:checked').val();
            	if(val=='Vendor'){
            		$("#userFor").val('Purchase');
            	}else if(val=='Customer'){
            		$("#userFor").val('Sale');
            	}
            }
            
            
            //----User Code---------//
            function validate_userCode() {
                var val = $("#userCode").val();
                var exp = /^[A-Z ]{5,25}$/;
                if (val == '') {
                    $("#userCodeError").html("<b>User Code Required</b>");
                    $("#userCodeError").css("color", "red");
                    $("#userCodeError").show();
                    userCodeError = false;
                } else if (!exp.test(val)) {
                    $("#userCodeError").html("<b>User Code  only[5-25]chars</b>");
                    $("#userCodeError").css("color", "red");
                    $("#userCodeError").show();
                    userCodeError = false;
                } else {//AJAX START
                	$.ajax({
                		url : 'validatecode',
                		data: {"code":val},
                		success:function(resTxt) {
                			if(resTxt!=''){
                                 $("#userCodeError").show();
                				 $("#userCodeError").html(resTxt);
                                 $("#userCodeError").css("color", "red");
                                 userCodeError = false;
                			}else{
                				$("#userCodeError").hide();
                				userCodeError = true;
                			}
                		}
                		
                	});              	
                	
                    //AJAX END
                }//else end
                return userCodeError;
            }

            //---------User Email---------//
            function validate_userEmail() {
                var val = $("#userEmail").val();
                //var exp = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,5}$/; 
                var exp =/^[A-Za-z0-9\.\-]+\@[a-z]+\.[a-z\.]{2,10}$/; 
                if (val == '') {
                    $("#userEmailError").html("<b>User Email Required</b>");
                    $("#userEmailError").css("color", "red");
                    $("#userEmailError").show();
                    userEmailError = false;
                } else if (!exp.test(val)) {
                    $("#userEmailError").html("<b>Invid Email</b>");
                    $("#userEmailError").css("color", "red");
                    $("#userEmailError").show();
                    userEmailError = false;
                } else {
                	$.ajax({
        				url: 'mailcheck',
        				data: {"mail":val},
        				success:function(resTxt) {
        					if(resTxt!=''){
        						$("#userEmailError").html(resTxt);
        	        			$("#userEmailError").css("color","red");
        	        			$("#userEmailError").show();
        	        			userEmailError = false;
        					}else{
        						$("#userEmailError").hide();
        						userEmailError = true;
        					}
        				}
        			});               }
                return userEmailError;
            }

            //-----------User Contact -------------//            

            function validate_userContact() {
                var val = $("#userContact").val();

                if (val.length < 5 || val.length > 150) {
                    $("#userContactError").html("<b>Description Required [5-150] chars only</b>");
                    $("#userContactError").css("color", "red");
                    $("#userContactError").show();
                    userContactError = false;
                } else {
                    $("#userContactError").hide();
                    userContactError = true;
                }
                return userContactError;
            }
            //------User Id Type---------//

            function validate_userIdType() {
                var val = $("#userIdType").val();
                if (val == '') {
                    $("#userIdTypeError").html("<b>Select User Id Type </b>");
                    $("#userIdTypeError").css("color", "red");
                    $("#userIdTypeError").show();
                    userIdTypeError = false;
                } else {
                    $("#userIdTypeError").hide();
                    userIdTypeError = true;
                }
                return userIdTypeError;
            }
            
            //--------Enable Other-------//
            function enableOthers(){
            	var val=$("#userIdType").val();
            	if(val=='OTHER'){
            		$("#ifother").removeAttr("readOnly");
            	}else{
            		$("#ifother").attr("readOnly","true");
            		$("#ifother").val("");
            	}
            }
            ///-------------Id Number-------//
            function validate_idNumber(){
            	var val=$("#idNumber").val();
            	var exp=/^[0-9]{4,14}$/;
            	if(val==''){
            		$("#idNumberError").html("<b>ID Number is Required</b>");
            		$("#idNumberError").css("color","red")
            		$("#idNumberError").show();
            		idNumberError=false;
            	}else if(!exp.test(val)){
            		$("#idNumberError").html("<b>ID Number must be numeric only[0-9] only</b>");
            		$("#idNumberError").css("color","red")
            		$("#idNumberError").show();
            		idNumberError=false;

            	}else{
            		$("#idNumberError").hide();
            		idNumberError=true;            		
            	}
            }
            
            //------on submit-------//
            $("#whUserTypeForm").submit(function () {
                validate_userType();
                validate_userCode();
                validate_userContact();
                validate_userIdType()
                validate_userEmail();
                validate_idNumber();
                if (userTypeError && userCodeError && userEmailError &&
                    userContactError && userIdTypeError &&idNumberError)
                    return true;
                else return false;
            });
        });
