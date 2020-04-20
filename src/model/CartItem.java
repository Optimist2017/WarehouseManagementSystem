package model;


import javax.persistence.*;

@Entity
@Table (name = "CartItem")
public class CartItem {


    @Id
    @GeneratedValue

    @Column (name = "Id")
    private Long id;
    @JoinColumn (name = "Product")
    @ManyToOne
    private  Product product;
    @Column (name = "Price")
    private Double price;
    @Column (name = "Quantity")
    private Integer quantity;
    @Column (name = "Total")
    private Double total ;

    @ManyToOne
    @JoinColumn (name = "sale")
    private Sale sale ;

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
