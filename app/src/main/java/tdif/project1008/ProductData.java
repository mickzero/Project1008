package tdif.project1008;

/**
 * Created by 58010461 on 11/13/2017.
 */

public class ProductData {
    private int id;
    private String product;
    private String detail;
    private int price;

    public ProductData(int id, String product, String detail, int price) {
        this.id = id;
        this.product = product;
        this.detail = detail;
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public String getProduct() {
        return this.product;
    }

    public String getDetail() {
        return this.detail;
    }

    public int getPrice() {
        return this.price;
    }
}
