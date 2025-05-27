import java.util.HashMap;
import java.util.Map;

public abstract class Pengguna {
    protected String id;
    protected String email;
    protected String kataSandi;
    protected String nama;
    protected String telepon;

    public Pengguna(String id, String email, String kataSandi, String nama, String telepon) throws KikoopiException {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new KikoopiException("Email tidak valid!");
        }
        if (kataSandi == null || kataSandi.length() < 6) {
            throw new KikoopiException("Kata sandi harus minimal 6 karakter!");
        }
        this.id = id;
        this.email = email;
        this.kataSandi = kataSandi;
        this.nama = nama;
        this.telepon = telepon;
    }

    public boolean autentikasi(String email, String kataSandi) {
        return this.email.equals(email) && this.kataSandi.equals(kataSandi);
    }

    public Map<String, String> getProfil() {
        Map<String, String> profil = new HashMap<>();
        profil.put("id", id);
        profil.put("email", email);
        profil.put("nama", nama);
        profil.put("telepon", telepon);
        return profil;
    }

    public void updateProfil(Map<String, String> data) throws KikoopiException {
        if (data.containsKey("email") && !data.get("email").matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new KikoopiException("Email baru tidak valid!");
        }
        if (data.containsKey("kataSandi") && data.get("kataSandi").length() < 6) {
            throw new KikoopiException("Kata sandi baru harus minimal 6 karakter!");
        }
        this.email = data.getOrDefault("email", email);
        this.nama = data.getOrDefault("nama", nama);
        this.telepon = data.getOrDefault("telepon", telepon);
        if (data.containsKey("kataSandi")) {
            this.kataSandi = data.get("kataSandi");
        }
    }
}