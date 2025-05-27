import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PengelolaKeanggotaan {
    private List<Keanggotaan> daftarKeanggotaan;

    public PengelolaKeanggotaan() {
        this.daftarKeanggotaan = new ArrayList<>();
        // Inisialisasi keanggotaan berdasarkan KikoopiMemberships.html
        try {
            daftarKeanggotaan.add(new Keanggotaan("M95055", "U001", new Date(), "Active"));
            daftarKeanggotaan.add(new Keanggotaan("M95054", "U002", new Date(), "Active"));
            daftarKeanggotaan.add(new Keanggotaan("M95053", "U003", new Date(), "Active"));
            daftarKeanggotaan.add(new Keanggotaan("M95052", "U004", new Date(), "Active"));
            daftarKeanggotaan.add(new Keanggotaan("M95051", "U005", new Date(), "Active"));
        } catch (KikoopiException e) {
            System.out.println("Gagal inisialisasi keanggotaan: " + e.getMessage());
        }
    }

    public void tambahKeanggotaan(Keanggotaan keanggotaan) {
        daftarKeanggotaan.add(keanggotaan);
    }

    public void perbaruiStatusKeanggotaan(String idPelanggan, String status) throws KikoopiException {
        Keanggotaan keanggotaan = cariKeanggotaan(idPelanggan);
        if (keanggotaan == null) {
            throw new KikoopiException("Keanggotaan tidak ditemukan!");
        }
        keanggotaan.setStatus(status);
    }

    public Keanggotaan cariKeanggotaan(String idPelanggan) {
        return daftarKeanggotaan.stream()
                .filter(k -> k.getIdPelanggan().equals(idPelanggan))
                .findFirst()
                .orElse(null);
    }
}