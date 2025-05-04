package com.feast.server_main.dto;




public class FoodItemDTO {

    private Integer foodId;
    private String foodName;
    private String foodType; 
    private String description;
    private Float price;
    private String imageURL;
    private Double rating;

	public FoodItemDTO(Integer foodId, String foodName, String foodType, String description, Float price,
			String imageURL, Double rating) {
		super();
		this.foodId = foodId;
		this.foodName = foodName;
		this.foodType = foodType;
		this.description = description;
		this.price = price;
		this.imageURL = imageURL;
		this.rating = rating;
	}

	public Integer getFoodId() {
		return foodId;
	}

	public void setFoodId(Integer foodId) {
		this.foodId = foodId;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
    

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	} 
    
    
}
