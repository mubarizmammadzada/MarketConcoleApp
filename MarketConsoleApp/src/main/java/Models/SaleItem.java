package Models;

public class SaleItem {
    public int getId() {
        return id;
    }

    private  int id;
    private static int saleItemCount=0;
    private Product product;
    private int count;


    public SaleItem(Product product,int count) {
        saleItemCount++;
        this.id=saleItemCount;
        this.product=product;
        this.count=count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
