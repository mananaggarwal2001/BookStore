package com.demoproject.bookStoreapplication.DTO;

public class BookDTO {
    private String title;
    private int availableQuantity;
    private double price;

    public BookDTO() {
        // empty constructor is important.
    }

    public BookDTO(String title, int availableQuantity, double price) {
        this.title = title;
        this.availableQuantity = availableQuantity;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "title='" + title + '\'' +
                ", availableQuantity=" + availableQuantity +
                ", price=" + price +
                '}';
    }
}
