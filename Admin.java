public class Admin extends Pengguna {
    private String peran;

    public Admin(String id, String email, String kataSandi, String nama, String telepon, String peran) throws KikoopiException {
        super(id, email, kataSandi, nama, telepon);
        if (peran == null || peran.trim().isEmpty()) {
            throw new KikoopiException("Peran tidak boleh kosong!");
        }
        this.peran = peran;
    }

    public void kelolaInventaris(Produk produk) {
        System.out.println("Mengupdate inventaris untuk produk: " + produk.getDetail().get("nama"));
    }

    public void kelolaKeanggotaan(Keanggotaan keanggotaan) {
        System.out.println("Mengupdate keanggotaan untuk pelanggan ID: " + keanggotaan.getIdPelanggan());
    }

    public String buatLaporan() {
        return "Laporan dihasilkan oleh Admin: " + nama + " (Peran: " + peran + ")";
    }
}