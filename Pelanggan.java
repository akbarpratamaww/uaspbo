import java.util.ArrayList;
import java.util.List;

public class Pelanggan extends Pengguna {
    private List<Pesanan> pesanan;
    private String alamat;
    private Keanggotaan keanggotaan;

    public Pelanggan(String id, String email, String kataSandi, String nama, String telepon, String alamat) throws KikoopiException {
        super(id, email, kataSandi, nama, telepon);
        if (alamat == null || alamat.trim().isEmpty()) {
            throw new KikoopiException("Alamat tidak boleh kosong!");
        }
        this.alamat = alamat;
        this.pesanan = new ArrayList<>();
        this.keanggotaan = null;
    }

    public void buatPesanan(Pesanan pesanan) {
        this.pesanan.add(pesanan);
    }

    public void tambahUlasan(Ulasan ulasan) {
        // Dikelola oleh PengelolaUlasan
    }

    public List<Pesanan> getRiwayatPesanan() {
        return new ArrayList<>(pesanan);
    }

    public void setKeanggotaan(Keanggotaan keanggotaan) {
        this.keanggotaan = keanggotaan;
    }

    public Keanggotaan getKeanggotaan() {
        return keanggotaan;
    }

    public String getAlamat() {
        return alamat;
    }
}