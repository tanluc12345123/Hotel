package com.tutorial.apidemo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "room_order")
public class RoomOrder extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "identity_card")
    private String identity_card;

    @Column(name = "arrival_date")
    private Date arrival_date;

    @Column(name = "departure_date")
    private Date departure_date;

    @Column(name = "number_of_people")
    private int number_of_people;

    @Column(name = "payment")
    private boolean payment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Room room;

    @Transient
    private String room_name;

    @Transient
    private String hotel_name;

    @Transient
    private String location_name;

    public RoomOrder(){

    }

    public RoomOrder(int id, String name, String phone, String email, String identity_card, Date arrival_date, Date departure_date, int number_of_people, boolean payment) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.identity_card = identity_card;
        this.arrival_date = arrival_date;
        this.departure_date = departure_date;
        this.number_of_people = number_of_people;
        this.payment = payment;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentity_card() {
        return identity_card;
    }

    public void setIdentity_card(String identity_card) {
        this.identity_card = identity_card;
    }

    public Date getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(Date arrival_date) {
        this.arrival_date = arrival_date;
    }

    public Date getDeparture_date() {
        return departure_date;
    }

    public void setDeparture_date(Date departure_date) {
        this.departure_date = departure_date;
    }

    public int getNumber_of_people() {
        return number_of_people;
    }

    public void setNumber_of_people(int number_of_people) {
        this.number_of_people = number_of_people;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

//    public Room getRoom() {
//        return room;
//    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getRoom_name() {
        return room.getRoom_name();
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getHotel_name() {
        return room.getHotel_name();
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getLocation_name() {
        return room.getLocation_name();
    }

    public void setLocation_name(String location_name) {
        this.location_name = location_name;
    }
}
