package vn.edu.eaut.lab8.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Product {

    private int id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;

    @DecimalMin(
        value = "0.01",
        message = "Giá phải lớn hơn 0"
    )
    private double price;

    @Min(
        value = 0,
        message = "Số lượng không được âm"
    )
    private int quantity;

    public Product() {
    }

    public Product(
            int id,
            String name,
            double price,
            int quantity) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}