package vn.edu.eaut.lab8.repository;

import java.util.ArrayList;
import java.util.List;

import vn.edu.eaut.lab8.model.Product;

public class ProductRepository {

    private static final List<Product> data =
            new ArrayList<>();

    private static int autoId = 3;

    static {

        data.add(
            new Product(
                1,
                "Laptop",
                15000000,
                10
            )
        );

        data.add(
            new Product(
                2,
                "Chuột không dây",
                350000,
                20
            )
        );
    }

    public List<Product> findAll() {
        return data;
    }

    public void add(Product product) {

        product.setId(autoId++);

        data.add(product);
    }

    public void delete(int id) {

        data.removeIf(
            product -> product.getId() == id
        );
    }
}