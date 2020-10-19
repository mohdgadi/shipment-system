import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import java.util.LinkedList;
import java.util.List;

public class PurchaseOrder {
  public static final String PURCHASE_ITEMS = "purchase_items";

  int id;
  List<PurchaseItem> purchaseItems;

 private int maxItemSize;

  public int getMaxItemSize() {
    return maxItemSize;
  }

  private int totalItemsCapacity;

  public PurchaseOrder(BasicDBObject jsonObject) {
    BasicDBList purchaseItemsList = (BasicDBList) jsonObject.get(PURCHASE_ITEMS);
    List<PurchaseItem> items = new LinkedList<PurchaseItem>();
    for(Object item : purchaseItemsList) {
      items.add(new PurchaseItem((BasicDBObject) item));
    }

    this.id = jsonObject.getInt("ID");
    this.purchaseItems = items;
    calculateCapacities();
  }

  public int getTotalItemsCapacity(){
    return totalItemsCapacity;
  }

  private void calculateCapacities() {
    int totalCapacity = 0;
    int maxSize = 0;
    for(PurchaseItem item : purchaseItems){
      totalCapacity += item.getQuantity();
      maxSize = Math.max(maxSize, item.getQuantity());
    }

    this.totalItemsCapacity = totalCapacity;
    this.maxItemSize = maxSize;
  }
}

