package Models;

import Enums.ProductType;

public class Product {

    public String getId() {
        return id;
    }

    private String id;
    private static int productCount=0;
    private String name;
    private double price;
    private ProductType productType;
    private int count;


    public Product(String name,double price,ProductType category,int count){
        productCount++;
        this.id=codeGenerator(category)+"-"+ productCount;
        this.name=name;
        this.price=price;
        this.productType=category;
        this.count=count;
    }

    private static String codeGenerator(ProductType type){
        switch (type){
            case PLAYSTATION:
                return "P";
            case XBOX:
                return "X";
            case COMPUTER:
                return "COMP";
            case CAMERA:
                return "CAM";
            default:
                return "MOB";
        }

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
