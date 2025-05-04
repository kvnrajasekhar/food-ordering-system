$(document).ready(function () {
  const userRole = localStorage.getItem("userRole");
  if (userRole !== "customer") {
    localStorage.setItem("errmsg", "User is not a customer.");
    window.location.href = "../layouts/404error.html";
  }
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

  const productListContainer = $("#product-list");
  const productModal = $("#productModal");
  const productModalLabel = $("#productModalLabel");
  const productModalBody = $("#productModalBody");
  let userCartItems = new Set();
  let cartRestaurantId = null;

  const restaurantId = localStorage.getItem("restaurantId");
  console.log("Restaurant ID from local storage:", restaurantId);

  const userId = localStorage.getItem("userId");
  console.log("userId:", userId);

  function fetchUserCart() {
    $.ajax({
      url: "http://localhost:8081/customer/cart",
      method: "GET",
      dataType: "json",
      data: { userId: userId },
      success: function (response) { // Changed parameter name to response
        userCartItems.clear();
        cartRestaurantId = null; // Reset to null before processing

        if (response && Array.isArray(response.details)) { // Access response.details
          const cartResponse = response.details;
          if (cartResponse.length > 0) {
            cartRestaurantId = cartResponse[0].foodItem.restaurantId; // Set from the first item
            cartResponse.forEach((item) => {
              if (item.foodItem && item.foodItem.foodId) {
                userCartItems.add(item.foodItem.foodId);
              }
            });
          }
        }
        console.log("Fetched cart. cartRestaurantId:", cartRestaurantId);
        fetchAndRenderProducts(restaurantId);
      },
      error: function (xhr, status, error) {
        console.error("Error fetching user cart:", error);
        fetchAndRenderProducts(restaurantId);
      },
    });
  }

  function fetchAndRenderProducts(restaurantId) {
    if (!restaurantId) {
      const errmsg =
        " Missing restaurant information. Please select restaurant.";
      localStorage.setItem("errmsg", errmsg);
      window.location.href = `../layouts/404error.html`;
      return;
    }
    const apiUrl = `http://localhost:8081/customer/restaurants/${restaurantId}/food-items`;

    $.ajax({
      url: apiUrl,
      method: "GET",
      dataType: "json",
      success: function (response) { 
        console.log("Successfully fetched product data:", response.details);

        if (response && Array.isArray(response.details)) { 
          renderProducts(response.details);
        } else {
          console.error("Error: Invalid product data format from server.", response);
          productListContainer.html(
            '<p class="text-center alert alert-danger">Error loading products.</p>'
          );
        }
      },
      error: function (xhr, status, error) {
        console.error("Error fetching product data:", error);
        const errmsg =
          "Status Code '" +
          xhr.status +
          "' : Error fetching product data. Please try again.";
        localStorage.setItem("errmsg", errmsg);
        // window.location.replace(`../layouts/404error.html`);
        // setTimeout(function () {
        //   messageContainer.fadeOut();
        // }, 5000);
      },
    });
  }

  function renderProducts(products) {
    productListContainer.empty();
    if (products.length === 0) {
      productListContainer.html(
        '<p class="text-center alert alert-warning">No food items available.</p>'
      );
      return;
    }
    $.each(products, function (index, product) {
      const card = $('<div class="product-card">');
      const isDisabled = userCartItems.has(product.foodId);
      const buttonText = userCartItems.has(product.foodId)
        ? "Already in Cart"
        : "Add to Cart";
      const isDisabledAttr = isDisabled ? "disabled-btn" : "";
      card.html(`
          <img src="${
            product.imageURL || "../products/img/default-food.jpg"
          }" alt="${product.foodName}">
          <div class="product-info">
            <h4>${product.foodName.replace(/_/g, " ")}</h4>
            <div class="product-meta">
              ${
                product.rating
                  ? `<span class="rating">⭐ ${product.rating}</span>`
                  : ""
              }
              ${
                product.description
                  ? `<span>${product.description.substring(0, 50)}...</span>`
                  : ""
              }
            </div>
            <p class="price">₹${
              product.price ? product.price.toFixed(2) : "N/A"
            }</p>
            <button class="add-to-cart-btn product-details-btn ${isDisabledAttr}" data-food-id="${product.foodId}" data-restaurant-id="${product.restaurantId}" ${isDisabled ? "disabled" : ""}>${buttonText}</button>
          </div>
        `);
      productListContainer.append(card);
    });

    $(".add-to-cart-btn:not(:disabled)").on("click", function () {
      const foodId = $(this).data("food-id");
      const clickedRestaurantId = $(this).data("restaurant-id");
      const productCard = $(this).closest(".product-card");
      const priceElement = productCard.find(".price");
      let price = null;
      if (priceElement.length > 0) {
        const priceText = priceElement.text().replace("₹", "");
        price = parseFloat(priceText);
        if (isNaN(price)) {
          console.error("Error: Could not determine the price of the item.");
          return;
        }
      } else {
        console.error("Error: Price information not found for this item.");
        return;
      }
      addToOrder(foodId, clickedRestaurantId, this, price, userId);
    });
  }

  function addToOrder(
    foodId,
    clickedRestaurantId,
    buttonElement,
    price,
    userId
  ) {
    if (!userId) {
      alert("Please log in to place an order.");
      return;
    }

    console.log(
      "addToOrder: cartRestaurantId:",
      cartRestaurantId,
      "clickedRestaurantId:",
      clickedRestaurantId
    );
    if (cartRestaurantId !== null && cartRestaurantId !== clickedRestaurantId) {
      if (
        confirm(
          "You already have items from another restaurant in your cart. Do you want to remove them and add this item?"
        )
      ) {
        clearCart(userId, () => {
          // Callback function to execute after the cart is cleared
          cartRestaurantId = clickedRestaurantId; //update
          addItemToOrder(foodId, buttonElement, price, userId);
          fetchUserCart(); //refresh
        });
      } else {
        return;
      }
    } else {
      cartRestaurantId = clickedRestaurantId; //update
      addItemToOrder(foodId, buttonElement, price, userId);
    }
  }

  function addItemToOrder(foodId, buttonElement, totalPrice, userId) {
    const restaurantId = localStorage.getItem("restaurantId");

    $.ajax({
      url: "http://localhost:8081/customer/order",
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify({
        user: { userId: userId },
        foodItem: { foodId: foodId },
        quantity: 1,
        totalPrice: totalPrice,
      }),
      success: function (response) { // Changed parameter name to response
        console.log("Item added to order:", response);
        alert("Item added to your order!");
        $(buttonElement)
          .prop("disabled", true)
          .addClass("disabled")
          .text("Already in Cart");
        userCartItems.add(foodId);
        fetchUserCart();
      },
      error: function (xhr, status, error) {
        console.error("Error adding item to order:", error);
        console.error("Status:", status);
        console.error("XHR Response:", xhr.responseText);
        alert("Failed to add item to order. Please try again.");
        const errmsg =
          "Status Code '" +
          xhr.status +
          "' : Error adding item to order. Please try again.";
        localStorage.setItem("errmsg", errmsg);
        window.location.href = `../layouts/404error.html`;
      },
    });
  }

  function clearCart(userId, callback) {
    $.ajax({
      url: `http://localhost:8081/customer/cart/clear?userId=${userId}`,
      method: "DELETE",
      success: function (response) { 
        console.log("Cart cleared successfully:", response);
        userCartItems.clear();
        cartRestaurantId = null;
        if (callback) {
          callback();
        }
      },
      error: function (xhr, status, error) {
        console.error("Error clearing cart:", error);
        alert("Failed to clear the cart. Please try again.");
        const errmsg = "Status Code '" + xhr.status + "' : Error clearing cart";
        localStorage.setItem("errmsg", errmsg);
        window.location.href = `../layouts/404error.html`;
      },
    });
  }

  fetchUserCart();
});


