import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Ulasan {
    private String id;
    private String idPelanggan;
    private int rating;
    private String komentar;
    private Date tanggal;

    public Ulasan(String id, String idPelanggan, int rating, String komentar, Date tanggal) throws KikoopiException {
        if (rating < 1 || rating > 5) {
            throw new KikoopiException("Rating harus antara 1 dan 5!");
        }
        if (komentar == null || komentar.trim().isEmpty()) {
            throw new KikoopiException("Komentar tidak boleh kosong!");
        }
        this.id = id;
        this.idPelanggan = idPelanggan;
        this.rating = rating;
        this.komentar = komentar;
        this.tanggal = tanggal;
    }

    public Map<String, String> getDetail() {
        Map<String, String> detail = new HashMap<>();
        detail.put("id", id);
        detail.put("idPelanggan", idPelanggan);
        detail.put("rating", String.valueOf(rating));
        detail.put("komentar", komentar);
        detail.put("tanggal", tanggal.toString());
        return detail;
    }
}