package vn.edu.eaut.lab8.bean;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import vn.edu.eaut.lab8.model.Product;
import vn.edu.eaut.lab8.repository.ProductRepository;

@Named("productBean")
@SessionScoped
public class ProductBean implements Serializable {

    private Product product = new Product();

    private final ProductRepository repo =
            new ProductRepository();

    public String save() {

        repo.add(product);

        FacesContext.getCurrentInstance()
                .addMessage(
                    null,
                    new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Thành công",
                        "Đã lưu sản phẩm"
                    )
                );

        product = new Product();

        return "product-form?faces-redirect=true";
    }

    public void delete(int id) {

        repo.delete(id);

        FacesContext.getCurrentInstance()
                .addMessage(
                    null,
                    new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Thành công",
                        "Đã xóa sản phẩm"
                    )
                );
    }

    public List<Product> getDsProduct() {
        return repo.findAll();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}