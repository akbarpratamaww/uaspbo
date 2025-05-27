import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PengelolaProduk {
    private List<Produk> daftarProduk;

    public PengelolaProduk() {
        this.daftarProduk = new ArrayList<>();
        // Inisialisasi produk berdasarkan Kikoopi.html
        try {
            daftarProduk.add(new Produk("P001", "Chicken Noodle Bowl", 32000, "Mie dengan potongan ayam panggang dan sayur selada, disajikan dengan saus gurih khas Asia.", "Makanan"));
            daftarProduk.add(new Produk("P002", "Ice Cappuccino", 20000, "Perpaduan espresso dan susu segar dengan rasa creamy yang ringan dan menyegarkan.", "Minuman"));
            daftarProduk.add(new Produk("P003", "Frappe Choco Cream", 25000, "Minuman dingin cokelat dengan krim di atasnya dan taburan bubuk cokelat.", "Minuman"));
            daftarProduk.add(new Produk("P004", "Ice Americano", 20000, "Kopi hitam dingin yang disajikan dengan es batu, punya rasa kuat dan segar tanpa tambahan susu atau gula.", "Minuman"));
            daftarProduk.add(new Produk("P005", "Ice Coffee Latte", 20000, "Kopi susu dingin dengan es batu, segar dan ringan untuk dinikmati kapan saja.", "Minuman"));
            daftarProduk.add(new Produk("P006", "Iced Matcha Latte", 28000, "Minuman dingin perpaduan bubuk matcha dan susu, berwarna hijau lembut dengan rasa manis dan sedikit pahit yang khas.", "Minuman"));
            daftarProduk.add(new Produk("P007", "Iced Vanilla Frappe", 27000, "Minuman dingin Vanilla dengan krim di atasnya dan taburan bubuk Vanilla.", "Minuman"));
            daftarProduk.add(new Produk("P008", "French Fries", 18000, "French fries yang renyah dan गुरih, sempurna untuk menemani hari Anda.", "Makanan"));
            daftarProduk.add(new Produk("P009", "Chips", 15000, "Chips gurih dan renyah, camilan sempurna untuk setiap saat.", "Makanan"));
            daftarProduk.add(new Produk("P010", "Cheese Cake", 23000, "Cheesecake lembut dan manis, nikmat di setiap gigitan.", "Makanan"));
        } catch (KikoopiException e) {
            System.out.println("Gagal inisialisasi produk: " + e.getMessage());
        }
    }

    public void tambahProduk(Produk produk) {
        daftarProduk.add(produk);
    }

    public List<Produk> cariProduk(String kataKunci) {
        if (kataKunci == null || kataKunci.trim().isEmpty()) {
            return new ArrayList<>(daftarProduk);
        }
        return daftarProduk.stream()
                .filter(p -> p.getDetail().get("nama").toLowerCase().contains(kataKunci.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void perbaruiProduk(Produk produk) {
        for (int i = 0; i < daftarProduk.size(); i++) {
            if (daftarProduk.get(i).getDetail().get("id").equals(produk.getDetail().get("id"))) {
                daftarProduk.set(i, produk);
                break;
            }
        }
    }

    public void hapusProduk(String id) {
        daftarProduk.removeIf(p -> p.getDetail().get("id").equals(id));
    }
}