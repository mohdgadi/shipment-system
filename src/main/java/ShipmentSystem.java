import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ShipmentSystem {
  private List<PurchaseOrder> purchaseOrderList;
  private List<Dock> docks;
  private List<Transaction> transactions;

  public static final String PURCHASE_ORDERS = "purchase_orders";
  public static final String DOCKS = "docks";


  public ShipmentSystem (String payload) {
      purchaseOrderList = new LinkedList<>();
      transactions = new LinkedList<>();
      docks = new LinkedList<>();

      BasicDBObject jsonObject = BasicDBObject.parse(payload);

      BasicDBList purchaseOrders = (BasicDBList) jsonObject.get(PURCHASE_ORDERS);
      for(Object purchaseOrder : purchaseOrders){
        purchaseOrderList.add(new PurchaseOrder((BasicDBObject) purchaseOrder));
      }

      BasicDBList docksList = (BasicDBList) jsonObject.get(DOCKS);
      for(Object dock : docksList){
        docks.add(new Dock((BasicDBObject) dock));
      }

    Collections.sort(docks, Comparator.comparingInt(Dock::getRemainingCapacity));
    Collections.sort(purchaseOrderList, Comparator.comparingInt(PurchaseOrder::getTotalItemsCapacity));
  }

  public List<Transaction> assignShipements() throws ShipmentNotPossibleException {
    if(docks.isEmpty()) {
      throw new ShipmentNotPossibleException("Docks are empty");
    }

    for(PurchaseOrder order : purchaseOrderList) {
      Dock dock = docks.get(0);
      if(dock.remainingCapacity >= order.getTotalItemsCapacity() && dock.getSlotCapacity() >= order.getMaxItemSize()) {
        List<Transaction> assignedTransactions = dock.dockPurchaseOrder(order);
        transactions.addAll(assignedTransactions);
      }
    }
    return transactions;
  }

  public double getUtilizationFactor() {
    int denominator = docks.size();

//    Calculate for docks
    double usedFactor = 0;
    for(Dock usedDock : docks) {
      usedFactor += (double) usedDock.getCummulativeUnusedCapacity() / usedDock.initialTotalCapacity;
    }

    return usedFactor/denominator;
  }
}
