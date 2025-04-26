$(document).ready(function() {
    const productListContainer = $("#product-list");
    const productModal = $("#productModal");
    const productModalLabel = $("#productModalLabel");
    const productModalBody = $("#productModalBody");

    // Function to get the restaurantId from the URL query parameters
    function getRestaurantIdFromUrl() {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get('restaurantId');
    }

    const restaurantId = getRestaurantIdFromUrl();

    function fetchAndRenderProducts(restaurantId) {
        if (!restaurantId) {
            productListContainer.html('<p class="text-center">No restaurant selected.</p>');
            return;
        }

        const apiUrl = `http://localhost:8081/customer/restaurants/${restaurantId}/food-items`;

        $.ajax({
            url: apiUrl,
            method: 'GET',
            dataType: 'json',
            success: function(foodItems) {
                renderProducts(foodItems);
            },
            error: function(xhr, status, error) {
                console.error("Error fetching product data:", error);
                productListContainer.html('<p class="text-center">Error loading products.</p>');
            }
        });
    }

    function renderProducts(products) {
        productListContainer.empty();
        if (products.length === 0) {
            productListContainer.html('<p class="text-center">No food items available for this restaurant.</p>');
            return;
        }
        $.each(products, function(index, product) {
            const card = $('<div class="product-card">');
            card.html(`
                <img src="${product.imageURL || '../products/img/default-food.png'}" alt="${product.foodName}">
                <div class="product-info">
                    <h4>${product.foodName.replace(/_/g, " ")}</h4>
                    <div class="product-meta">
                        ${product.rating ? `<span class="rating">⭐ ${product.rating}</span>` : ""}
                        ${product.description ? `<span>${product.description.substring(0, 50)}...</span>` : ""}
                    </div>
                    <p class="price">$${product.price ? product.price.toFixed(2) : 'N/A'}</p>
                    <a href="#" class="product-details-btn" data-product-id="${product.foodId}" data-toggle="modal" data-target="#productModal">Order</a>
                </div>
            `);
            productListContainer.append(card);
        });
    }

    // Fetch and render products based on the restaurantId from the URL
    fetchAndRenderProducts(restaurantId);

});