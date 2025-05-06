$(document).ready(function () {
  const userRole = localStorage.getItem("userRole");
  if (userRole !== "customer") {
    localStorage.setItem("errmsg", "User is not a customer.");
    window.location.href = "../../layouts/404error.html";
  }
  const $itemsList = $("#items-list");
  const $filterButtons = $(".filter-button");

  fetch("../layouts/nav.html")
    .then((response) => response.text())
    .then((navbarHTML) => {
      $("#navbar-container").html(navbarHTML);

      const $logoutLink = $("#logout-link");

      if ($logoutLink.length) {
        $logoutLink.on("click", function (e) {
          e.preventDefault();
          localStorage.removeItem("userId");
          localStorage.removeItem("userRole");
          window.location.replace("../../login/login.html");
        });
      } else {
        console.warn("#logout-link not found in loaded navbar!");
      }
    })
    .catch((error) => {
      console.error("Failed to load navbar:", error);
    });

  function displayItems(filter) {
    $itemsList.empty();
    console.log("displayItems called with filter: [" + filter + "]");

    $.ajax({
      url: "http://localhost:8081/customer/food-items",
      method: "GET",
      dataType: "json",
      success: function (response) {
        console.log("Successfully fetched foodItems:", response);

        if (!response || !Array.isArray(response.details)) {
          console.error(
            "ERROR: Invalid data from server, expected response.details to be an array!",
            response
          );
          $itemsList.html(
            "<p class='text-center alert alert-danger'>Error: Invalid data from server (expected an array in details).</p>"
          );
          return;
        }

        const foodItems = response.details;

        if (foodItems.length === 0) {
          console.warn("WARNING: foodItems array is empty.");
          $itemsList.html(
            "<p class='text-center alert alert-warning'>No food items received from the server.</p>"
          );
          return;
        }

        let filteredItems =
          filter === "All"
            ? foodItems
            : foodItems.filter((item) => {
                if (!item || typeof item !== "object") {
                  console.warn(
                    "WARNING: Invalid item in foodItems array:",
                    item
                  );
                  return false; // Skip invalid items
                }
                if (item.foodType === undefined) {
                  console.warn(
                    "WARNING: item.foodType is undefined for item:",
                    item
                  );
                  return false; // Skip items without foodType
                }
                const match = item.foodType === filter;

                return match;
              });

        console.log(
          "filteredItems (length: " + filteredItems.length + "):",
          filteredItems
        );

        if (filteredItems.length === 0) {
          $itemsList.html(
            "<p class='text-center alert alert-warning'>No items found for this cuisine: [" +
              filter +
              "]</p>"
          );
          return;
        }

        $.each(filteredItems, function (index, item) {
          let imageSource =
            item.imageURL || "../../products/img/default-food.jpg";
          const $card = $("<div>").addClass("restaurant-card").html(`
            <img src="${imageSource}" alt="${item.foodName}">
            <div class="restaurant-info">
              <h4>${item.foodName.replace(/_/g, " ")}</h4>
              <div class="restaurant-meta">
                ${
                  item.rating
                    ? `<span class="rating">⭐ ${item.rating}</span>`
                    : ""
                }
                <span>${item.foodType}</span>
              </div>
              <p>${
                item.description ? item.description.substring(0, 25) + "..." : ""
              }</p>
              <a href="" class="view-menu item-order-btn" data-food-id="${item.foodId}">Order</a>
            </div>
          `);
          $itemsList.append($card);
          $card.find(".item-order-btn").on("click", function (event) {
            event.preventDefault();
            const foodId = $(this).data("food-id");
            getResId(foodId)
              .then(function (restaurantId) {
                localStorage.setItem("restaurantId", restaurantId);
                window.location.href = "../products/product.html";
              })
              .catch(function (error) {
                console.error("Error getting restaurant ID:", error);
                alert(
                  "Failed to retrieve restaurant information. Please try again."
                ); 
              });
          });
        });
      },
      error: function (xhr, status, error) {
        console.error("Error fetching food items:", xhr.status);
        const errmsg =
          "Status Code '" + xhr.status + "' : Error fetching food items!";
        localStorage.setItem("errmsg", errmsg);
        window.location.href = "../layouts/404error.html";
      },
    });
  }

  function getResId(foodId) {
    return new Promise(function (resolve, reject) {
      $.ajax({
        url: `http://localhost:8081/customer/food-items/${foodId}/restaurant`, 
        method: "GET",
        dataType: "json",
        success: function (response) {
          if (response && response.details) {
            resolve(response.details); 
          } else {
            reject(new Error("Restaurant ID not found in response")); 
          }
        },
        error: function (xhr, status, error) {
          reject(
            new Error(
              "Error fetching restaurant ID: " + xhr.status + " - " + error
            )
          ); 
        },
      });
    });
  }

  $filterButtons.on("click", function () {
    $filterButtons.removeClass("active");
    $(this).addClass("active");
    const cuisine = $(this).data("cuisine");
    displayItems(cuisine);
  });

  displayItems("All");
});

