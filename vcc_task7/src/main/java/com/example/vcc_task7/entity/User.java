package com.example.vcc_task7.entity;

public class User {
    private Integer id;

    private String name;

    private String address;

    private Integer age;

    private Long money;

    public User() {
    }

    public User(Integer id, String name, String address, Integer age) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.money = 0L;
    }

    public User(Integer id, String name, String address, Integer age, Long money) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.age = age;
        this.money = money;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }
}
