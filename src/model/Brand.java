package model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "brand")
public class Brand {

    @Id
    @GeneratedValue

    @Column (name = "id")
    private Long id ;

    @Column (name = "name")
    private  String name;

    @ManyToOne
    @JoinColumn (name = "supplier")
    private  Supplier supplier;
    @Column (name = "description")
    private  String description;
    @Column (name = "addedby")
    private  String addedby;
    @Column (name = "datecreated")
    private LocalDate datecreated;

    @OneToMany
    private List<Product> productList= new ArrayList<>();

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String adddedby) {
        this.addedby = adddedby;
    }

    public LocalDate getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(LocalDate datecreated) {
        this.datecreated = datecreated;
    }






}
