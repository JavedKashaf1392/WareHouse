 $(document).ready(function () {
            //1.hide error span            
            $("#shipmentModeError").hide();
            $("#shipmentCodeError").hide();
            $("#enableShipmentError").hide();
            $("#shipmentGradeError").hide();
            $("#descriptionError").hide();

            //2.define flag variable           
            var shipmentModeError = false;
            var shipmentCodeError = false;
            var enableShipmentError = false;
            var shipmentGradeError = false;
            var descriptionError = false;


            //link to event 
            $("#shipmentMode").change(function () {
                //5.call the function
                validate_shipmentMode();
            });


            $("#shipmentCode").keyup(function () {
                //5.call the function
                $("#shipmentCode").val($("#shipmentCode").val().toUpperCase());
                validate_shipmentCode();
            });

            $("#enableShipment").change(function () {
                validate_enableShipment();
            });

            $('input[type="radio"][name="shipmentGrade"]').change(function () {
                validate_shipmentGrade();
            });

            $("#description").keyup(function () {
                //5.call the function
                validate_description();
            });
            //4.define the function        

            //-----Mode--------//
            function validate_shipmentMode() {
                var val = $("#shipmentMode").val();
                if (val == '') {
                    $("#shipmentModeError").html("<b>Shipment Mode Required</b>");
                    $("#shipmentModeError").css("color", "red");
                    $("#shipmentModeError").show();
                    shipmentModeError = false;
                } else {
                    $("#shipmentModeError").hide();
                    shipmentModeError = true;
                }
                return shipmentModeError;
            }
            //-----Code--------//
            function validate_shipmentCode() {
                var val = $("#shipmentCode").val();
                var exp = /^[A-Z.]{5,25}$/;
                if (val == '') {
                    $("#shipmentCodeError").html("<b>Shipment Code Required</b>");
                    $("#shipmentCodeError").css("color", "red");
                    $("#shipmentCodeError").show();
                    shipmentCodeError = false;
                } else if (!exp.test(val)) {
                    $("#shipmentCodeError").html("<b>Shipment Code UpperCase only[3-8]chars</b>");
                    $("#shipmentCodeError").css("color", "red");
                    $("#shipmentCodeError").show();
                    shipmentCodeError = false;
                } else {
                	//register page
                	var loc = 'validatecode';
                	var id = 0;
                	if($("#id").val()!==undefined){
                		//edit page
                		loc = '../validatecode';
                		id = $("#id").val();
                	}
                	//AJAX START
                		$.ajax({
                			url :loc,
                			data:{"code":val,"id":id},
                		success:function(resTxt) {
                			if(resTxt!=''){
                                 $("#shipmentCodeError").show();
                				 $("#shipmentCodeError").html(resTxt);
                                 $("#shipmentCodeError").css("color", "red");
                                 shipmentCodeError = false;
                			}else{
                				$("#shipmentCodeError").hide();
                                shipmentCodeError = true;
                			}
                		}
                		
                	});              	
                	
                    //AJAX END
                }//else end
                return shipmentCodeError;
            }
            //-----Enable--------//
            function validate_enableShipment() {
                var val = $("#enableShipment").val();
                if (val == '') {
                    $("#enableShipmentError").html("<b>Enabel Shipment  Required</b>");
                    $("#enableShipmentError").css("color", "red");
                    $("#enableShipmentError").show();
                    enableShipmentError = false;
                } else {
                    $("#enableShipmentError").hide();
                    enableShipmentError = true;
                }
                return enableShipmentError;
            }


            //-----Grade--------//
            function validate_shipmentGrade() {
                var len = $('input[type="radio"][name="shipmentGrade"]:checked').length;
                if (len == 0) {
                    $("#shipmentGradeError").html("<b>Shipment Grade Required</b>");
                    $("#shipmentGradeError").css("color", "red");
                    $("#shipmentGradeError").show();
                    shipmentGradeError = false;
                } else {
                    $("#shipmentGradeError").hide();
                    shipmentGradeError = true;
                }
                return shipmentGradeError;
            }
            //-----Description--------//
            function validate_description() {
                var val = $("#description").val();

                if (val.length < 5 || val.length > 150) {
                    $("#descriptionError").html("<b>Description Required</b>");
                    $("#descriptionError").css("color", "red");
                    $("#descriptionError").show();
                    descriptionError = false;
                } else {
                    $("#descriptionError").hide();
                    descriptionError = true;
                }
                return descriptionError;
            }

            //-----------form submission-------------/
            $("#shipmentTypeForm").submit(function () {
                validate_shipmentMode();
                validate_shipmentCode();
                validate_enableShipment();
                validate_shipmentGrade();
                validate_description();
                if (shipmentModeError && shipmentCodeError && enableShipmentError && shipmentGradeError && descriptionError) {
                    return true;
                } else {
                    return false;
                }
            });
        });