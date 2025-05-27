import java.util.ArrayList;
import java.util.List;

public class PengelolaPengguna {
    private List<Pengguna> daftarPengguna;

    public PengelolaPengguna() {
        this.daftarPengguna = new ArrayList<>();
        try {
            // Inisialisasi admin default
            daftarPengguna.add(new Admin("ADM001", "admin@kikoopi.com", "admin123", "Admin Kikoopi", "089602113245", "Manager"));
        } catch (KikoopiException e) {
            System.out.println("Gagal inisialisasi admin: " + e.getMessage());
        }
    }

    public void tambahPengguna(Pengguna pengguna) throws KikoopiException {
        if (cariPengguna(pengguna.getProfil().get("email")) != null) {
            throw new KikoopiException("Email sudah terdaftar!");
        }
        daftarPengguna.add(pengguna);
    }

    public Pengguna cariPengguna(String email) {
        return daftarPengguna.stream()
                .filter(p -> p.getProfil().get("email").equals(email))
                .findFirst()
                .orElse(null);
    }

    public void perbaruiPengguna(Pengguna pengguna) {
        for (int i = 0; i < daftarPengguna.size(); i++) {
            if (daftarPengguna.get(i).getProfil().get("id").equals(pengguna.getProfil().get("id"))) {
                daftarPengguna.set(i, pengguna);
                break;
            }
        }
    }

    public void hapusPengguna(String id) {
        daftarPengguna.removeIf(p -> p.getProfil().get("id").equals(id));
    }

    public Pengguna autentikasi(String email, String kataSandi) {
        return daftarPengguna.stream()
                .filter(p -> p.autentikasi(email, kataSandi))
                .findFirst()
                .orElse(null);
    }
}