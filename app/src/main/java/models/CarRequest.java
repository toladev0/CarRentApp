package models;

public class CarRequest {
    private String name;
    private String[] category;
    private int price;
    private String description;
    private String[] features;
    private String images;

    public String getName() {
        return name;
    }

    public String[] getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String[] getFeatures() {
        return features;
    }

    public String getImages() {
        return images;
    }

    public CarRequest(String name, String[] category, int price, String description, String[] features, String images){
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.features = features;
        this.images = images;
    }
}
