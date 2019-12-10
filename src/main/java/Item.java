public class Item extends AbstractItem {
    private String item_name;
    private String code;
    private String quantity;
    private String expiration_date;

    public Item(String item_name, String code, String quantity, String expiration_date) {
        this.item_name = item_name;
        this.code = code;
        this.quantity = quantity;
        this.expiration_date = expiration_date;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getExpirationDate() {
        return expiration_date;
    }

    public void setExpirationDate(String expiration_date) {
        this.expiration_date = expiration_date;
    }


    @Override
    public String toString() {
        return "Item [item name=" + item_name + ", code=" + code + ", quantity=" + quantity
                + ", expiration date=" + expiration_date + "]";
    }

}