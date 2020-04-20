package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table (name = "product")
public class Product {

    @Id
    @GeneratedValue

    @Column (name = "id")
    private Long id;
    @Column (name = "productname")
    private String productname;
    @Column (name = "quantity")
    private String quantity;
    @Column (name = "purchaseprice")
    private String purchaseprice;
    @Column (name = "salesprice")
    private String salesprice;
    @Column (name = "description")
    private String description;

    @ManyToOne
    @JoinColumn (name = "supplier")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn (name = "brand")
    private Brand brand;

    @ManyToOne
    @JoinColumn (name = "category")
    private Category category;


    @ManyToOne
    @JoinColumn (name = "employee")
    private Employees employees ;
    @Column (name = "dateadded")
    private LocalDate dateadded;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPurchaseprice() {
        return purchaseprice;
    }

    public void setPurchaseprice(String purchaseprice) {
        this.purchaseprice = purchaseprice;
    }

    public String getSalesprice() {
        return salesprice;
    }

    public void setSalesprice(String salesprice) {
        this.salesprice = salesprice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public LocalDate getDateadded() {
        return dateadded;
    }

    public void setDateadded(LocalDate dateadded) {
        this.dateadded = dateadded;
    }




}
