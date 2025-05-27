import java.util.*;
import java.util.stream.Collectors;

public class KikoopiApp {
    private static PengelolaPengguna pengelolaPengguna = new PengelolaPengguna();
    private static PengelolaProduk pengelolaProduk = new PengelolaProduk();
    private static PengelolaPesanan pengelolaPesanan = new PengelolaPesanan();
    private static PengelolaUlasan pengelolaUlasan = new PengelolaUlasan();
    private static PengelolaKeanggotaan pengelolaKeanggotaan = new PengelolaKeanggotaan();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Pesan selamat dalam tabel
        System.out.println("┌───────────────────────────────────────────────────┐");
        System.out.println("│            Kikoopi Coffee Shop                    │");
        System.out.println("├───────────────────────────────────────────────────┤");
        System.out.println("│ Alamat  : Gedung Graha Ganesha, Jl. Hayam Wuruk  │");
        System.out.println("│           No.28, Denpasar, Bali                   │");
        System.out.println("│ Telepon : 0896-0211-3245                         │");
        System.out.println("│ Email   : info@kikoopi.com                       │");
        System.out.println("└───────────────────────────────────────────────────┘");

        while (true) {
            // Menu utama dalam tabel
            System.out.println("\n┌────────────────── Menu Utama ───────────────────┐");
            System.out.println("│ No │ Pilihan                                  │");
            System.out.println("├────┬──────────────────────────────────────────┤");
            System.out.println("│ 1  │ Login                                    │");
            System.out.println("│ 2  │ Registrasi                               │");
            System.out.println("│ 3  │ Keluar                                   │");
            System.out.println("└────┴──────────────────────────────────────────┘");
            System.out.print("Pilih opsi (1-3): ");
            try {
                int pilihan = scanner.nextInt();
                scanner.nextLine();
                switch (pilihan) {
                    case 1:
                        login();
                        break;
                    case 2:
                        registrasi();
                        break;
                    case 3:
                        System.out.println("\n┌──────────────────────────────────────────────┐");
                        System.out.println("│ Terima kasih telah mengunjungi Kikoopi!   │");
                        System.out.println("└──────────────────────────────────────────────┘");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Pilihan tidak valid! Silakan pilih 1-3!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Masukkan harus berupa angka!");
                scanner.nextLine();
            }
        }
    }

    private static void login() {
        System.out.println("\n┌───────────────── Form Login ──────────────────┐");
        System.out.println("│ Email          :                              │");
        System.out.println("│ Kata Sandi     :                              │");
        System.out.println("└───────────────────────────────────────────────┘");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Kata Sandi: ");
        String kataSandi = scanner.nextLine();

        Pengguna pengguna = pengelolaPengguna.autentikasi(email, kataSandi);
        if (pengguna != null) {
            System.out.println("\nLogin berhasil! Selamat datang, " + pengguna.getProfil().get("nama"));
            if (pengguna instanceof Pelanggan) {
                menuPelanggan((Pelanggan) pengguna);
            } else if (pengguna instanceof Admin) {
                menuAdmin((Admin) pengguna);
            }
        } else {
            System.out.println("Email atau kata sandi salah!");
        }
    }

    private static void registrasi() {
        try {
            System.out.println("\n┌────────────── Form Registrasi ──────────────┐");
            System.out.println("│ Nama           :                           │");
            System.out.println("│ Email          :                           │");
            System.out.println("│ Kata Sandi     : (min 6 karakter)         │");
            System.out.println("│ Telepon        :                           │");
            System.out.println("│ Alamat         :                           │");
            System.out.println("└────────────────────────────────────────────┘");
            System.out.print("Nama: ");
            String nama = scanner.nextLine();
            if (nama.trim().isEmpty()) {
                throw new KikoopiException("Nama tidak boleh kosong!");
            }
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Kata Sandi: ");
            String kataSandi = scanner.nextLine();
            System.out.print("Telepon: ");
            String telepon = scanner.nextLine();
            System.out.print("Alamat: ");
            String alamat = scanner.nextLine();

            Pelanggan pelanggan = new Pelanggan(
                    UUID.randomUUID().toString(), email, kataSandi, nama, telepon, alamat);
            pengelolaPengguna.tambahPengguna(pelanggan);
            System.out.println("\nRegistrasi berhasil! Silakan login dengan email: " + email);
        } catch (KikoopiException e) {
            System.out.println("Gagal registrasi: " + e.getMessage());
        }
    }

