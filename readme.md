# Food Ordering Application - README

## 1. Introduction

This document provides a comprehensive overview of the Food Ordering Application, outlining its purpose, features, technical specifications, and setup instructions. This application enables users to browse restaurants, view menus, place orders, and manage their orders.  Restaurants can manage their menus and order statuses.

## 2. Purpose

The primary purpose of this application is to streamline the food ordering process for both customers and restaurants. It aims to provide a user-friendly platform for:

* Customers to easily find and order food from their favorite restaurants.
* Restaurants to efficiently manage their orders and menus.

## 3. Features

The application includes the following key features:

### 3.1 Customer Features

* **Restaurant Browsing:** Users can browse a list of restaurants.
* **Menu Viewing:** Users can view the menu of a selected restaurant.
* **Order Placement:** Users can add food items to their cart and place orders.
* **Order Status:** Users can view their order history and track order statuses.
* **User Authentication:** Users can create accounts, log in, and manage their profiles.
* **Address Management:** Users can manage their delivery addresses.

### 3.2 Restaurant Features

* **Menu Management:** Restaurants can add, edit, and delete food items from their menus.
* **Order Management:** Restaurants can view incoming orders, accept/reject them, and update their statuses.
* **Restaurant Profile Management:** Restaurants can manage their profile information.
* **Restaurant Authentication:** Restaurants can create accounts, log in, and manage their profiles.

### 3.3 Common Features

* **Order Status Tracking:** Both customers and restaurants can track the status of an order.
* **Database Storage:** Application data, including user information, restaurant details, menus, and orders, is stored in a database.

## 4. Technical Specifications

The application is built using the following technologies:

* **Frontend:** HTML, CSS, JavaScript
* **Backend:**  Java with Spring Boot
* **Database:** Oracle
* **API:** RESTful API
* **Server:** Tomcat

## 5. Database Schema

The database schema includes the following tables:

* **Users:** Stores customer information (user ID, name, email, password, phone number, address,role).
* **Restaurants:** Stores restaurant information (restaurant ID, name, address, cuisine).
* **FoodItems:** Stores food item details (food ID, name, description, price, restaurant ID,imageURL).
* **Orders:** Stores order information (order ID, user ID, order date, total price, quantity).
* **RestaurantOrderStatuses:** Stores the current status of order for a restaurant.

## 6. API Endpoints

The application exposes the following API endpoints:

### 6.1 User API Endpoints

* `POST /signup`: Registers a new user.
* `POST /login`: Logs in a user.
* `GET /customer/profile/${userId}`: Retrieves user details.
* `PUT /customer/profile/edit/${userId}`: Updates user details.
* `POST /customer/customer/restaurants/${restaurantId}/food-items`: Shows Food Items of a Restaurant
* `GET /customer/customer/order`: Places order for a user.
* `POST /customer/cart?userId=${userId}`: Returns the cart of user

### 6.2 Restaurant API Endpoints

* `POST /restaurant/create`: Registers a new restaurant.
* `POST /login`: Logs in a restaurant.
* `GET /restaurant/{restaurantId}/food-items`: Retrieves the menu for a restaurant.
* `POST /restaurant/addItem`: Adds a new food item to the menu.
* `PUT /restaurant/food-item/${currentEditItemId}`: Updates a food item in the menu.
* `DELETE /restaurant/food-item/${productIdToRemove}`: Deletes a food item from the menu.
* `GET /restaurant/order/{restaurantId}/statuses`: Retrieves all orders for a restaurant.
* `PUT /restaurant/order`: Updates the status of an order.

### 6.3 Order API Endpoints
* `POST /order`: Places a new order.
* `GET /order/{orderId}`: Retrieves order details.

## 7. Setup Instructions

To set up and run the application, follow these steps:

1.  **Clone the repository:** `git clone https://github.com/kvnrajasekhar/food-ordering-system.git`
2.  **Configure the database:** Create a database and update the configuration file  `application.properties`with your database credentials.
4.  **Start the server:**  `java -jar target/your-app.jar`
5.  **Access the application:** Open your browser and navigate to the specified URL (e.g., `http://localhost:3000`).

## 8.  Future Enhancements

The following enhancements are planned for future releases:

* **Payment Gateway Integration:** Integrate a secure payment gateway (e.g., Stripe, PayPal).
* **Search Functionality:** Implement search functionality for restaurants and food items.
* **Rating and Reviews:** Allow users to rate and review restaurants and food items.
* **Real-time Order Tracking:** Implement real-time order tracking for customers.
* **Push Notifications:** Implement push notifications for order status updates.
* **Recommendations:** Provide food and restaurant recommendations to users.

## 9.  Conclusion

This Food Ordering Application provides a robust and scalable solution for managing food orders.  It offers a user-friendly interface for customers and efficient order management tools for restaurants.


## Screenshots
### Home Page
![alt text](https://github.com/kvnrajasekhar/food-ordering-system/blob/main/screenshots/home%20page.png)
### Food Items
![alt text](https://github.com/kvnrajasekhar/food-ordering-system/blob/main/screenshots/food%20filters.png)
### Restaurants
![alt text](https://github.com/kvnrajasekhar/food-ordering-system/blob/main/screenshots/restaurants.png)
### Footer
![alt text](https://github.com/kvnrajasekhar/food-ordering-system/blob/main/screenshots/footer.png)
### Empty Cart
![alt text](https://github.com/kvnrajasekhar/food-ordering-system/blob/main/screenshots/empty%20cart.png)
### Items
![alt text](https://github.com/kvnrajasekhar/food-ordering-system/blob/main/screenshots/restaurant%20products.png)
### Cart
![alt text](https://github.com/kvnrajasekhar/food-ordering-system/blob/main/screenshots/cart%20items.png)
### Checkout
![alt text](https://github.com/kvnrajasekhar/food-ordering-system/blob/main/screenshots/checkout%20.png)
### Payment
![alt text](https://github.com/kvnrajasekhar/food-ordering-system/blob/main/screenshots/payment.png)
### Delivery Page -1
![alt text](https://github.com/kvnrajasekhar/food-ordering-system/blob/main/screenshots/delivery-1.png)
### Restaurant Home 
![alt text](https://github.com/kvnrajasekhar/food-ordering-system/blob/main/screenshots/restaurant-home.png)
### Restaurant Products
![alt text](https://github.com/kvnrajasekhar/food-ordering-system/blob/main/screenshots/restaurant-producrs.png)
### Restaurant Menu Management
![alt text](https://github.com/kvnrajasekhar/food-ordering-system/blob/main/screenshots/add-items.png)
### Restaurant Orders
![alt text](https://github.com/kvnrajasekhar/food-ordering-system/blob/main/screenshots/restaurant-orders.png)
### Delivery Page Out For Delivery
![alt text](https://github.com/kvnrajasekhar/food-ordering-system/blob/main/screenshots/delivery-2.png)

