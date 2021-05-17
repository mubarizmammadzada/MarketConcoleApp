import Enums.ProductType;
import Interfaces.IMarketable;
import Models.Product;
import Models.Sale;
import Models.SaleItem;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Market implements IMarketable {
    private ArrayList<Sale> sales = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    //Add sale methodunda static date set etmek uchun.
    private static int counter = 0;

    public ArrayList<Product> getProducts() {
        return products;
    }

    @Override
    public List<Sale> getSales() {
        List<Sale> sales = this.sales.stream()
                .filter(sale -> sale.isCancelled() == false).collect(Collectors.toList());
        return sales;
    }

    @Override
    public void addSale(List<SaleItem> saleItemss) {
        counter++;
        Sale sale = new Sale();
        LocalDate saleDate;
        if (counter == 1) {
            saleDate = LocalDate.of(2010, 10, 23);
            sale.setSaleDate(saleDate);
        }
        if (counter == 2) {
            saleDate = LocalDate.of(2022, 10, 23);
            sale.setSaleDate(saleDate);
        }
        if (counter == 3) {
            saleDate = LocalDate.of(2022, 10, 23);
            sale.setSaleDate(saleDate);
        }


        for (SaleItem slItem : saleItemss) {
            sale.getSaleItems().add(slItem);
        }

        double price = 0;
        for (SaleItem s : sale.getSaleItems()) {
            price += s.getProduct().getPrice() * s.getCount();
        }
        for (SaleItem saleItem : sale.getSaleItems()) {
            Product product = this.products.stream()
                    .filter(product1 -> product1.getId().trim().toLowerCase()
                            .equals(saleItem.getProduct().getId().trim().toLowerCase())).findAny().get();
            product.setCount(product.getCount() - saleItem.getCount());
        }
        sale.setSalePrice(price);
        this.sales.add(sale);
    }

    @Override
    public void removeSale(String SaleCode) {
        Optional<Sale> sale = this.sales.stream()
                .filter(sale1 -> sale1.getId().trim().toLowerCase()
                        .equals(SaleCode.trim().toLowerCase())).findAny();

        if (sale.isEmpty()) {
            System.out.println("Bele bir satsih olmayib");
        } else {
            sale.get().setCancelled(true);
            for (SaleItem saleItem : sale.get().getSaleItems()) {
                Product product1 = this.products.stream()
                        .filter(product2 -> product2.getId().trim().toLowerCase()
                                .equals(saleItem.getProduct().getId().trim().toLowerCase())).findAny().get();
                product1.setCount(saleItem.getCount() + product1.getCount());

            }
            for (Product pr : this.products) {
                System.out.println(pr.getName() + " " + pr.getCount());
            }
        }
    }

    @Override
    public void removeSaleItem(String saleCode, int saleItemCode, int count) {

        Optional<Sale> sale = this.sales.stream()
                .filter(sale1 -> sale1.getId().trim().toLowerCase()
                        .equals(saleCode.trim().toLowerCase())).findAny();

        Optional<SaleItem> saleItem = sale.get().getSaleItems().stream()
                .filter(saleItem1 -> saleItem1.getId() == saleItemCode).findAny();
        if (saleItem.isEmpty()) {
            System.out.println("Sale Item not ofund");

        } else {
            if (saleItem.get().getCount() < count) {
                System.out.println("You have not received the number of products included");
            } else {
//                SaleItem saleItem1 = sale.get().getSaleItems().stream()
//                        .filter(saleItem2 -> saleItem2.getId() == saleItemCode).findAny().get();

                sale.get().getSaleItems().remove(saleItem.get());
                Product product = this.products.stream()
                        .filter(product1 -> product1.getId().trim().toLowerCase()
                                .equals(saleItem.get().getProduct().getId().trim().toLowerCase())).findAny().get();
                product.setCount(product.getCount() + count);
                saleItem.get().setCount(saleItem.get().getCount() - count);
                ArrayList<SaleItem> saleItems = new ArrayList<>();
                for (SaleItem s : sale.get().getSaleItems()) {
                    saleItems.add(s);
                    if (saleItem.get().getCount() > 0) {
                        saleItems.add(saleItem.get());
                    }
                    this.addSale(saleItems);
                    System.out.println("Sonra");




                }
                System.out.println("Sonra");
                System.out.println("Sale item was deleted");

            }


        }

    }


    @Override
    public List<Sale> getSaleByDateInterval(LocalDate date1, LocalDate date2) {
        List<Sale> salesWithDate = this.sales.stream()
                .filter(sale -> sale.getSaleDate().isAfter(date1)
                        && sale.getSaleDate().isBefore(date2)).collect(Collectors.toList());

        return salesWithDate;
    }

    @Override
    public List<Sale> getSalesByDate(LocalDate date) {
        List<Sale> salesWithDate = this.sales.stream()
                .filter(sale -> sale.getSaleDate().equals(date)).collect(Collectors.toList());
        return salesWithDate;
    }

    @Override
    public List<Sale> getSalesByPriceInterval(double minPrice, double maxPrice) {
        List<Sale> saleWitPriceInterval = this.sales.stream()
                .filter(sale -> sale.getSalePrice() >= minPrice &&
                        sale.getSalePrice() <= maxPrice).collect(Collectors.toList());
        return saleWitPriceInterval;
    }

    @Override
    public Sale getSaleBySaleUniqueCode(String saleUniqueCode) {
        Optional<Sale> sale = this.sales.stream()
                .filter(sale1 -> sale1.getId().toLowerCase().trim()
                        .equals(saleUniqueCode.trim().toLowerCase())).findAny();
        if (sale.isEmpty()) {
            System.out.println("Product not found");
            return null;
        } else {
            return sale.get();
        }
    }

    @Override
    public void addProduct(Product product) {
        boolean isExistProduct = false;
        for (Product product1 : this.products) {
            if (product.getName().toLowerCase().trim().equals(product1.getName().toLowerCase().trim())) {
                isExistProduct = true;
                break;
            }
        }
        if (isExistProduct) {
            String asnwer;
            do {
                System.out.println(product.getName() + " adinda mehsulumuz var.Yene de yaratmaq isteyirsinizmi?(y-beli,n-xeyir)");
                Scanner scanner = new Scanner(System.in);
                asnwer = scanner.next();
                if (asnwer.trim().toLowerCase().equals("y")) {
                    isExistProduct = false;
                }
                if (asnwer.trim().toLowerCase().equals("n")) {
                    isExistProduct = true;
                }

            } while (!asnwer.trim().toLowerCase().equals("y") && !asnwer.trim().toLowerCase().equals("n"));
        }
        if (product.getPrice() <= 0) {
            isExistProduct = true;
            System.out.println(product.getName() + "'s Price can't be less than zero");
        }
        if (!isExistProduct) {
            products.add(product);
            System.out.println(product.getName() + " was added to Productlist");
        } else {
            System.out.println(product.getName() + " wasn't added to Productlist");
        }
    }

    @Override
    public void editProduct(String uniqueProductCode, Product product) {
        if (product.getPrice() <= 0) {
            System.out.println(product.getName() + "'s price can't be less tha zero");
        } else {
            Product pr = this.products.stream().filter(product2 -> product2.getId().toLowerCase().trim()
                    .equals(uniqueProductCode.toLowerCase().trim())).findAny().get();
            pr.setCount(product.getCount());
            pr.setName(product.getName());
            pr.setPrice(product.getPrice());
            pr.setProductType(product.getProductType());
            System.out.println("Changed product's name is:" + pr.getName() + " price's:" + pr.getPrice()
                    + " count's:" + pr.getCount());
        }


    }

    @Override
    public List<Product> getProductsByCategory(ProductType category) {
        List<Product> products = this.products.stream().
                filter(product -> product.getProductType().equals(category)).collect(Collectors.toList());
        return products;
    }

    @Override
    public List<Product> getProductByPriceInterval(double minPrice, double maxPrice) {
        List<Product> products = this.products.stream()
                .filter(pr -> pr.getPrice() >= minPrice && pr.getPrice() <= maxPrice).collect(Collectors.toList());
        return products;
    }

    @Override
    public List<Product> searchProductsByName(String searchWord) {
        List<Product> products = this.products.stream()
                .filter(product -> product.getName().toLowerCase().trim()
                        .contains(searchWord.toLowerCase().trim())).collect(Collectors.toList());
        return products;
    }
}
