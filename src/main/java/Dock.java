import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import java.util.LinkedList;
import java.util.List;

public class Dock {
 private int id;
 List<Slot> slots = new LinkedList<>();
 List<Slot> usedSlots = new LinkedList<>();
 int remainingCapacity;
 int initialTotalCapacity;

  public Dock(BasicDBObject jsonObject) {
   this.id = jsonObject.getInt("ID");
   BasicDBList slotsList = (BasicDBList) jsonObject.get("slots");
   for(Object slotObj : slotsList) {
     slots.add(new Slot((BasicDBObject) slotObj));
   }

   calculateRemainingCapacity();
   this.initialTotalCapacity = remainingCapacity;
  }

  public int getRemainingCapacity(){
    return remainingCapacity;
  }

  private void calculateRemainingCapacity() {
    int total = 0;
    for(Slot s : slots) {
      total += s.getCapacity();
    }
    this.remainingCapacity = total;
  }

  public int getSlotCapacity () {
   if(slots.size() == 0) {
     return 0;
   }

   return slots.get(0).getCapacity();
  }

  public List<Transaction> dockPurchaseOrder(PurchaseOrder order) {
   List<Transaction> transactions = new LinkedList<>();

   for(PurchaseItem item : order.purchaseItems) {
    Transaction t = new Transaction(slots.get(0), item, id, order.id);
    Slot firstSlot = slots.remove(0);
    firstSlot.assignOrder(item);
    usedSlots.add(firstSlot);
    transactions.add(t);
   }

   calculateRemainingCapacity();
   return transactions;
  }

  public int getCummulativeUnusedCapacity() {
    int total = 0;

//    For unused slots
    if(!slots.isEmpty()) {
      total += slots.size() * slots.get(0).getCapacity();
    }

// For used slots
    for(Slot s : usedSlots){
      total += s.getUnusedCapacity();
    }

    return total;
  }
}
