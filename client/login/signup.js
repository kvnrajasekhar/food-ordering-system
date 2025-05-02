$(document).ready(function () {
  $("#dropDown").click(function () {
    $(".drop-down").toggleClass("drop-down--active");
  });
  $(".drop-down__item").click(function () {
    const value = $(this).data("value");
    const text = $(this).text().trim();
    $(".drop-down__name").text(text);
    $(".drop-down").removeClass("drop-down--active");
    $(".role-value").val(value); 
  }); // Close the dropdown if clicked outside
  $(document).on("click", function (event) {
    if (!$(event.target).closest(".drop-down-wrapper").length) {
      $(".drop-down").removeClass("drop-down--active");
    }
  });
});

$("#signupForm").submit(function (event) {
  event.preventDefault();

  // Log to console to check if the function is being called.
  console.log("signupForm submit function is called!");

  const name = $("#name").val();
  const email = $("#email").val();
  const phonenumber = $("#phone-number").val();
  const password = $("#password").val();
  const confirmPassword = $("#confirmPassword").val();
  const role = $(".role-value").val();
  const terms = $("#terms").prop("checked");

  // Basic validation
  if (!name || !email || !phonenumber|| !password || !confirmPassword || !role || !terms) {
    alert(
      "Please fill in all fields and accept the terms and conditions."
    ); 
    return;
  }

  if (password !== confirmPassword) {
    alert("Passwords do not match.");
    return;
  }

  // Create user object
  const user = {
    userName: name,
    email: email,
    phoneNumber: phonenumber,
    password: password,
    role: role,
  };

  console.log("User object:", user);

  $.ajax({
    type: "POST",
    url: "http://localhost:8081/signup",
    contentType: "application/json",
    data: JSON.stringify(user),
    dataType: "json", // Expect JSON response
    success: function (response) {
      console.log("Signup successful:", response);
      alert("Signup successful! Please login.");
      window.location.href = "login.html"; 
    },
    error: function (xhr, status, error) {
      // Handle signup error
      console.error("Signup error:", xhr.responseText);
      if (xhr.responseJSON && xhr.responseJSON.message) {
        window.location.href = "../layouts/404error.html"; 
        console.log("Signup error: " + xhr.responseJSON.message); // Display server message
      } else {
        alert("An error occurred during signup. Please try again later.");
      }
    },
  });
});
