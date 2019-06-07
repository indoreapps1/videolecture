package com.example.videolecture.model;

public class Result {

    private String image;

    private int id;

    private String categoryName;

    private String loginId;

    private String phone;

    private String status;

    public String getLoginId ()
    {
        return loginId;
    }

    public void setLoginId (String loginId)
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
