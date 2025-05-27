import java.util.ArrayList;
import java.util.List;

public class PengelolaPesanan {
    private List<Pesanan> daftarPesanan;

    public PengelolaPesanan() {
        this.daftarPesanan = new ArrayList<>();
    }

    public void buatPesanan(Pesanan pesanan) {
        daftarPesanan.add(pesanan);
    }

    public Pesanan getPesanan(String id) {
        return daftarPesanan.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void perbaruiStatusPesanan(String id, String status) throws KikoopiException {
        Pesanan pesanan = getPesanan(id);
        if (pesanan == null) {
            throw new KikoopiException("Pesanan tidak ditemukan!");
        }
        pesanan.setStatus(status);
    }

    public void hapusPesanan(String id) {
        daftarPesanan.removeIf(p -> p.getId().equals(id));
    }
}