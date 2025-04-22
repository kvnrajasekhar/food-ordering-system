$(document).ready(function(){
  $(".remove-btn").click(function(){
    const orderItem = $(this).closest('.order-item');

    orderItem.fadeOut(300, function() {
      $(this).remove();
    })
  })

  
  $(".quantity button").on("click", function() {
    const button = $(this);
    const input = button.siblings(".quantity-input");
    let quantity = parseInt(input.val());
    const Baseprice=219.54;

    if (button.text() === "+") {
      quantity++;
    } else if (button.text() === "-" && quantity > 1) {
      quantity--;
    }

    input.val(quantity);

    const newItemPrice = quantity * Baseprice;
    button.closest('.order-item').find('.price').text('₹' + newItemPrice.toFixed(2));

    updateTotal();
  });

  function updateTotal() {
    let total = 0;
    $(".order-item").each(function() {
      const priceText = $(this).find('.price').text().replace('₹', '');
      total += parseFloat(priceText);
    });
    
    // Assuming a fixed delivery fee for simplicity
    const deliveryFee = 50;$(document).ready(function(){
  // Remove order item on button click
  $(".remove-btn").on("click", function(){
    const orderItem = $(this).closest('.order-item');
    orderItem.fadeOut(300, function() {
      $(this).remove();
      updateTotal();
    })
  })

  // Update quantity and price on button click
  $(".quantity button").on("click", function() {
    const button = $(this);
    const input = button.siblings(".quantity-input");
    let quantity = parseInt(input.val());
    const basePrice = 219.54;

    if (button.text() === "+") {
      quantity++;
    } else if (button.text() === "-" && quantity > 1) {
      quantity--;
    }

    input.val(quantity);

    const newItemPrice = quantity * basePrice;
    button.closest('.order-item').find('.price').text('₹' + newItemPrice.toFixed(2));

    updateTotal();
  });

  // Update total price
  function updateTotal() {
    let total = 0;
    $(".order-item").each(function() {
      const priceText = $(this).find('.price').text().replace('₹', '');
      total += parseFloat(priceText);
    });
    
    // Assuming a fixed delivery fee for simplicity
    const deliveryFee = 50;
    $(".total span:last-child").text('₹' + (total + deliveryFee).toFixed(2));
    return total + deliveryFee;
  }

  // Initial total update on page load
  updateTotal();
  var amount = updateTotal();
  console.log(amount);

  // Razorpay options
  var options = {
    "key": "rzp_test_2JhJlBOrI82g0i", // Replace with your actual test key ID
    "amount": "63876",
    "currency": "INR",
    "name": "Feast+",
    "description": "Order Payment",
    "image": "./img/logo-dark.png", // Replace with your logo URL
    "handler": function (response){
        alert("Payment Successful! Payment ID: " + response.razorpay_payment_id);
        // You can redirect to a success page or perform other actions here
        window.location.href = "../delivery/delivery.html"; // Redirect on success
    },
    "prefill": {
        "name": "John Doe", // Replace with actual customer name
        "email": "john.doe@example.com", // Replace with actual customer email
        "contact": "9855432145" // Replace with actual customer phone number
    },
    "theme": {
        "color": "#F37254" // Customize the theme color
    }
  };

  var rzp1 = new Razorpay(options);

  // Open Razorpay on button click
  document.getElementById('rzp-button1').onclick = function(e){
    rzp1.open();
    var amountInPaise = Math.round(amount * 100);
    options.amount = amountInPaise;
    e.preventDefault();
  }
});
    $(".total span:last-child").text('₹' + total.toFixed(2));
    return total;
  }

  // Initial total update on page load
  updateTotal();
  var amount = updateTotal();
  console.log(amount);



  var options = {
    "key": "rzp_test_2JhJlBOrI82g0i", // Replace with your actual test key ID
    "amount":"63876",
    "currency": "INR",
    "name": "Feast+",
    "description": "Order Payment",
    "image": "./img/logo-dark.png", // Replace with your logo URL
    "handler": function (response){
        alert("Payment Successful! Payment ID: " + response.razorpay_payment_id);
        // You can redirect to a success page or perform other actions here
        window.location.href = "../delivery/delivery.html"; // Redirect on success
    },
    "prefill": {
        "name": "John Doe", // Replace with actual customer name
        "email": "john.doe@example.com", // Replace with actual customer email
        "contact": "9855432145" // Replace with actual customer phone number
    },
    "theme": {
        "color": "#F37254" // Customize the theme color
    }
};

var rzp1 = new Razorpay(options);

document.getElementById('rzp-button1').onclick = function(e){
  rzp1.open();
  var amountInPaise = Math.round(amount * 100);
  options.amount=amountInPaise;
  e.preventDefault();
}

});