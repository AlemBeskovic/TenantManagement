package com.Beskovica.tenantsystem.model;

import jakarta.persistence.*;

@Entity
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private int apartmentNumber;

   private String town;
   private String state;
   private int monthRent;
   private int balance;

   @Transient
   private int depositAmount;




    public Tenant() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Tenant {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", apartmentNumber=" + apartmentNumber +
                ", town='" + town + '\'' +
                ", state='" + state + '\'' +
                ", monthRent=" + monthRent +
                ", balance=" + balance +
                '}';
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getMonthRent() {
        return monthRent;
    }

    public void setMonthRent(int monthRent) {
        this.monthRent = monthRent;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getDepositAmount(){
        return depositAmount;
    }
    public void setDepositAmount(int depositAmount){
        this.depositAmount = depositAmount;
    }


}