    private static void menuPelanggan(Pelanggan pelanggan) {
        while (true) {
            // Menu pelanggan dalam tabel
            System.out.println("\n┌────────────── Menu Pelanggan ──────────────┐");
            System.out.println("│ No │ Pilihan                               │");
            System.out.println("├────┬──────────────────────────────────────┘─┤");
            System.out.println("│ 1  │ Cari Produk                            │");
            System.out.println("│ 2  │ Buat Pesanan                            │");
            System.out.println("│ 3  │ Lihat Riwayat Pesanan                  │");
            System.out.println("│ 4  │ Tambah Ulasan                          │");
            System.out.println("│ 5  │ Daftar Keanggotaan                     │");
            System.out.println("│ 6  │ Update Profil                          │");
            System.out.println("│ 7  │ Logout                                 │");
            System.out.println("└────┴────────────────────────────────────────┘");
            System.out.print("Pilih opsi (1-7): ");
            try {
                int pilihan = scanner.nextInt();
                scanner.nextLine();
                switch (pilihan) {
                    case 1:
                        cariProduk();
                        break;
                    case 2:
                        buatPesanan(pelanggan);
                        break;
                    case 3:
                        lihatRiwayatPesanan(pelanggan);
                        break;
                    case 4:
                        tambahUlasan(pelanggan);
                        break;
                    case 5:
                        daftarKeanggotaan(pelanggan);
                        break;
                    case 6:
                        updateProfil(pelanggan);
                        break;
                    case 7:
                        System.out.println("\nLogout berhasil!");
                        return;
                    default:
                        System.out.println("Pilihan tidak valid! Silakan pilih 1-7.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Masukkan harus berupa angka!");
                scanner.nextLine();
            }
        }
    }

    private static void cariProduk() {
        System.out.println("\n┌──────────── Form Cari Produk ─────────────┐┘");
        System.out.println("│ Kata Kunci   : (kosongkan untuk semua)    │");
        System.out.println("└────────────────────────────────────────────┘");
        System.out.print("Masukkan kata kunci: ");
        String kataKunci = scanner.nextLine();
        List<Produk> hasil = pengelolaProduk.cariProduk(kataKunci);
        if (hasil.isEmpty()) {
            System.out.println("Tidak ada produk ditemukan!");
        } else {
            System.out.println("\nDaftar Produk:");
            hasil.forEach(p -> System.out.println(
                    "Nama: " + p.getDetail().get("nama") +
                    ", Harga: Rp" + p.getDetail().get("harga") +
                    ", Deskripsi: " + p.getDetail().get("deskripsi") +
                    ", Kategori: " + p.getDetail().get("kategori")));
        }
    }

    private static void buatPesanan(Pelanggan pelanggan) {
        try {
            List<ItemPesanan> items = new ArrayList<>();
            System.out.println("\nDaftar produk tersedia:");
            List<Produk> semuaProduk = pengelolaProduk.cariProduk("");
            if (semuaProduk.isEmpty()) {
                System.out.println("Belum ada produk tersedia!");
                return;
            }
            semuaProduk.forEach(p -> System.out.println(
                    p.getDetail().get("nama") + " (Rp" + p.getDetail().get("harga") + ")"));
            while (true) {
                System.out.println("\n┌────────── Form Tambah Item Pesanan ────────┐┘");
                System.out.println("│ Nama Produk : (ketik 'selesai' untuk akhiri)   │");
                System.out.println("│ Kuantitas   :                             │");
                System.out.println("└────────────────────────────────────────────┘");
                System.out.print("Masukkan nama produk: ");
                String namaProduk = scanner.nextLine();
                if (namaProduk.equalsIgnoreCase("selesai")) {
                    break;
                }
                System.out.print("Kuantitas: ");
                try {
                    int kuantitas = scanner.nextInt();
                    scanner.nextLine();
                    List<Produk> hasil = pengelolaProduk.cariProduk(namaProduk);
                    if (!hasil.isEmpty()) {
                        items.add(new ItemPesanan(hasil.get(0), kuantitas));
                        System.out.println("Ditambahkan: " + namaProduk + " x" + kuantitas);
                    } else {
                        System.out.println("Produk '" + namaProduk + "' tidak ditemukan!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Kuantitas harus berupa angka!");
                    scanner.nextLine();
                } catch (KikoopiException e) {
                    System.out.println("Gagal menambah item: " + e.getMessage());
                }
            }
            if (!items.isEmpty()) {
                Pesanan pesanan = new Pesanan(UUID.randomUUID().toString(), pelanggan.getProfil().get("id"), items, new Date());
                pengelolaPesanan.buatPesanan(pesanan);
                pelanggan.buatPesanan(pesanan);
                System.out.println("\nPesanan dibuat dengan total: Rp" + pesanan.hitungTotal());
            } else {
                System.out.println("\nTidak ada item yang ditambahkan ke pesanan!");
            }
        } catch (KikoopiException e) {
            System.out.println("Gagal membuat pesanan: " + e.getMessage());
        }
    }

    private static void lihatRiwayatPesanan(Pelanggan pelanggan) {
        List<Pesanan> pesanan = pelanggan.getRiwayatPesanan();
        if (pesanan.isEmpty()) {
            System.out.println("\nBelum ada pesanan!");
        } else {
            System.out.println("\nRiwayat Pesanan:");
            pesanan.forEach(p -> System.out.println(
                    "Pesanan ID: " + p.getId() +
                    ", Total: Rp" + p.hitungTotal() +
                    ", Status: " + p.getStatus()));
        }
    }

    private static void tambahUlasan(Pelanggan pelanggan) {
        try {
            System.out.println("\n┌──────────── Form Tambah Ulasan ────────────┐┘");
            System.out.println("│ Rating       : (1-5)                      │");
            System.out.println("│ Komentar     :                            │");
            System.out.println("└────────────────────────────────────────────┘");
            System.out.print("Rating (1-5): ");
            int rating = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Komentar: ");
            String komentar = scanner.nextLine();
            Ulasan ulasan = new Ulasan(UUID.randomUUID().toString(), pelanggan.getProfil().get("id"), rating, komentar, new Date());
            pengelolaUlasan.tambahUlasan(ulasan);
            pelanggan.tambahUlasan(ulasan);
            System.out.println("\nUlasan berhasil ditambahkan!");
        } catch (InputMismatchException e) {
            System.out.println("Rating harus berupa angka!");
            scanner.nextLine();
        } catch (KikoopiException e) {
            System.out.println("Gagal menambah ulasan: " + e.getMessage());
        }
    }

    private static void daftarKeanggotaan(Pelanggan pelanggan) {
        try {
            if (pelanggan.getKeanggotaan() != null) {
                System.out.println("\nAnda sudah terdaftar sebagai anggota dengan status: " + pelanggan.getKeanggotaan().getStatus());
                return;
            }
            Keanggotaan keanggotaan = new Keanggotaan(UUID.randomUUID().toString(), pelanggan.getProfil().get("id"), new Date(), "Active");
            pengelolaKeanggotaan.tambahKeanggotaan(keanggotaan);
            pelanggan.setKeanggotaan(keanggotaan);
            System.out.println("\nKeanggotaan berhasil didaftarkan! ID: " + keanggotaan.getIdPelanggan());
        } catch (KikoopiException e) {
            System.out.println("Gagal mendaftar keanggotaan: " + e.getMessage());
        }
    }

    private static void updateProfil(Pelanggan pelanggan) {
        try {
            System.out.println("\n┌──────────── Form Update Profil ─────────────┐");
            System.out.println("│ Nama         : (kosongkan jika tidak diubah)  │");
            System.out.println("│ Email        : (kosongkan jika tidak diubah)  │");
            System.out.println("│ Telepon      : (kosongkan jika tidak diubah)  │");
            System.out.println("│ Kata Sandi   : (kosongkan jika tidak diubah)  │");
            System.out.println("│ Alamat       : (kosongkan jika tidak diubah)  │");
            System.out.println("└──────────────────────────────────────────────┘");
            Map<String, String> profilBaru = new HashMap<>();
            System.out.print("Nama baru: ");
            String nama = scanner.nextLine();
            if (!nama.isEmpty()) profilBaru.put("nama", nama);
            System.out.print("Email baru: ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                if (pengelolaPengguna.cariPengguna(email) != null) {
                    System.out.println("Email sudah terdaftar!");
                    return;
                }
                profilBaru.put("email", email);
            }
            System.out.print("Telepon baru: ");
            String telepon = scanner.nextLine();
            if (!telepon.isEmpty()) profilBaru.put("telepon", telepon);
            System.out.print("Kata sandi baru: ");
            String kataSandi = scanner.nextLine();
            if (!kataSandi.isEmpty()) profilBaru.put("kataSandi", kataSandi);
            System.out.print("Alamat baru: ");
            String alamat = scanner.nextLine();
            if (!alamat.isEmpty()) {
                if (alamat.trim().isEmpty()) {
                    throw new KikoopiException("Alamat tidak boleh kosong!");
                }
                profilBaru.put("alamat", alamat);
            }
            pelanggan.updateProfil(profilBaru);
            pengelolaPengguna.perbaruiPengguna(pelanggan);
            System.out.println("\nProfil berhasil diperbarui!");
        } catch (KikoopiException e) {
            System.out.println("Gagal memperbarui profil: " + e.getMessage());
        }
    }

    private static void menuAdmin(Admin admin) {
        while (true) {
            // Menu admin dalam tabel
            System.out.println("\n┌─────────────── Menu Admin ────────────────┐");
            System.out.println("│ No │ Pilihan                               │");
            System.out.println("├────┬──────────────────────────────────────┘─┤");
            System.out.println("│ 1  │ Tambah Produk                         │");
            System.out.println("│ 2  │ Update Produk                         │");
            System.out.println("│ 3  │ Hapus Produk                          │");
            System.out.println("│ 4  │ Kelola Keanggotaan                    │");
            System.out.println("│ 5  │ Lihat Ulasan                          │");
            System.out.println("│ 6  │ Buat Laporan                          │");
            System.out.println("│ 7  │ Logout                                │");
            System.out.println("└────┴────────────────────────────────────────┘");
            System.out.print("Pilih opsi (1-7): ");
            try {
                int pilihan = scanner.nextInt();
                scanner.nextLine();
                switch (pilihan) {
                    case 1:
                        tambahProduk(admin);
                        break;
                    case 2:
                        updateProduk(admin);
                        break;
                    case 3:
                        hapusProduk();
                        break;
                    case 4:
                        kelolaKeanggotaan(admin);
                        break;
                    case 5:
                        lihatUlasan();
                        break;
                    case 6:
                        System.out.println("\n" + admin.buatLaporan());
                        break;
                    case 7:
                        System.out.println("\nLogout berhasil!");
                        return;
                    default:
                        System.out.println("Pilihan tidak valid! Silakan pilih 1-7.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Masukkan harus berupa angka!");
                scanner.nextLine();
            }
        }
    }

    private static void tambahProduk(Admin admin) {
        try {
            System.out.println("\n┌──────────── Form Tambah Produk ────────────┐┘");
            System.out.println("│ Nama Produk  :                            │");
            System.out.println("│ Harga        :                            │");
            System.out.println("│ Deskripsi    :                            │");
            System.out.println("│ Kategori     : (Makanan/Minuman)          │");
            System.out.println("└────────────────────────────────────────────┘");
            System.out.print("Nama Produk: ");
            String nama = scanner.nextLine();
            System.out.print("Harga: ");
            double harga = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Deskripsi: ");
            String deskripsi = scanner.nextLine();
            System.out.print("Kategori: ");
            String kategori = scanner.nextLine();
            Produk produk = new Produk(UUID.randomUUID().toString(), nama, harga, deskripsi, kategori);
            pengelolaProduk.tambahProduk(produk);
            admin.kelolaInventaris(produk);
            System.out.println("\nProduk berhasil ditambahkan!");
        } catch (InputMismatchException e) {
            System.out.println("Harga harus berupa angka!");
            scanner.nextLine();
        } catch (KikoopiException e) {
            System.out.println("Gagal menambah produk: " + e.getMessage());
        }
    }

    private static void updateProduk(Admin admin) {
        try {
            System.out.println("\n┌──────────── Form Update Produk ────────────┐┘");
            System.out.println("│ Nama Produk  : (produk yang akan diupdate) │");
            System.out.println("└────────────────────────────────────────────┘");
            System.out.print("Nama produk: ");
            String namaProduk = scanner.nextLine();
            List<Produk> hasil = pengelolaProduk.cariProduk(namaProduk);
            if (!hasil.isEmpty()) {
                System.out.println("\n┌──────────── Form Update Produk ────────────┐┘");
                System.out.println("│ Nama Baru    : (kosongkan jika tidak diubah)  │");
                System.out.println("│ Harga Baru   : (0 jika tidak diubah)      │");
                System.out.println("│ Deskripsi    : (kosongkan jika tidak diubah)  │");
                System.out.println("│ Kategori     : (kosongkan jika tidak diubah)  │");
                System.out.println("└────────────────────────────────────────────┘");
                Produk produkLama = hasil.get(0);
                System.out.print("Nama baru: ");
                String namaBaru = scanner.nextLine();
                System.out.print("Harga baru: ");
                double hargaBaru = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Deskripsi baru: ");
                String deskripsiBaru = scanner.nextLine();
                System.out.print("Kategori baru: ");
                String kategoriBaru = scanner.nextLine();
                Produk produkBaru = new Produk(
                        produkLama.getDetail().get("id"),
                        namaBaru.isEmpty() ? produkLama.getNama() : namaBaru,
                        hargaBaru == 0 ? produkLama.getHarga() : hargaBaru,
                        deskripsiBaru.isEmpty() ? produkLama.getDetail().get("deskripsi") : deskripsiBaru,
                        kategoriBaru.isEmpty() ? produkLama.getDetail().get("kategori") : kategoriBaru
                );
                pengelolaProduk.perbaruiProduk(produkBaru);
                admin.kelolaInventaris(produkBaru);
                System.out.println("\nProduk berhasil diperbarui!");
            } else {
                System.out.println("Produk '" + namaProduk + "' tidak ditemukan!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Harga harus berupa angka!");
            scanner.nextLine();
        } catch (KikoopiException e) {
            System.out.println("Gagal memperbarui produk: " + e.getMessage());
        }
    }

    private static void hapusProduk() {
        System.out.println("\n┌──────────── Form Hapus Produk ─────────────┐┘");
        System.out.println("│ Nama Produk  :                            │");
        System.out.println("└────────────────────────────────────────────┘");
        System.out.print("Nama Produk: ");
        String namaHapus = scanner.nextLine();
        List<Produk> hasil = pengelolaProduk.cariProduk(namaHapus);
        if (!hasil.isEmpty()) {
            pengelolaProduk.hapusProduk(hasil.get(0).getDetail().get("id"));
            System.out.println("\nProduk berhasil dihapus!");
        } else {
            System.out.println("Produk '" + namaHapus + "' tidak ditemukan!");
        }
    }

    private static void kelolaKeanggotaan(Admin admin) {
        try {
            System.out.println("\n┌───────── Form Kelola Keanggotaan ─────────┐┘");
            System.out.println("│ ID Pelanggan :                            │");
            System.out.println("│ Status Baru  : (Active/Inactive)          │");
            System.out.println("└────────────────────────────────────────────┘");
            System.out.print("ID Pelanggan: ");
            String idPelanggan = scanner.nextLine();
            Keanggotaan keanggotaan = pengelolaKeanggotaan.cariKeanggotaan(idPelanggan);
            if (keanggotaan != null) {
                System.out.print("Status baru: ");
                String status = scanner.nextLine();
                pengelolaKeanggotaan.perbaruiStatusKeanggotaan(idPelanggan, status);
                admin.kelolaKeanggotaan(keanggotaan);
                System.out.println("\nStatus keanggotaan berhasil diperbarui!");
            } else {
                System.out.println("Keanggotaan untuk ID '" + idPelanggan + "' tidak ditemukan!");
            }
        } catch (KikoopiException e) {
            System.out.println("Gagal mengelola keanggotaan: " + e.getMessage());
        }
    }

    private static void lihatUlasan() {
        System.out.println("\n┌──────────── Form Cari Ulasan ─────────────┐┘");
        System.out.println("│ Kata Kunci   : (kosongkan untuk semua)    │");
        System.out.println("└────────────────────────────────────────────┘");
        System.out.print("Masukkan kata kunci: ");
        String kataKunci = scanner.nextLine();
        List<Ulasan> ulasan = pengelolaUlasan.cariUlasanByKomentar(kataKunci);
        if (ulasan.isEmpty()) {
            System.out.println("Tidak ada ulasan ditemukan!");
        } else {
            System.out.println("\nDaftar Ulasan:");
            ulasan.forEach(u -> System.out.println(
                    "Pelanggan ID: " + u.getDetail().get("idPelanggan") +
                    ", Rating: " + u.getDetail().get("rating") +
                    ", Komentar: " + u.getDetail().get("komentar") +
                    ", Tanggal: " + u.getDetail().get("tanggal")));
        }
    }
}