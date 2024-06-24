package com.example.vcc_spring.entity;

public class Student {
    private Integer id;

    private String name;

    private String sex;

    private Integer age;

    private Double toan;

    private Double ly;

    private Double hoa;

    private Double average;

    private String result;

    public Student() {
    }

    public Student(Integer id, String name, String sex, Integer age, Double toan, Double ly, Double hoa) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.toan = toan;
        this.ly = ly;
        this.hoa = hoa;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getToan() {
        return toan;
    }

    public void setToan(Double toan) {
        this.toan = toan;
    }

    public Double getLy() {
        return ly;
    }

    public void setLy(Double ly) {
        this.ly = ly;
    }

    public Double getHoa() {
        return hoa;
    }

    public void setHoa(Double hoa) {
        this.hoa = hoa;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "{" +
                "Id = " + id + "\n" +
                "Name = '" + name + '\'' + "\n" +
                "Sex = '" + sex + '\'' + "\n" +
                "Age = " + age + "\n" +
                "Toan = " + toan + "\n" +
                "Ly = " + ly + "\n" +
                "Hoa = " + hoa + "\n" +
                "TB = " + average + "\n" +
                "KQHT = " + result + "\n" +
                '}';
    }
}
