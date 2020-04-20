package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "Sale")
public class Sale {

    @Id
    @GeneratedValue

    @Column (name = "Id")
    private Long id;

    @OneToMany (mappedBy = "Sale" , cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private List<CartItem> items = new ArrayList<>();

    @Column (name = "Time")
    private Timestamp time;

    @Column (name = "Amount")
    private Double amount;

    @JoinColumn (name = "Soldby")
    private Employees soldby;

    @JoinColumn (name = "customer")
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employees getSoldby() {
        return soldby;
    }

    public void setSoldby(Employees soldby) {
        this.soldby = soldby;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
