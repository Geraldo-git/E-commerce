package br.com.zazix.commerce.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "tb_order")
public class Order {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant moment;
    private OrderStatus status;

    private User client;

    public Order(){

    }

    public Order(Long id, Instant moment) {
        this.id = id;
        this.moment = moment;
    }


}
