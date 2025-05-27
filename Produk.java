import java.util.HashMap;
import java.util.Map;

public class Produk {
    private String id;
    private String nama;
    private double harga;
    private String deskripsi;
    private String kategori;

    public Produk(String id, String nama, double harga, String deskripsi, String kategori) throws KikoopiException {
        if (nama == null || nama.trim().isEmpty()) {
            throw new KikoopiException("Nama produk tidak boleh kosong!");
        }
        if (harga <= 0) {
            throw new KikoopiException("Harga harus lebih dari 0!");
        }
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.kategori = kategori;
    }

    public Map<String, String> getDetail() {
        Map<String, String> detail = new HashMap<>();
        detail.put("id", id);
        detail.put("nama", nama);
        detail.put("harga", String.valueOf(harga));
        detail.put("deskripsi", deskripsi);
        detail.put("kategori", kategori);
        return detail;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }
}