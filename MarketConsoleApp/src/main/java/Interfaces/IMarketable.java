package Interfaces;

import Enums.ProductType;
import Models.Product;
import Models.Sale;
import Models.SaleItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface IMarketable {
    List<Sale> getSales();

    void addSale(List<SaleItem> saleItems);

    void removeSaleItem(String salecode,int SaleItemCode,int count);

    void removeSale(String SaleCode);

    List<Sale> getSaleByDateInterval(LocalDate date1, LocalDate date2);

    List<Sale> getSalesByDate(LocalDate date);

    List<Sale> getSalesByPriceInterval(double firstPrice, double secondPrice);

    Sale getSaleBySaleUniqueCode(String saleUniqueCode);

    void addProduct(Product product);

    void editProduct(String uniqueProductCode, Product product);

    List<Product> getProductsByCategory(ProductType category);

    List<Product> getProductByPriceInterval(double minPrice, double maxPrice);

    List<Product> searchProductsByName(String searchWord);


}
