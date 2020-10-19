import com.mongodb.BasicDBObject;

public class PurchaseItem {

  private int id;
  private int quantity;

  public PurchaseItem(int id, int quantity) {
    this.id = id;
    this.quantity = quantity;
  }

  public PurchaseItem(BasicDBObject jsonObject) {
    this.id = jsonObject.getInt("ID");
    this.quantity = jsonObject.getInt("quantity");
  }

  public int getId() {
    return id;
  }

  public int getQuantity() {
    return quantity;
  }
}
