 $(document).ready(function () {
            //1.hide error span            
            $("#orderCodeError").hide();
            $("#shipmentCodeError").hide();
            $("#customerError").hide();
            $("#referenceNumberError").hide();
            $("#defaultStatusError").hide();
            $("#stockModeError").hide();
            $("#stockSourceError").hide();
            $("#descriptionError").hide();

            //2.define flag variable           
            var orderCodeError = false;
            var shipmentCodeError = false;
            var customerError = false;
            var referenceNumberError = false;
            var defaultStatusError = false;
            var stockModeError = false;
            var stockSourceError = false;
            var descriptionError = false;


            //link to event           


            $("#orderCode").keyup(function () {
                //5.call the function
                $("#orderCode").val($("#orderCode").val().toUpperCase());
                validate_orderCode();
            });
            
            $("#shipmentCode").change(function () {
            	//5.call the function            	
            	validate_shipmentCode();
            });
            $("#customer").change(function () {
            	//5.call the function            	
            	validate_customer();
            });
            
            $("#referenceNumber").keyup(function () {
            	//5.call the function
            
            	validate_referenceNumber();
            });
            $("#defaultStatus").keyup(function () {
            	//5.call the function
            	
            	validate_defaultStatus();
            });


            $('input[type="radio"][name="stockMode"]').change(function () {
                validate_stockMode();
            });
            $("#stockSource").change(function () {
            	validate_stockSource();
            });

            $("#description").keyup(function () {
                //5.call the function
                validate_description();
            });
            //4.define the function        

            //---------ShipmentCode-------//
            function validate_shipmentCode(){
            	var val = $("#shipmentCode").val();
                if (val == '') {
                    $("#shipmentCodeError").html("<b>Select Shipment Code </b>");
                    $("#shipmentCodeError").css("color", "red");
                    $("#shipmentCodeError").show();
                    shipmentCodeError = false;
                } else {
                    $("#shipmentCodeError").hide();
                    shipmentCodeError = true;
                }
                return shipmentCodeError;
            }
            //---------Vendor-------//
            function validate_customer(){
            	var val = $("#customer").val();
            	if (val == '') {
            		$("#customerError").html("<b>Select Customer Code </b>");
            		$("#customerError").css("color", "red");
            		$("#customerError").show();
            		customerError = false;
            	} else {
            		$("#customerError").hide();
            		customerError = true;
            	}
            	return customerError;
            }
            //-----Ref Number-------//
            function validate_referenceNumber() {
                var val = $("#referenceNumber").val();
                if (val == '') {
                    $("#referenceNumberError").html("<b>Reference Number Required</b>");
                    $("#referenceNumberError").css("color", "red");
                    $("#referenceNumberError").show();
                    referenceNumberError = false;
                } else {
                    $("#referenceNumberError").hide();
                    referenceNumberError = true;
                }
                return referenceNumberError;
            }
            //-----Status--------//
            function validate_defaultStatus() {
            	var val = $("#defaultStatus").val();
            	if (val == '') {
            		$("#defaultStatusError").html("<b>Default StatusE Required</b>");
            		$("#defaultStatusError").css("color", "red");
            		$("#defaultStatusError").show();
            		defaultStatusError = false;
            	} else {
            		$("#defaultStatusError").hide();
            		defaultStatusError = true;
            	}
            	return defaultStatusError;
            }
            //-----Code--------//
            function validate_orderCode() {
                var val = $("#orderCode").val();
                var exp = /^[A-Z.]{5,25}$/;
                if (val == '') {
                    $("#orderCodeError").html("<b>Sale Order Code Required</b>");
                    $("#orderCodeError").css("color", "red");
                    $("#orderCodeError").show();
                    orderCodeError = false;
                } else if (!exp.test(val)) {
                    $("#orderCodeError").html("<b>Sale Order Code UpperCase only[3-8]chars</b>");
                    $("#orderCodeError").css("color", "red");
                    $("#orderCodeError").show();
                    orderCodeError = false;
                } else {
                	//AJAX START
                	$.ajax({
                		url : 'validatecode',
                		data: {"code":val},
                		success:function(resTxt) {
                			if(resTxt!=''){
                                 $("#orderCodeError").show();
                				 $("#orderCodeError").html(resTxt);
                                 $("#orderCodeError").css("color", "red");
                                 orderCodeError = false;
                			}else{
                				$("#orderCodeError").hide();
                				orderCodeError = true;
                			}
                		}
                		
                	});              	
                	
                    //AJAX END
                }//else end
                return orderCodeError;
            }
            


            //-----Mode--------//
            function validate_stockMode() {
                var len = $('input[type="radio"][name="stockMode"]:checked').length;
                if (len == 0) {
                    $("#stockModeError").html("<b>Stock Mode Required</b>");
                    $("#stockModeError").css("color", "red");
                    $("#stockModeError").show();
                    stockModeError = false;
                } else {
                    $("#stockModeError").hide();
                    stockModeError = true;
                }
                return stockModeError;
            }
            ///----source----------//
            function validate_stockSource() {
            	var val = $("#stockSource").val();
            	if (val == '') {
            		$("#stockSourceError").html("<b>Stock Source Required</b>");
            		$("#stockSourceError").css("color", "red");
            		$("#stockSourceError").show();
            		stockSourceError = false;
            	} else {
            		$("#stockSourceError").hide();
            		stockSourceError = true;
            	}
            	return stockSourceError;
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
            $("#saleOrderForm").submit(function () {
                validate_orderCode();
                validate_shipmentCode();
                validate_customer();
                validate_referenceNumber();
                validate_defaultStatus();
                validate_stockMode();
                validate_stockSource();
                validate_description();
                if (orderCodeError
                		&& stockModeError
                		&&shipmentCodeError
                		&&customerError
                		&& stockSourceError
                		&& referenceNumberError
                		&& defaultStatusError
                		&& descriptionError) {
                    return true;
                } else {
                    return false;
                }
            });
        });