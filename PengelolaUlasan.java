import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PengelolaUlasan {
    private List<Ulasan> daftarUlasan;

    public PengelolaUlasan() {
        this.daftarUlasan = new ArrayList<>();
        // Inisialisasi ulasan berdasarkan kikoopiReview.html
        try {
            daftarUlasan.add(new Ulasan("R001", "U001", 5, "Kopinya enak", new Date()));
            daftarUlasan.add(new Ulasan("R002", "U002", 5, "Saya suka matchanya!", new Date()));
            daftarUlasan.add(new Ulasan("R003", "U003", 5, "Enaak banget tempatnyaa!", new Date()));
            daftarUlasan.add(new Ulasan("R004", "U004", 5, "Saya suka baristanya", new Date()));
        } catch (KikoopiException e) {
            System.out.println("Gagal inisialisasi ulasan: " + e.getMessage());
        }
    }

    public void tambahUlasan(Ulasan ulasan) {
        daftarUlasan.add(ulasan);
    }

    public List<Ulasan> cariUlasanByKomentar(String kataKunci) {
        if (kataKunci == null || kataKunci.trim().isEmpty()) {
            return new ArrayList<>(daftarUlasan);
        }
        return daftarUlasan.stream()
                .filter(u -> u.getDetail().get("komentar").toLowerCase().contains(kataKunci.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void hapusUlasan(String id) {
        daftarUlasan.removeIf(u -> u.getDetail().get("id").equals(id));
    }
}