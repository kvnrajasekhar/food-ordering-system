$(document).ready(function () {
  fetch("./layouts/nav.html")
    .then((response) => response.text())
    .then((navbarHTML) => {
      $("#navbar-container").html(navbarHTML);
      const $logoutLink = $("#logout-link");

      if ($logoutLink.length) {
        $logoutLink.on("click", function (e) {
          e.preventDefault();
          localStorage.removeItem("userId");
          localStorage.removeItem("restaurantId");
          window.location.replace("../login/login.html");
        });
      } else {
        console.warn("#logout-link not found in loaded navbar!");
      }
    })
    .catch((error) => {
      console.error("Failed to load navbar:", error);
    });

  const profileSection = $("#profileSection");
  const editProfileSection = $("#editProfileSection");
  const editProfileBtn = $("#editProfileBtn");
  const saveProfileBtn = $("#saveProfileBtn");
  const logoutBtnProfile = $("#logoutBtnProfile");
  const logoutBtnEdit = $("#logoutBtnEdit");


  const userId = localStorage.getItem("userId"); 

  if (!userId) {
    console.error("userId is missing from the URL.");
    alert("Missing user information. Please login again.");
    window.location.href = "../login/login.html";
    return;
  }

  fetchUserProfile(userId);

  // if (user) {
  //   displayProfile(user);
  //   populateEditForm(user);
  // } else {
  //   console.error("Empty data array:", user);
  //   alert("No data received from server. Please check the API response.");
  // }
  editProfileBtn.addEventListener("click", function () {
    profileSection.style.display = "none";
    editProfileSection.style.display = "block";
  });

  saveProfileBtn.addEventListener("click", function () {
    saveUserProfile(userId);
  });

  logoutBtnProfile.addEventListener("click", function () {
    e.preventDefault();
    localStorage.removeItem("userId");
    localStorage.removeItem("restaurantId");
    window.location.replace("../login/login.html");
  });

  logoutBtnEdit.addEventListener("click", function () {
    window.location.href = "../login/login.html";
  });
});

function fetchUserProfile(userId) {
  const url = `http://localhost:8081/customer/profile/${userId}`;

  $.ajax({
    url: url,
    method: "GET",
    success: function (data) {
      console.log("Fetched profile:", data);
      if (data && data.length > 0) {
        displayProfile(data[0]);
        populateEditForm(data[0]);
      } else {
        console.error("Empty data array:", data);
        alert("No data received from server. Please check the API response.");
      }
    },
    error: function (xhr, status, error) {
      console.error("Error fetching profile:", status, error);
      const errmsg = status+"! Error fetching profile " ;
      localStorage.setItem("errmsg",errmsg)
      window.location.href = `../layouts/404error.html`;
    },
  });
}
function displayProfile(data) {
  if (data && typeof data === "object") {
    $("#displayName").text(data.userName);
    $("#displayMobile").text(data.phoneNumber);
    $("#displayEmail").text(data.email);
    $("#displayAddress").text(data.address);
  } else {
    console.error("Invalid data format:", data);
    const errmsg = "Invalid data received from server.  Please check the API response.";
    localStorage.setItem("errmsg",errmsg)
    window.location.href = `../layouts/404error.html`;
  }
}

function populateEditForm(data) {
  if (data && typeof data === "object") {
    $("#editName").val(data.userName);
    $("#editMobile").val(data.phoneNumber);
    $("#editEmail").val(data.email);
    $("#editAddress").val(data.address);
  } else {
    console.error("Invalid data format:", data);
    alert("Invalid data received from server.  Please check the API response.");
    const errmsg = "Invalid data received from server.  Please check the API response.";
    localStorage.setItem("errmsg",errmsg)
    window.location.href = `../layouts/404error.html`;
  }
}

function saveUserProfile(userId) {
  const url = `http://localhost:8081/customer/profile/edit/${userId}`;
  const updatedData = {
    userName: $("#editName").val(),
    phoneNumber: $("#editMobile").val(),
    email: $("#editEmail").val(),
    address: $("#editAddress").val(),
  };
  console.log("Sending data:", updatedData);

  $.ajax({
    url: url,
    method: "PUT",
    contentType: "application/json",
    data: JSON.stringify(updatedData),
    success: function (response) {
      console.log("Profile updated successfully:", response);
      alert("Profile updated successfully!");
      editProfileSection.style.display = "none";
      profileSection.style.display = "block";
    },
    error: function (xhr, status, error) {
      console.error("Error updating profile:", xhr.responseText, status, error);
      alert("Failed to update profile. Please check the console for details.");
      const errmsg = status+ "! Error updating profile: " ;
      localStorage.setItem("errmsg",errmsg)
      window.location.href = `../layouts/404error.html`;
    },
  });
}
