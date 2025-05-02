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

const productListContainer = $("#product-list");
const productModal = $("#productModal");
const productModalLabel = $("#productModalLabel");
const productModalBody = $("#productModalBody");
const addItemBtn = $("#add-item-btn");
const editItemModal = $("#editItemModal");
const editItemModalBody = $("#editItemModalBody");
const saveEditItemBtn = $("#saveEditItem"); // Assuming a fixed restaurant ID for this page
let currentEditItemId = null;
let fooditem = []; // Initialize as an empty array


const restaurantId = localStorage.getItem("restaurantId");
const userId = localStorage.getItem("userId");

function renderProducts(products) {
  productListContainer.innerHTML = "";
  if (products.length === 0) {
    productListContainer.innerHTML =
      "<div class='text-center alert alert-info'>Your Restaurant has no products!.</div>";
    return;
  }
  products.forEach((product) => {
    console.log("Product being rendered:", product);
    const productName = product.foodName ? product.foodName : "No Name";
    const card = document.createElement("div");
    card.classList.add("product-card", "mb-3", "product-list-item");
    card.innerHTML = `
            <img src="${
              product.imageURL || ""
            }" alt="${productName}" class="img-fluid">
            <div class="product-info p-3">
                <h4>${productName}</h4>
                <div class="product-meta mb-2">
                    ${
                      product.rating
                        ? `<span class="rating">⭐ ${product.rating}</span>`
                        : ""
                    }
                    ${
                      product.foodType ? `<span>${product.foodType}</span>` : ""
                    }
                </div>
                <p class="mb-2">${
                  product.description || "No description available."
                }</p>
                <div class="product-actions d-flex justify-content-end gap-2">
                    <button class="btn btn-sm btn-success edit-item-btn" data-product-id="${
                      product.foodId
                    }" data-toggle="modal" data-target="#editItemModal">Edit</button>
                    <button class="btn btn-sm btn-danger remove-item-btn" data-product-id="${
                      product.foodId
                    }">Remove</button>
                </div>
            </div>
        `;
    productListContainer.appendChild(card);
  });
}

function fetchFoodItems() {
  const apiUrl = `http://localhost:8081/restaurant/${restaurantId}/food-items`;
  $.ajax({
    url: apiUrl,
    method: "GET",
    dataType: "json",
    success: function (data) {
      console.log("Successfully fetched food items:", data);
      fooditem = data;
      renderProducts(fooditem);
    },
    error: function (xhr, status, error) {
      console.error("Error fetching food items:", status, error);
      const errmsg = status+"! Error fetching food items. Please try again later.";
      localStorage.setItem("errmsg",errmsg)
      window.location.href = `./layouts/404error.html`;
    },
  });
}

fetchFoodItems();

productListContainer.addEventListener("click", function (event) {
  const target = event.target;
  console.log("Clicked target:", target);
  const productIdToRemove = target.dataset.productId;

  if (target.classList.contains("remove-item-btn")) {
    console.log("Deleting item with ID:", productIdToRemove); 
    if (
      productIdToRemove &&
      confirm("Are you sure you want to remove this item?")
    ) {
      $.ajax({
        url: `http://localhost:8081/restaurant/food-item/${productIdToRemove}`,
        method: "DELETE",
        success: function () {
          fooditem = fooditem.filter(
            (item) => item.foodItemId.toString() !== productIdToRemove
          );
          renderProducts(fooditem);
          console.log("Item removed successfully.");
        },
        error: function (xhr, status, error) {
          console.error("Error removing item:", status, error);
          const errmsg = status+"! Error removing item. Please try again later.";
          localStorage.setItem("errmsg",errmsg)
          window.location.href = `./layouts/404error.html`;
          alert("Failed to remove item.");
        },
      });
    }
  } else if (target.classList.contains("edit-item-btn")) {
    currentEditItemId = productIdToRemove;
    console.log("Editing item with ID:", currentEditItemId); // Debugging
    editItemModal.modal("show");
  }
});

addItemBtn.addEventListener("click", function () {
  currentEditItemId = null;
  editItemModal.modal("show");
});

