import com.mongodb.BasicDBObject;

public class Slot {
 private String start;
 private String end;
 private int capacity;
 private PurchaseItem item;

 public Slot(BasicDBObject jsonObject) {
   this.start = jsonObject.getString("start");
   this.end = jsonObject.getString("end");
   this.capacity = jsonObject.getInt("capacity");
 }

 public void assignOrder(PurchaseItem item) {
   this.item = item;
 }

  public int getCapacity(){
    return this.capacity;
  }

  public int getUnusedCapacity() {
   if(item == null){
     return capacity;
   }
   return capacity - item.getQuantity();
  }

  public String getStart() {
    return start;
  }

  public String getEnd() {
    return end;
  }
}
