$(document).ready(function () {
  const $itemsList = $("#items-list");
  const $filterButtons = $(".filter-button");

  // Fetch and inject navbar, then attach event listener
  fetch("../layouts/nav.html")
    .then((response) => response.text())
    .then((navbarHTML) => {
      $("#navbar-container").html(navbarHTML);

      const $logoutLink = $("#logout-link"); 

      if ($logoutLink.length) {
        $logoutLink.on("click", function (e) {
          e.preventDefault();
          localStorage.removeItem("userId");
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
      success: function (foodItems) {
        console.log(
          "Successfully fetched foodItems (length: " + foodItems.length + "):",
          foodItems
        );

        if (!Array.isArray(foodItems)) {
          console.error("ERROR: foodItems is not an array!", foodItems);
          $itemsList.html(
            "<p class='text-center alert alert-danger'>Error: Invalid data from server (not an array).</p>"
          );
          return;
        }

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
          let imageSource = item.imageURL || "../../products/img/default-food.jpg";
          const $card = $("<div>").addClass("restaurant-card").html(`
                        <img src="${
                          imageSource
                        }" alt="${item.foodName}">
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
                              item.description
                                ? item.description.substring(0, 50) + "..."
                                : ""
                            }</p>
                            <a href="../restaurant-menu/resto.html" class="view-menu">Order</a>
                        </div>
                    `);
          $itemsList.append($card);
        });
      },
      error: function (xhr, status, error) {
        console.error("Error fetching food items:", xhr.status);
        const errmsg = "Status Code '"+xhr.status+ "' : Error fetching food items!"; // Include error
        localStorage.setItem("errmsg", errmsg);
        window.location.href = "../layouts/404error.html";
      },
    });
  }

  $filterButtons.on("click", function () {
    $filterButtons.removeClass("active");
    $(this).addClass("active");
    const cuisine = $(this).data("cuisine");
    displayItems(cuisine);
  });

  // Initial display, show all items
  displayItems("All");
});

document.addEventListener("DOMContentLoaded", () => {
  const menuContainer = $("#menu-items"); 
  const staticImageUrl = "../restaurant-menu/food-images/mcd.jpg";

  function fetchAndRenderRestaurants() {
    $.ajax({
      url: "http://localhost:8081/customer/restaurants",
      method: "GET", 
      dataType: "json",
      success: function (restaurants) {
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
    menuContainer.empty(); // Use jQuery's empty()
    restaurants.forEach((restaurant) => {
      const col = $("<div>").addClass("col-md-4 mb-4").html(`
                    <div class="card menu-card">
                        <img src="${staticImageUrl}" class="card-img-top" alt="${restaurant.restaurantName}">
                        <div class="card-body">
                            <h5 class="card-title restaurant-name">${restaurant.restaurantName}</h5>
                            <p class="card-text restaurant-address">Address: ${restaurant.address}</p>
                            <p class="card-text restaurant-cuisine">Cuisine: ${restaurant.cuisine}</p>
                            <button class="btn view-menu-btn view-menu" data-restaurant-id="${restaurant.restaurantId}">View Menu</button>
                        </div>
                    </div>
                `);

      menuContainer.append(col); // Use jQuery's append()
    });

    const userId = localStorage.getItem("userId"); // Get userId from local storage
    console.log("User Id from local storage:", userId);

    // Add event listeners to the "View Menu" buttons after they are rendered
    $(".view-menu-btn").each(function () {
      $(this).on("click", function () {
        const restaurantId = $(this).data("restaurant-id");
        localStorage.setItem("restaurantId", restaurantId); // Store the restaurantId in local storage
        window.location.href = `/client/products/product.html`;
      });
    });
  }

  fetchAndRenderRestaurants();
});
