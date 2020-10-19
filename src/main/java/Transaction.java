public class Transaction {
 private Slot slot;
 private PurchaseItem item;
 private int dockID;
 private int orderID;

  public Slot getSlot() {
    return slot;
  }

  public PurchaseItem getItem() {
    return item;
  }

  public int getDockID() {
    return dockID;
  }

  public int getOrderID() {
    return orderID;
  }

  public Transaction(Slot slot, PurchaseItem item, int dockID, int orderID) {
    this.slot=slot;
    this.dockID = dockID;
    this.orderID = orderID;
    this.item = item;
  }
}
