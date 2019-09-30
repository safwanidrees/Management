package com.example.management;

public class Detail {

    private String DetailId;
    private String  Name;
    private String  Email;

    private String Phone;
    private String Address;
    private String orderspinner;

    public Detail() {
    }


    public Detail(String DetailId, String Name, String Email, String Phone, String Address, String orderspinner) {
        this.DetailId = DetailId;
        this.Name = Name;
        this.Email = Email;
        this.Phone = Phone;
        this.Address = Address;
        this.orderspinner = orderspinner;
    }

    public void setDetailId(String detailId) {
        DetailId = detailId;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setOrderspinner(String orderspinner) {
        this.orderspinner = orderspinner;
    }

    public String getDetailId() {
        return DetailId;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getAddress() {
        return Address;
    }

    public String getOrderspinner() {
        return orderspinner;
    }

}
