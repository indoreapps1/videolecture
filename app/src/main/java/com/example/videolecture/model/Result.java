package com.example.videolecture.model;

public class Result {

    private String image;

    private int id;

    private String categoryName;

    private int loginId;

    private String phone;

    private String status;

    private String v_title;

    public Result(String v_title, String v_description, String v_review) {
        this.v_title = v_title;
        this.v_description = v_description;
        this.v_review = v_review;
    }

    public String getV_title() {
        return v_title;
    }

    public String getV_description() {
        return v_description;
    }

    public String getV_review() {
        return v_review;
    }

    private String v_description;

    private String v_review;


    public int getLoginId ()
    {
        return loginId;
    }

    public void setLoginId (int loginId)
    {
        this.loginId = loginId;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getCategoryName ()
    {
        return categoryName;
    }

    public void setCategoryName (String categoryName)
    {
        this.categoryName = categoryName;
    }

//    String text;
//    int img;
//
//    public Result(int img,  String text) {
//        this.img = img;
//        this.text = text;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public int getImg() {
//        return img;
//    }
//
//    public void setImg(int img) {
//        this.img = img;
//    }
}