document.addEventListener("DOMContentLoaded", () => {
  const menuContainer = $("#menu-items");
  const staticImageUrls = [
    "../restaurant-menu/res-images/rt1.jpg",
    "../restaurant-menu/res-images/rt2.jpg",
    "../restaurant-menu/res-images/rt3.png",
    "../restaurant-menu/res-images/rt4.png",
    "../restaurant-menu/res-images/rt5.jpg",
    "../restaurant-menu/res-images/rt6.jpg",
    "../restaurant-menu/res-images/rt7.png",
  ];

  function fetchAndRenderRestaurants() {
    $.ajax({
      url: "http://localhost:8081/customer/restaurants",
      method: "GET",
      dataType: "json",
      success: function (response) {
        console.log("Successfully fetched restaurants:", response);

        if (!response || !Array.isArray(response.details)) {
          console.error(
            "ERROR: Invalid data from server.  Expected response.details to be an array.",
            response
          );
          menuContainer.html(
            "<p class='error-message alert alert-warning'>Error loading restaurants: Invalid data format from server.</p>"
          );
          return;
        }
        const restaurants = response.details;

        renderRestaurants(restaurants);
      },
      error: function (xhr, status, error) {
        console.error("Error fetching restaurant data:", error);
        menuContainer.html(
          "<p class='error-message alert alert-warning'>Error loading restaurants.</p>"
        );
      },
    });
  }

  function renderRestaurants(restaurants) {
    menuContainer.empty();
    restaurants.forEach((restaurant) => {
      const randomImage =
        staticImageUrls[Math.floor(Math.random() * staticImageUrls.length)];
      const col = $("<div>").addClass(" restaurant-card").html(`
          <div class="card menu-card">
            <div class="img-wrapper">
              <img src="${randomImage}" class="card-img-top img-fluid" alt="${restaurant.restaurantName}">
            </div>            
            <div class="card-body ">
              <h5 class="card-title restaurant-name">${restaurant.restaurantName}</h5>
              <p class="card-text restaurant-address">Address: ${restaurant.address}</p>
              <p class="card-text restaurant-cuisine">Cuisine: ${restaurant.cuisine}</p>
              <button class="btn view-menu-btn view-menu" data-restaurant-id="${restaurant.restaurantId}">View Menu</button>
            </div>
          </div>
        `);

      menuContainer.append(col);
    });

    const userId = localStorage.getItem("userId");
    console.log("User Id from local storage:", userId);

    $(".view-menu-btn").each(function () {
      $(this).on("click", function () {
        const restaurantId = $(this).data("restaurant-id");
        localStorage.setItem("restaurantId", restaurantId);
        window.location.href = `/client/customer/products/product.html`;
      });
    });
  }

  fetchAndRenderRestaurants();
});
