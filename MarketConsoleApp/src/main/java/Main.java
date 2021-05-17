import Enums.ProductType;
import Models.Product;
import Models.Sale;
import Models.SaleItem;
import jdk.jfr.Category;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Market market = new Market();
        Product product = new Product("Asus", 900, ProductType.COMPUTER, 20);
        Product product1 = new Product("Acer", 2500, ProductType.COMPUTER, 40);
        Product product2 = new Product("Iphone6", 1000, ProductType.MOBILE, 40);
        Product product3 = new Product("Iphone6s", 1300, ProductType.MOBILE, 40);
        Product product4 = new Product("Iphone8", 1700, ProductType.MOBILE, 40);
        Product product5 = new Product("Ps3", 2100, ProductType.PLAYSTATION, 40);
//        LocalDate startDate = LocalDate.parse("10-12-2000", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//        System.out.println("----------");
//        System.out.println(startDate);
        market.addProduct(product);
        market.addProduct(product2);
        market.addProduct(product3);
        market.addProduct(product4);
        market.addProduct(product5);
        market.addProduct(product1);
//
//        SaleItem saleItem = new SaleItem(product, 3);
//        SaleItem saleItem1 = new SaleItem(product, 1);
//        SaleItem saleItem2 = new SaleItem(product2, 1);
//        SaleItem saleItem3 = new SaleItem(product2, 2);
//
//        ArrayList<SaleItem> saleItems = new ArrayList<>();
//
//        saleItems.add(saleItem);
//        market.addSale(saleItems);
//        market.removeSaleItem("S1", 1, 4);
        boolean isContinued = false;
        do {
            fistMenu();
            Scanner scanner = new Scanner(System.in);
            String operationSelection = scanner.next();
            switch (operationSelection) {
                case "1":
                    productOperationSelections();
                    Scanner scanner1 = new Scanner(System.in);
                    String selectProductSelection = scanner.next();

                    switch (selectProductSelection) {
                        case "1":
                            market.addProduct(createProduct());
                            break;
                        case "2":
                            if (market.getProducts().size() == 0) {
                                System.out.println("There is no product in the market");
                                isContinued = true;
                            } else {
                                System.out.println("Type the code of the product you want to change");
                                System.out.println("----------------------");
                                for (Product p : market.getProducts()) {
                                    System.out.println(p.getId() + "-" + p.getName());
                                }
                                market.editProduct(selectCode(market), createProduct());
                            }
                            isContinued = true;
                            break;
                        case "3":
                            if (market.getProducts().size() == 0) {
                                System.out.println("There is no product in the market");
                                isContinued = true;
                            } else {
                                System.out.println("Type the code of the product you want to change");
                                System.out.println("----------------------");
                                for (Product p : market.getProducts()) {
                                    System.out.println(p.getId() + "-" + p.getName());
                                }
                                removeProduct(market);
                            }
                            isContinued = true;
                            break;
                        case "4":
                            for (Product p : market.getProducts()) {
                                System.out.println(p.getId() + " " + p.getName());
                            }
                        case "5":
                            ProductType type = findCategory();

                            List<Product> products = market.getProductsByCategory(type);
                            for (Product p : products) {
                                System.out.println(p.getId() + " " + p.getName());
                            }
                            break;
                        case "6":
                            Scanner firstPriceInput;
                            String firstPriceString;
                            double minPrice = 0;
                            Scanner secondPriceInput;
                            String secondPriceString;
                            double maxPrice = 0;
                            Boolean isnNumber = true;
                            do {
                                System.out.println("Enter minimum price");
                                firstPriceInput = new Scanner(System.in);
                                firstPriceString = firstPriceInput.next();
                                System.out.println("Enter maximum price");
                                secondPriceInput = new Scanner(System.in);
                                secondPriceString = firstPriceInput.next();
                                try {
                                    minPrice = Double.parseDouble(firstPriceString);
                                    maxPrice = Double.parseDouble(secondPriceString);
                                    isnNumber = true;
                                    if (minPrice < 0 || maxPrice <= 0) {
                                        System.out.println("Enter positive numbers");
                                        isnNumber = false;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Enter number type");
                                    isnNumber = false;
                                }
                            } while (!isnNumber);
                            List<Product> products1 = market.getProductByPriceInterval(minPrice, maxPrice);
                            if (products1.size() == 0) {
                                System.out.println("Not found product with price interval");
                            } else {
                                for (Product p : products1) {
                                    System.out.println(p.getId() + "," + p.getName() + "," + p.getProductType() + "," + p.getCount() + "," + p.getPrice());
                                }
                            }

                            break;
                        case "7":
                            System.out.println("Enter word for product searching");
                            Scanner searchScanner = new Scanner(System.in);
                            String searchingString = searchScanner.next();
                            List<Product> searchingProduct = market.searchProductsByName(searchingString);
                            if (searchingProduct.size() == 0) {
                                System.out.println("Not found product with searching word");
                            } else {
                                for (Product p : searchingProduct) {
                                    System.out.println(p.getId() + "," + p.getName() + "," + p.getProductType() + "," +
                                            p.getCount() + "," + p.getPrice());
                                }
                            }
                            break;
                        case "0":
                            isContinued = true;
                            break;


                    }
                    break;
                case "2":
                    saleOperationSelection();
                    Scanner saleOperationSelectionInput = new Scanner(System.in);
                    String saleOperationSelectionString = scanner.next();
                    switch (saleOperationSelectionString) {
                        case "1":
                            Scanner saleItemCountInput;
                            String saleItemCountString;
                            boolean correctCount = true;
                            int count = 0;
                            do {
                                try {
                                    System.out.println("Enter wanted items count");
                                    saleItemCountInput = new Scanner(System.in);
                                    saleItemCountString = saleItemCountInput.next();
                                    count = Integer.parseInt(saleItemCountString);


                                } catch (Exception e) {
                                    System.out.println("Enter number type");
                                    correctCount = false;
                                }
                                if (count <= 0) {
                                    correctCount = false;
                                } else {
                                    correctCount = true;
                                }
                            } while (!correctCount);
                            ArrayList<SaleItem> saleItems = createSaleItemList(count, market);
                            market.addSale(saleItems);
                            isContinued = true;
                            break;
                        case "2":
                            String saleCode = getSaleCode(market);
                            int saleItemId = getSaleItemCode(saleCode, market);
                            int removedProductCount = getCount();
                            market.removeSaleItem(saleCode, saleItemId, removedProductCount);
                        case "3":
                            Scanner saleRemoveCodeInput;
                            String saleRemoveString;
                            Optional<Sale> sale = null;
                            boolean isSelected = true;
                            do {
                                for (Sale s : market.getSales()) {
                                    System.out.println(s.getId() + "," + s.getSalePrice());
                                }
                                System.out.println("Enter sale code for remove");
                                saleRemoveCodeInput = new Scanner(System.in);
                                saleRemoveString = saleRemoveCodeInput.next();
                                String finalSaleRemoveString = saleRemoveString;
                                sale = market.getSales().stream()
                                        .filter(sale1 -> sale1.getId().trim().toLowerCase()
                                                .equals(finalSaleRemoveString.trim().toLowerCase())).findAny();
                                if (sale.isEmpty()) {
                                    isSelected = false;
                                    System.out.println("Enter correct code");
                                } else {
                                    isSelected = true;
                                }
                            } while (!isSelected);
                            for (Product p : market.getProducts()) {
                                System.out.println(p.getName() + " " + p.getCount());
                            }
                            market.removeSale(sale.get().getId());
                            isContinued = true;
                            break;
                        case "4":
                            System.out.println("All sales list");
                            for (Sale sale1 : market.getSales()) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                System.out.println(sale1.getId() + "," + sale1.getSalePrice() + "," + sale1.getSaleDate()
                                        .format(formatter));
                            }
                            isContinued = true;
                            break;
                        case "5":
                            Scanner firstDateOfSalesInput;
                            String firstDateOfString;
                            Scanner secondDateOfSalesInput;
                            String secondDateOfString;
                            LocalDate firstDatee = null;
                            LocalDate secondDatee = null;
                            boolean isDates = true;
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                            do {
                                System.out.println("Enter first date");
                                firstDateOfSalesInput = new Scanner(System.in);
                                firstDateOfString = firstDateOfSalesInput.next();
                                System.out.println("Enter second date");
                                secondDateOfSalesInput = new Scanner(System.in);
                                secondDateOfString = secondDateOfSalesInput.next();

                                try {
                                    firstDatee = LocalDate.parse(firstDateOfString, formatter);
                                    secondDatee = LocalDate.parse(secondDateOfString, formatter);
                                } catch (Exception e) {
                                    System.out.println("Enter correct format date");
                                    isDates = false;
                                }
                            } while (!isDates);

                            ;
                            for (Sale s : market.getSaleByDateInterval(firstDatee,secondDatee)) {
                                System.out.println(s.getId() + "," + s.getSalePrice());
                            }
                            isContinued = true;
                            break;
                        case "6":
                            Scanner minPriceInput = null;
                            String minPriceString = null;
                            Scanner maxPriceInput = null;
                            String maxPriceString = null;
                            double maxSalePrice = -1;
                            double minSalePrice = -1;
                            boolean isSalePrice = true;
                            DateTimeFormatter formatterr = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                            do {
                                System.out.println("Enter minimum price");
                                minPriceInput = new Scanner(System.in);
                                minPriceString = minPriceInput.next();
                                System.out.println("Enter maximum price");
                                maxPriceInput = new Scanner(System.in);
                                maxPriceString = maxPriceInput.next();
                                try {
                                    minSalePrice = Double.parseDouble(minPriceString);
                                    maxSalePrice = Double.parseDouble(maxPriceString);
                                } catch (Exception e) {
                                    System.out.println("Enter number type");
                                    isSalePrice = false;
                                }
                                if (minSalePrice <= 0 || maxSalePrice <= 0) {
                                    System.out.println("Enter positive number");
                                    isSalePrice = false;
                                } else {
                                    isSalePrice = true;
                                }
                            } while (!isSalePrice);
                            for (Sale s : market.getSalesByPriceInterval(minSalePrice, maxSalePrice)) {
                                System.out.println(s.getId() + "," + s.getSalePrice() + "," + s.getSaleDate().format(formatterr));
                            }
                            isContinued = true;
                            break;
                        case "7":
                            Scanner DateOfSalesInput;
                            String DateOfString;
                            LocalDate date = null;
                            DateTimeFormatter formatte = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

                            boolean isDate = true;
                            do {
                                System.out.println("Enter first date");
                                DateOfSalesInput = new Scanner(System.in);
                                DateOfString = DateOfSalesInput.next();
                                try {
                                    date = LocalDate.parse(DateOfString, formatte);
                                } catch (Exception e) {
                                    System.out.println("Enter correct format date");
                                    isDates = false;
                                }
                            } while (!isDate);
                            for (Sale s : market.getSalesByDate(date)) {
                                System.out.println(s.getId() + "," + s.getSalePrice());
                            }
                            isContinued = true;

                            break;
                        case "8":

                            String s = getSaleCode(market);
                            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MMM-uuuu");

                            Optional<Sale> salle = market.getSales().stream()
                                    .filter(sale1 -> sale1.getId().trim().toLowerCase()
                                            .equals(s.trim().toLowerCase())).findAny();
                            if (salle.isEmpty()) {
                                System.out.println("Sale not found");
                            } else {
                                System.out.println(salle.get().getId() + "," + salle.get().getSalePrice() + ","
                                        + salle.get().getSaleDate().format(format));
                            }
                            isContinued = true;
                            break;
                        case "0":
                            isContinued = true;
                            break;

                    }

                    break;
                case "3":
                    isContinued = false;
                    System.out.println("Exit from system");
                    break;


            }
        } while (isContinued);


    }

    public static void fistMenu() {
        System.out.println("1-operations on products");
        System.out.println("2-sales operations");
        System.out.println("3-Exit system");
    }

    public static void productOperationSelections() {
        System.out.println();
        System.out.println("1-Create new Product");
        System.out.println("2-Edit product");
        System.out.println("3-Delete product");
        System.out.println("4-Show all products");
        System.out.println("5-Show all products by Product category");
        System.out.println("6-Show all products by price interval");
        System.out.println("7-Search product by Product's name");
        System.out.println("0-Back previous step");
    }

    public static void saleOperationSelection() {
        System.out.println();
        System.out.println("1-Add new Sale");
        System.out.println("2-Return of the received product");
        System.out.println("3-Remove Sale");
        System.out.println("4-Show all sales");
        System.out.println("5-Show sales by date interval");
        System.out.println("6-Show sales by price interval");
        System.out.println("7-Show sales by date");
        System.out.println("8-Show sale details by Salecode");
        System.out.println("0-Back previous step");

    }

    public static Product createProduct() {
        System.out.println("Enter Product name");
        Scanner inputProductName = new Scanner(System.in);
        String stringProductName = inputProductName.next();
        System.out.println("Enter Product price");
        boolean isNumber = true;

        double price = -1;
        do {

            Scanner inputProductPrice = new Scanner(System.in);
            String stringProductPrice = inputProductName.next();
            try {
                System.out.println("Please enter correct number");
                price = Double.parseDouble(stringProductPrice);
            } catch (Exception e) {

                isNumber = false;
            }
            if (price <= 0) {
                isNumber = false;
            } else {
                isNumber = true;
            }
        } while (!isNumber);

        int counter = 0;
        for (ProductType p : ProductType.values()) {
            counter++;
            System.out.println(counter + p.toString());

        }
        String stringProductCategory = null;
        do {
            System.out.println("Select category");
            Scanner selectProductCategory;

            int selectionCategory = -1;
            try {
                System.out.println("Please enter correct number" + "(1-" + counter + ")");
                selectProductCategory = new Scanner(System.in);
                stringProductCategory = selectProductCategory.next();

                selectionCategory = Integer.parseInt(stringProductCategory);
            } catch (Exception e) {

                isNumber = false;
            }
            if (selectionCategory <= 0) {
                isNumber = false;
            } else {
                isNumber = true;
            }
        } while (!isNumber);
        ProductType type = null;
        switch (stringProductCategory) {
            case "1":
                type = ProductType.COMPUTER;
                break;
            case "2":
                type = ProductType.MOBILE;
                break;
            case "3":
                type = ProductType.CAMERA;
                break;
            case "4":
                type = ProductType.PLAYSTATION;
                break;
            case "5":
                type = ProductType.XBOX;
        }
        System.out.println("Enter product count");
        Scanner productCountScanner;
        String productCountString;
        int productCount = -1;
        do {
            System.out.println("Please enter number type");
            productCountScanner = new Scanner(System.in);
            productCountString = productCountScanner.next();

            try {

                productCount = Integer.parseInt(productCountString);

            } catch (Exception e) {
                isNumber = false;
            }
            if (productCount <= 0) {
                isNumber = false;
            } else {
                isNumber = true;
            }
        } while (!isNumber);
        Product product = new Product(stringProductName, price, type, productCount);

        return product;
    }

    public static String selectCode(Market market) {
        boolean isExistCode = true;
        Scanner codeForEditionInput;
        String codeForEditionString;
        Optional<Product> product;
        do {
            System.out.println("Please enter correct code");
            codeForEditionInput = new Scanner(System.in);
            codeForEditionString = codeForEditionInput.next();
            String finalCodeForEditionString = codeForEditionString;
            product = market.getProducts().stream()
                    .filter(product1 -> product1.getId().trim().toLowerCase()
                            .equals(finalCodeForEditionString.trim().toLowerCase())).findAny();
            if (product.isEmpty()) {
                isExistCode = false;
            } else {
                isExistCode = true;
            }
        } while (!isExistCode);

        return codeForEditionString;
    }

    public static void removeProduct(Market market) {
        String productCode = selectCode(market);
        Optional<Product> product = market.getProducts().stream()
                .filter(product1 -> product1.getId().trim().toLowerCase()
                        .equals(productCode)).findAny();
        product.get().setCount(0);
        System.out.println(product.get().getName() + "- was deleted");
    }

    public static ProductType findCategory() {

        boolean isExistType = true;
        ProductType type = ProductType.COMPUTER;
        do {
            Scanner findCategoryScanner;
            String categoryString;
            int counter = 0;
            for (ProductType p : ProductType.values()) {
                counter++;
                System.out.println(counter + "-" + p.toString());
            }
            categoryString = "";
            int typeNumber = 0;
            try {
                System.out.println();
                System.out.println("Select category");
                findCategoryScanner = new Scanner(System.in);
                categoryString = findCategoryScanner.next();
                typeNumber = Integer.parseInt(categoryString);
                isExistType = true;
            } catch (Exception e) {
                isExistType = false;
                System.out.println("Enter number type");
            }
            if (isExistType && typeNumber >= 1 && typeNumber <= counter) {

                switch (categoryString) {
                    case "1":
                        type = ProductType.COMPUTER;
                        break;
                    case "2":
                        type = ProductType.MOBILE;
                        break;
                    case "3":
                        type = ProductType.CAMERA;
                        break;
                    case "4":
                        type = ProductType.PLAYSTATION;
                        break;
                    default:
                        type = ProductType.XBOX;
                        break;
                }
            } else {
                isExistType = false;
            }

        } while (!isExistType);
        return type;
    }

    public static ArrayList<SaleItem> createSaleItemList(int count, Market market) {
        ArrayList<SaleItem> saleItems = new ArrayList<>();
        System.out.println("Products:");
        System.out.println();

        Scanner selectProductInput;
        String selectProductString;
        Scanner selectProductCountInput;
        String selectProductCountString;
        boolean isCorrectInput = true;
        int productCount = 0;
        Optional<Product> product = null;
        for (int i = 0; i < count; i++) {
            do {
                for (Product p : market.getProducts()) {
                    System.out.println(p.getId() + "," + p.getName() + "," + p.getPrice() + "," + p.getCount());
                }
                System.out.println("Enter selected product code");
                selectProductInput = new Scanner(System.in);
                selectProductString = selectProductInput.next();
                String finalSelectProductString = selectProductString;
                product = market.getProducts().stream()
                        .filter(product1 -> product1.getId().trim().toLowerCase()
                                .equals(finalSelectProductString.trim().toLowerCase())).findAny();

                if (product.isEmpty()) {
                    isCorrectInput = false;
                } else {
                    isCorrectInput = true;
                }
            } while (!isCorrectInput);

            do {
                try {
                    System.out.println("Enter product count");
                    selectProductCountInput = new Scanner(System.in);
                    selectProductCountString = selectProductCountInput.next();
                    productCount = Integer.parseInt(selectProductCountString);
                    if (productCount <= 0 || productCount > product.get().getCount()) {
                        isCorrectInput = false;
                    } else {
                        isCorrectInput = true;
                    }
                } catch (Exception e) {
                    System.out.println("Enter number type");
                    isCorrectInput = false;
                }

            } while (!isCorrectInput);

            SaleItem saleItem = new SaleItem(product.get(), productCount);
            saleItems.add(saleItem);
        }

        return saleItems;
    }

    public static String getSaleCode(Market market) {
        Scanner saleFinderByCodeInput;
        String saleFinderByCodeString = null;
        Optional<Sale> sale = null;
        boolean isExistSale = true;
        do {
            for (Sale sale1 : market.getSales()) {
                System.out.println(sale1.getId() + "," + sale1.getSalePrice());
            }
            System.out.println("Enter sale code");
            saleFinderByCodeInput = new Scanner(System.in);
            saleFinderByCodeString = saleFinderByCodeInput.next();
            String finalSaleFinderByCodeString = saleFinderByCodeString;
            sale = market.getSales().stream()
                    .filter(sale1 -> sale1.getId().trim().toLowerCase()
                            .equals(finalSaleFinderByCodeString.trim().toLowerCase())).findAny();
            if (sale.isEmpty()) {
                isExistSale = false;
            } else {
                isExistSale = true;
            }
        } while (!isExistSale);
        return sale.get().getId();
    }

    public static int getSaleItemCode(String saleCode, Market market) {
        Sale sale = market.getSales().stream()
                .filter(sale1 -> sale1.getId().trim().toLowerCase()
                        .equals(saleCode.trim().toLowerCase())).findAny().get();
        Scanner findSaleItemByCodeInput;
        String findSaleItemByCodeString = null;
        Optional<SaleItem> saleItem = null;
        int saleItemId = -1;
        boolean isExistSaleItem = true;
        do {
            for (SaleItem s : sale.getSaleItems()) {
                System.out.println(s.getId() + "," + s.getProduct().getName() + "," + s.getCount());
            }
            System.out.println("Enter saleItem code");
            findSaleItemByCodeInput = new Scanner(System.in);
            findSaleItemByCodeString = findSaleItemByCodeInput.next();
            try {
                saleItemId = Integer.parseInt(findSaleItemByCodeString);

            } catch (Exception e) {
                System.out.println("Enter correct code (Number type)");
                isExistSaleItem = false;
            }
            if (saleItemId <= 0) {
                System.out.println("Enter positive number");
                isExistSaleItem = false;
            } else {
                isExistSaleItem = true;
            }
        } while (!isExistSaleItem);
        return saleItemId;
    }

    public static int getCount() {
        Scanner getCountInput;
        String getCountString;
        boolean isNumber = true;
        int count = -1;
        do {
            System.out.println("Enter count for removing item");
            getCountInput = new Scanner(System.in);
            getCountString = getCountInput.next();
            try {
                count = Integer.parseInt(getCountString);

            } catch (Exception e) {
                System.out.println("Enter positive number");
                isNumber = false;
            }
            if (count <= 0) {
                System.out.println("Enter positive number");
                isNumber = false;
            } else {
                isNumber = true;
            }
        } while (!isNumber);
        return count;
    }

}
