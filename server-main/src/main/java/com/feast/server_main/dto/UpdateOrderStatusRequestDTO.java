package com.feast.server_main.dto;

public class UpdateOrderStatusRequestDTO {

    private OrderDetails order;
    private String status;
    private RestaurantDetails restaurant;

    public static class OrderDetails {
        private Integer orderId;

        public OrderDetails() {
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }
    }

    public static class RestaurantDetails {
        private Integer restaurantId;

        public RestaurantDetails() {
        }

        public Integer getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(Integer restaurantId) {
            this.restaurantId = restaurantId;
        }
    }

    public UpdateOrderStatusRequestDTO() {
    }

    public OrderDetails getOrder() {
        return order;
    }

    public void setOrder(OrderDetails order) {
        this.order = order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RestaurantDetails getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDetails restaurant) {
        this.restaurant = restaurant;
    }
}