editItemModal.on("show.bs.modal", function (e) {
  const modalBody = document.getElementById("editItemModalBody");
  modalBody.innerHTML = `
        <form id="editForm">
            <div class="form-group">
                <label for="edit-name">Name</label>
                <input type="text" class="form-control" id="edit-name" value="" required>
            </div>
            <div class="form-group">
                <label for="edit-foodType">Category</label>
                <input type="text" class="form-control" id="edit-foodType" value="">
            </div>
            <div class="form-group">
                <label for="edit-description">Description</label>
                <textarea class="form-control" id="edit-description"></textarea>
            </div>
            <div class="form-group">
                <label for="edit-price">Price</label>
                <input type="number" class="form-control" id="edit-price" step="0.1" value="">
            </div>
            <div class="form-group">
                <label for="edit-imageURL">Image URL</label>
                <input type="text" class="form-control" id="edit-imageURL" value="">
            </div>
            <div class="form-group">
                <label for="edit-rating">Rating</label>
                <input type="number" class="form-control" id="edit-rating" min="0" max="5" step="0.1" value="">
            </div>
            <input type="hidden" id="edit-foodId" value="">
            <button type="submit" class="btn btn-primary">Save Changes</button>
        </form>
    `;

  const editForm = document.getElementById("editForm");
  if (editForm) {
    editForm.addEventListener("submit", handleEditSubmit);
  }

  // Populate the form if we are editing
  if (currentEditItemId) {
    const itemToEdit = fooditem.find(
      (item) => item.foodId && item.foodId.toString() === currentEditItemId
    ); // Use foodId here
    if (itemToEdit) {
      document.getElementById("edit-name").value = itemToEdit.foodName || "";
      document.getElementById("edit-foodType").value =
        itemToEdit.foodType || "";
      document.getElementById("edit-description").value =
        itemToEdit.description || "";
      document.getElementById("edit-price").value = itemToEdit.price || "";
      document.getElementById("edit-imageURL").value =
        itemToEdit.imageURL || "";
      document.getElementById("edit-rating").value = itemToEdit.rating || "";
      document.getElementById("edit-foodId").value = itemToEdit.foodId || ""; // Use foodId here
    } else {
      console.error(
        "Item to edit not found in fooditem array:",
        currentEditItemId
      );
    }
  } else {
    // Clear the form for adding a new item
    document.getElementById("edit-name").value = "";
    document.getElementById("edit-foodType").value = "";
    document.getElementById("edit-description").value = "";
    document.getElementById("edit-price").value = "";
    document.getElementById("edit-imageURL").value = "";
    document.getElementById("edit-rating").value = "";
    document.getElementById("edit-foodId").value = "";
  }
});

function handleEditSubmit(event) {
  event.preventDefault();
  console.log("handleEditSubmit called, currentEditItemId:", currentEditItemId); 
  const foodId = $("#edit-foodId").value;
  const name = $("#edit-name").value;
  const foodType = $("#edit-foodType").value;
  const description = $("#edit-description").value;
  const price = parseFloat($("#edit-price").value);
  const imageURL = $("#edit-imageURL").value;
  const rating = parseFloat($("#edit-rating").value);

  const foodItemDTO = {
    foodName: name,
    foodType: foodType,
    description: description,
    price: price,
    imageURL: imageURL,
    rating: rating,
    restaurant: {
      restaurantId: restaurantId,
    },
  };

  const addItemUrl = "http://localhost:8081/restaurant/addItem";
  const updateItemUrl = `http://localhost:8081/restaurant/food-item/${currentEditItemId}`;

  if (currentEditItemId) {
    // Editing existing item
    $.ajax({
      url: updateItemUrl,
      method: "PUT",
      contentType: "application/json",
      data: JSON.stringify(foodItemDTO),
      success: function (updatedItem) {
        const indexToUpdate = fooditem.findIndex(
          (item) => item.foodId && item.foodId.toString() === currentEditItemId
        );
        if (indexToUpdate !== -1) {
          fooditem[indexToUpdate] = updatedItem;
          renderProducts(fooditem);
        }
        editItemModal.modal("hide");
        currentEditItemId = null;
      },
      error: function (xhr, status, error) {
        console.error("Error updating item:", status, error);
        const errmsg = status+"! Error updating item. Please try again later.";
        localStorage.setItem("errmsg",errmsg)
        window.location.href = `./layouts/404error.html`;
      },
    });
  } else {
    // Adding a new item
    $.ajax({
      url: addItemUrl,
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify(foodItemDTO),
      success: function (newItem) {
        fooditem.push(newItem);
        renderProducts(fooditem);
        editItemModal.modal("hide");
        currentEditItemId = null;
      },
      error: function (xhr, status, error) {
        console.error("Error adding item:", status, error);
        const errmsg = status+"! Error adding item. Please try again later.";
        localStorage.setItem("errmsg",errmsg)
        window.location.href = `./layouts/404error.html`;
      },
    });
  }
}
