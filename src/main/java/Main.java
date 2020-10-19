import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    try {
      String payload = new String(Files.readAllBytes(Paths.get("data.json")));
      ShipmentSystem shipmentSystem = new ShipmentSystem(payload);
      List<Transaction> transactions = shipmentSystem.assignShipements();
      double utilization = shipmentSystem.getUtilizationFactor();
      for(Transaction t : transactions) {
        System.out.println(" Slot start - " + t.getSlot().getStart() + ", Slot end - "+ t.getSlot().getEnd() +
            ", Dock Id - " + t.getDockID() + ", Po_Id:"+ t.getOrderID() + ", Item_id - " + t.getItem().getId() +
            ", quantity - " + t.getItem().getQuantity() + "\n");
      }

      System.out.println("Utilization factor - "+ utilization);
    }catch (Exception e){
      System.out.println(e);
    }
  }
}
