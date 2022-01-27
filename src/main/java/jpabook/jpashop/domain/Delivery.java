package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Delivery {
    @Id
    @GeneratedValue
    private long id;

    @Embedded
    private Address address;

    @OneToOne(mappedBy="delivery", fetch = FetchType.LAZY)
    private Order order;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}