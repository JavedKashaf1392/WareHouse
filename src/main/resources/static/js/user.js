$(document).ready(function() {
	// 1.hide span error
	$("#nameError").hide();
	$("#emailError").hide();
	$("#passwordError").hide();
	$("#cpasswordError").hide();
	$("#rolesError").hide();

	// 2.define flag
	var nameError = false;
	var emailError = false;
	var passwordError = false;
	var cpasswordError = false;
	var rolesError = false;

	// 3/5 link event to inputs
	$('input[type="checkbox"][name="roles"]').change(function() {
		validate_roles();
	});

	$("#name").keyup(function() {
		$("#name").val($("#name").val().toUpperCase());
		validate_name();
	});

	$("#password").keyup(function() {
		validate_password();
	});
	
	$("#cpassword").keyup(function() {
		validate_cpassword();
	});

	$("#email").change(function() {
		validate_email();
	});

	// 4.define function

	// ----User Name---------//
	function validate_name() {
		var val = $("#name").val();
		var exp = /^[A-Z ]{3,25}$/;
		if (val == '') {
			$("#nameError").html("<b>User Name Required</b>");
			$("#nameError").css("color", "red");
			$("#nameError").show();
			nameError = false;
		} else if (!exp.test(val)) {
			$("#nameError").html("<b>User Name  only[3-25]chars</b>");
			$("#nameError").css("color", "red");
			$("#nameError").show();
			nameError = false;
		} else {
			// register page
			var loc = 'validateName';
			var id = 0;
			if ($("#id").val() !== undefined) {
				// edit page
				loc = '../validateName';
				id = $("#id").val();
			}
			// AJAX START
			$.ajax({
				url : loc,
				data : {
					"name" : val,
					"id" : id
				},
				success : function(resTxt) {
					if (resTxt != '') {
						$("#nameError").show();
						$("#nameError").html(resTxt);
						$("#nameError").css("color", "red");
						nameError = false;
					} else {
						$("#nameError").hide();
						nameError = true;
					}
				}

			});

			// AJAX END
		}// else end
		return nameError;
	}

	// -------Password----///
	function validate_password() {
    	var val = $("#password").val();
    	if (val == '') {
    		$("#passwordError").html("<b>User Password Required</b>");
    		$("#passwordError").css("color", "red");
    		$("#passwordError").show();
    		passwordError = false;
    	} else {
    		$("#passwordError").hide();
    		passwordError = true;
    	}
    	return passwordError;
    }

	// -------CPassword----///
	function validate_cpassword() {
		
	
		var pwd = $("#password").val();
		var cpwd = $("#cpassword").val();
		if(pwd!=cpwd){
			$("#cpasswordError").html("<b>Password not matched, please check it</b>");
			$("#cpasswordError").css("color", "red");
			$("#cpasswordError").show();
			cpasswordError=false;
			passwordError=false;
		}
		else{
			$("#cpasswordError").hide();
			cpasswordError=true;
		}
		return cpasswordError;
	}
	
	
	
	// ---------User Email---------//
	function validate_email() {
		var val = $("#email").val();
		// var exp = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,5}$/;
		var exp = /^[A-Za-z0-9\.\-]+\@[a-z]+\.[a-z\.]{2,10}$/;
		if (val == '') {
			$("#emailError").html("<b>User Email Required</b>");
			$("#emailError").css("color", "red");
			$("#emailError").show();
			emailError = false;
		} else if (!exp.test(val)) {
			$("#emailError").html("<b>Invid Email</b>");
			$("#emailError").css("color", "red");
			$("#emailError").show();
			emailError = false;
		} else {
			// register page
			var loc = 'validateEmail';
			var id = 0;
			if ($("#id").val() !== undefined) {
				// edit page
				loc = '../validateEmail';
				id = $("#id").val();
			}
			// AJAX START
			$.ajax({
				url : loc,
				data : {
					"mail" : val,
					"id" : id
				},
				success : function(resTxt) {
					if (resTxt != '') {
						$("#emailError").html(resTxt);
						$("#emailError").css("color", "red");
						$("#emailError").show();
						emailError = false;
					} else {
						$("#emailError").hide();
						emailError = true;
					}
				}
			});
		}
		return emailError;
	}

	// ----------User Roles---------//
	function validate_roles() {
		var len = $('input[type="checkbox"][name="roles"]:checked').length;
		if (len == 0) {
			$("#rolesError").html(" Choose <b>User Role </b>");
			$("#rolesError").css("color", "red");
			$("#rolesError").show();
			rolesError = false;
		} else {
			$("#rolesError").hide();
			rolesError = true;
		}
		return rolesError;
	}

	// ------on submit-------//
	$("#userForm").submit(function() {
		validate_name();

		validate_password();
		validate_cpassword();
		validate_email();
		validate_roles()
		if (nameError   &&passwordError &&cpasswordError && emailError && rolesError )
			return true;
		else
			return false;
	});
});
