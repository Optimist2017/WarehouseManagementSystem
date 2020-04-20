package model;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "customers")
public class Customer {


    @Id
    @GeneratedValue

    @Column (name = "id")
    private Long id;
    @Column (name = "name")
    private String name;
    @Column (name = "contact")
    private String contact;
    @Column (name = "address")
    private String address;
    @Column (name = "startdate")
    private LocalDate startdate;

    @Column(name = "addedby")
    private String addedby;
    @Column (name = "totalpurchase")
    private Double totalpurhase;

    @OneToMany
    private List<Sale> sales = new ArrayList<>();

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public Double getTotalpurhase() {
        return totalpurhase;
    }

    public void setTotalpurhase(Double totalpurhase) {
        this.totalpurhase = totalpurhase;
    }





}
