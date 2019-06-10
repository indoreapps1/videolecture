package com.example.videolecture.model;

public class Result {

    private String image;

    private String id;

    private String categoryName;

    private int loginId;

    private String phone;

    private String status;

    private String subcategoryId;

    private String title;

    private String categoryId;

    public String getSubcategoryId ()
    {
        return subcategoryId;
    }

    public void setSubcategoryId (String subcategoryId)
    {
        this.subcategoryId = subcategoryId;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getCategoryId ()
    {
        return categoryId;
    }

    public void setCategoryId (String categoryId)
    {
        this.categoryId = categoryId;
    }






    private String v_title;
//
//    private String ques;
//
    private String ans;

    private String v_name;

    public Result(String v_name){
        this.v_name=v_name;
    }
    public String getV_name() {
        return v_name;
    }

    //    public Result(String ans) {
//        this.ans = ans;
//    }

    public String getAns() {
        return ans;
    }

//    public Result(String ques) {
//        this.ques = ques;
//    }

//    public String getQues() {
//        return ques;
//    }

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



    private String productId;


    private String description;

    private String video;

    private String time;

    public String getProductId ()
    {
        return productId;
    }

    public void setProductId (String productId)
    {
        this.productId = productId;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getVideo ()
    {
        return video;
    }

    public void setVideo (String video)
    {
        this.video = video;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }


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

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
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
