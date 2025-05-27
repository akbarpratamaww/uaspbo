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
        while (true) {
            System.out.println("\n=== Kikoopi Coffee Shop ===");
            System.out.println("1. Login");
            System.out.println("2. Registrasi");
            System.out.println("3. Keluar");
            System.out.print("Pilih opsi: ");
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
                        System.out.println("Terima kasih telah mengunjungi Kikoopi!");
                        return;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Masukkan harus berupa angka!");
                scanner.nextLine();
            }
        }
    }

    private static void login() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Kata Sandi: ");
        String kataSandi = scanner.nextLine();

        Pengguna pengguna = pengelolaPengguna.autentikasi(email, kataSandi);
        if (pengguna != null) {
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
            System.out.print("Nama: ");
            String nama = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Kata Sandi (min 6 karakter): ");
            String kataSandi = scanner.nextLine();
            System.out.print("Telepon: ");
            String telepon = scanner.nextLine();
            System.out.print("Alamat: ");
            String alamat = scanner.nextLine();

            Pelanggan pelanggan = new Pelanggan(
                    UUID.randomUUID().toString(), email, kataSandi, nama, telepon, alamat);
            pengelolaPengguna.tambahPengguna(pelanggan);
            System.out.println("Registrasi berhasil! Silakan login.");
        } catch (KikoopiException e) {
            System.out.println("Gagal registrasi: " + e.getMessage());
        }
    }

    private static void menuPelanggan(Pelanggan pelanggan) {
        while (true) {
            System.out.println("\n=== Menu Pelanggan ===");
            System.out.println("1. Cari Produk");
            System.out.println("2. Buat Pesanan");
            System.out.println("3. Lihat Riwayat Pesanan");
            System.out.println("4. Tambah Ulasan");
            System.out.println("5. Daftar Keanggotaan");
            System.out.println("6. Update Profil");
            System.out.println("7. Logout");
            System.out.print("Pilih opsi: ");
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
                        return;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Masukkan harus berupa angka!");
                scanner.nextLine();
            }
        }
    }

    private static void cariProduk() {
        System.out.print("Masukkan kata kunci (kosongkan untuk melihat semua produk): ");
        String kataKunci = scanner.nextLine();
        List<Produk> hasil = pengelolaProduk.cariProduk(kataKunci);
        if (hasil.isEmpty()) {
            System.out.println("Tidak ada produk ditemukan!");
        } else {
            hasil.forEach(p -> System.out.println("Nama: " + p.getDetail().get("nama") + ", Harga: Rp" + p.getDetail().get("harga") + ", Deskripsi: " + p.getDetail().get("deskripsi")));
        }
    }

    private static void buatPesanan(Pelanggan pelanggan) {
        try {
            List<ItemPesanan> items = new ArrayList<>();
            System.out.println("Daftar produk tersedia:");
            pengelolaProduk.cariProduk("").forEach(p -> System.out.println(p.getDetail().get("nama")));
            while (true) {
                System.out.print("Masukkan nama produk (atau 'selesai' untuk mengakhiri): ");
                String namaProduk = scanner.nextLine();
                if (namaProduk.equalsIgnoreCase("selesai")) break;
                System.out.print("Kuantitas: ");
                try {
                    int kuantitas = scanner.nextInt();
                    scanner.nextLine();
                    List<Produk> hasil = pengelolaProduk.cariProduk(namaProduk);
                    if (!hasil.isEmpty()) {
                        items.add(new ItemPesanan(hasil.get(0), kuantitas));
                    } else {
                        System.out.println("Produk tidak ditemukan!");
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
                System.out.println("Pesanan dibuat dengan total: Rp" + pesanan.hitungTotal());
            } else {
                System.out.println("Tidak ada item yang ditambahkan ke pesanan!");
            }
        } catch (KikoopiException e) {
            System.out.println("Gagal membuat pesanan: " + e.getMessage());
        }
    }

    private static void lihatRiwayatPesanan(Pelanggan pelanggan) {
        List<Pesanan> pesanan = pelanggan.getRiwayatPesanan();
        if (pesanan.isEmpty()) {
            System.out.println("Belum ada pesanan!");
        } else {
            pesanan.forEach(p -> System.out.println("Pesanan ID: " + p.getId() + ", Total: Rp" + p.hitungTotal() + ", Status: " + p.getStatus()));
        }
    }

    private static void tambahUlasan(Pelanggan pelanggan) {
        try {
            System.out.print("Rating (1-5): ");
            int rating = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Komentar: ");
            String komentar = scanner.nextLine();
            Ulasan ulasan = new Ulasan(UUID.randomUUID().toString(), pelanggan.getProfil().get("id"), rating, komentar, new Date());
            pengelolaUlasan.tambahUlasan(ulasan);
            pelanggan.tambahUlasan(ulasan);
            System.out.println("Ulasan ditambahkan!");
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
                System.out.println("Anda sudah terdaftar sebagai anggota!");
                return;
            }
            Keanggotaan keanggotaan = new Keanggotaan(UUID.randomUUID().toString(), pelanggan.getProfil().get("id"), new Date(), "Active");
            pengelolaKeanggotaan.tambahKeanggotaan(keanggotaan);
            pelanggan.setKeanggotaan(keanggotaan);
            System.out.println("Keanggotaan berhasil didaftarkan!");
        } catch (KikoopiException e) {
            System.out.println("Gagal mendaftar keanggotaan: " + e.getMessage());
        }
    }

    private static void updateProfil(Pelanggan pelanggan) {
        try {
            Map<String, String> profilBaru = new HashMap<>();
            System.out.print("Nama baru (kosongkan jika tidak diubah): ");
            String nama = scanner.nextLine();
            if (!nama.isEmpty()) profilBaru.put("nama", nama);
            System.out.print("Email baru (kosongkan jika tidak diubah): ");
            String email = scanner.nextLine();
            if (!email.isEmpty()) {
                if (pengelolaPengguna.cariPengguna(email) != null) {
                    System.out.println("Email sudah terdaftar!");
                    return;
                }
                profilBaru.put("email", email);
            }
            System.out.print("Telepon baru (kosongkan jika tidak diubah): ");
            String telepon = scanner.nextLine();
            if (!telepon.isEmpty()) profilBaru.put("telepon", telepon);
            System.out.print("Kata sandi baru (kosongkan jika tidak diubah): ");
            String kataSandi = scanner.nextLine();
            if (!kataSandi.isEmpty()) profilBaru.put("kataSandi", kataSandi);
            pelanggan.updateProfil(profilBaru);
            pengelolaPengguna.perbaruiPengguna(pelanggan);
            System.out.println("Profil berhasil diperbarui!");
        } catch (KikoopiException e) {
            System.out.println("Gagal memperbarui profil: " + e.getMessage());
        }
    }

    private static void menuAdmin(Admin admin) {
        while (true) {
            System.out.println("\n=== Menu Admin ===");
            System.out.println("1. Tambah Produk");
            System.out.println("2. Update Produk");
            System.out.println("3. Hapus Produk");
            System.out.println("4. Kelola Keanggotaan");
            System.out.println("5. Buat Laporan");
            System.out.println("6. Logout");
            System.out.print("Pilih opsi: ");
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
                        System.out.println(admin.buatLaporan());
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Masukkan harus berupa angka!");
                scanner.nextLine();
            }
        }
    }

    private static void tambahProduk(Admin admin) {
        try {
            System.out.print("Nama Produk: ");
            String nama = scanner.nextLine();
            System.out.print("Harga: ");
            double harga = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Deskripsi: ");
            String deskripsi = scanner.nextLine();
            System.out.print("Kategori (Makanan/Minuman): ");
            String kategori = scanner.nextLine();
            Produk produk = new Produk(UUID.randomUUID().toString(), nama, harga, deskripsi, kategori);
            pengelolaProduk.tambahProduk(produk);
            admin.kelolaInventaris(produk);
            System.out.println("Produk berhasil ditambahkan!");
        } catch (InputMismatchException e) {
            System.out.println("Harga harus berupa angka!");
            scanner.nextLine();
        } catch (KikoopiException e) {
            System.out.println("Gagal menambah produk: " + e.getMessage());
        }
    }

    private static void updateProduk(Admin admin) {
        try {
            System.out.print("Nama produk yang akan diupdate: ");
            String namaProduk = scanner.nextLine();
            List<Produk> hasil = pengelolaProduk.cariProduk(namaProduk);
            if (!hasil.isEmpty()) {
                Produk produkLama = hasil.get(0);
                System.out.print("Nama baru (kosongkan jika tidak diubah): ");
                String namaBaru = scanner.nextLine();
                System.out.print("Harga baru (0 jika tidak diubah): ");
                double hargaBaru = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Deskripsi baru (kosongkan jika tidak diubah): ");
                String deskripsiBaru = scanner.nextLine();
                System.out.print("Kategori baru (kosongkan jika tidak diubah): ");
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
                System.out.println("Produk berhasil diperbarui!");
            } else {
                System.out.println("Produk tidak ditemukan!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Harga harus berupa angka!");
            scanner.nextLine();
        } catch (KikoopiException e) {
            System.out.println("Gagal memperbarui produk: " + e.getMessage());
        }
    }

    private static void hapusProduk() {
        System.out.print("Nama Produk: ");
        String namaHapus = scanner.nextLine();
        List<Produk> hasil = pengelolaProduk.cariProduk(namaHapus);
        if (!hasil.isEmpty()) {
            pengelolaProduk.hapusProduk(hasil.get(0).getDetail().get("id"));
            System.out.println("Produk berhasil dihapus!");
        } else {
            System.out.println("Produk tidak ditemukan!");
        }
    }

    private static void kelolaKeanggotaan(Admin admin) {
        try {
            System.out.print("ID Pelanggan: ");
            String idPelanggan = scanner.nextLine();
            Keanggotaan keanggotaan = pengelolaKeanggotaan.cariKeanggotaan(idPelanggan);
            if (keanggotaan != null) {
                System.out.print("Status baru (Active/Inactive): ");
                String status = scanner.nextLine();
                pengelolaKeanggotaan.perbaruiStatusKeanggotaan(idPelanggan, status);
                admin.kelolaKeanggotaan(keanggotaan);
                System.out.println("Status keanggotaan berhasil diperbarui!");
            } else {
                System.out.println("Keanggotaan tidak ditemukan!");
            }
        } catch (KikoopiException e) {
            System.out.println("Gagal mengelola keanggotaan: " + e.getMessage());
        }
    }
}