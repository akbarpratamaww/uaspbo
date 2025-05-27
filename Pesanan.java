import java.util.Date;
import java.util.List;

public class Pesanan {
    private String id;
    private String idPelanggan;
    private List<ItemPesanan> item;
    private double total;
    private String status;
    private Date tanggal;

    public Pesanan(String id, String idPelanggan, List<ItemPesanan> item, Date tanggal) throws KikoopiException {
        if (item == null || item.isEmpty()) {
            throw new KikoopiException("Pesanan harus memiliki setidaknya satu item!");
        }
        this.id = id;
        this.idPelanggan = idPelanggan;
        this.item = item;
        this.status = "Pending";
        this.tanggal = tanggal;
        this.total = hitungTotal();
    }

    public double hitungTotal() {
        return item.stream().mapToDouble(ItemPesanan::getSubtotal).sum();
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) throws KikoopiException {
        if (!status.equals("Pending") && !status.equals("Completed") && !status.equals("Cancelled")) {
            throw new KikoopiException("Status tidak valid! Harus Pending, Completed, atau Cancelled.");
        }
        this.status = status;
    }
}