 $(document).ready(function () {
            //1.hide error span            
            $("#orderModeError").hide();
            $("#orderCodeError").hide();
            $("#orderTypeError").hide();
            $("#orderAcptError").hide();
            $("#descriptionError").hide();
            var descriptionError = false;
            //2.define flag variable           
            var orderModeError = false;
            var orderCodeError = false;
            var orderTypeError = false;
            var orderAcptError = false;
            //3.link to event && function call
            $('input[type="radio"][name="orderMode"]').change(function () {
                validate_orderMode();
            });
            $("#orderCode").keyup(function () {
                $("#orderCode").val($("#orderCode").val().toUpperCase());
                validate_orderCode();
            });

            $("#orderType").change(function () {
                validate_orderType();
            });

            $('input[type="checkbox"][name="orderAcpt"]').change(function () {
                validate_orderAcpt();
            });
            $("#description").keyup(function () {
                validate_description();
            });

            //4.fucntion definition

            //----------Mode---------//
            function validate_orderMode() {
                var len = $('input[type="radio"][name="orderMode"]:checked').length;
                if (len == 0) {
                    $("#orderModeError").html(" Choose <b>Order Mode </b>");
                    $("#orderModeError").css("color", "red");
                    $("#orderModeError").show();
                    orderModeError = false;
                } else {
                    $("#orderModeError").hide();
                    orderModeError = true;
                }
                return orderModeError;
            }
            //-------------Code------------//
            function validate_orderCode() {
                var val = $("#orderCode").val();
                var exp = /^[A-Z ]{5,25}$/;
                if (val == '') {
                    $("#orderCodeError").html("<b>Order Code Required</b>");
                    $("#orderCodeError").css("color", "red");
                    $("#orderCodeError").show();
                    orderCodeError = false;
                } else if (!exp.test(val)) {
                    $("#orderCodeError").html("<b>Order Code  only[5-25]chars</b>");
                    $("#orderCodeError").css("color", "red");
                    $("#orderCodeError").show();
                    orderCodeError = false;
                } else {
                    $("#orderCodeError").hide();
                    orderCodeError = true;
                }
                return orderCodeError;
            }

            //------------Type--------//
            function validate_orderType() {
                var val = $("#orderType").val();
                if (val == '') {
                    $("#orderTypeError").html("<b>Select Order Type </b>");
                    $("#orderTypeError").css("color", "red");
                    $("#orderTypeError").show();
                    orderTypeError = false;
                } else {
                    $("#orderTypeError").hide();
                    orderTypeError = true;
                }
                return orderTypeError;
            }

            //--------Acpt------------//
            function validate_orderAcpt() {
                var len = $('input[type="checkbox"][name="orderAcpt"]:checked').length;
                if (len == 0) {
                    $("#orderAcptError").html(" Choose <b>Order Accept </b>");
                    $("#orderAcptError").css("color", "red");
                    $("#orderAcptError").show();
                    orderAcptError = false;
                } else {
                    $("#orderAcptError").hide();
                    orderAcptError = true;
                }
                return orderAcptError;
            }
            //---description--------//
            function validate_description() {
                var val = $("#description").val();

                if (val.length < 5 || val.length > 150) {
                    $("#descriptionError").html("<b>Description Required [5-150] chars only</b>");
                    $("#descriptionError").css("color", "red");
                    $("#descriptionError").show();
                    descriptionError = false;
                } else {
                    $("#descriptionError").hide();
                    descriptionError = true;
                }
                return descriptionError;
            }


            //-----------on submit-------------//
            $("#orderMethodForm").submit(function () {
                validate_orderMode();
                validate_orderCode();
                validate_orderType();
                validate_orderAcpt();
                validate_description();
                if (orderModeError && orderCodeError && orderTypeError
                    && orderAcptError && descriptionError) {
                    return true;
                } else {
                    return false;
                }
            });
        });