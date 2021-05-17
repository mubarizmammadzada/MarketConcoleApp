package Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Sale {
    public String getId() {
        return id;
    }

    private String  id;
    private static int saleCount=0;
    private double salePrice;
    private ArrayList<SaleItem> saleItems;
    private LocalDate saleDate;
    private boolean isCancelled=false;

    public void setSaleDate(LocalDate saleDate) {
        saleItems=new ArrayList<>();
        this.saleDate = saleDate;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }



    public Sale(){
        saleCount++;
        this.id="S"+saleCount;
        this.saleDate=LocalDate.now();
        this.isCancelled=false;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public ArrayList<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(ArrayList<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }
}
