import java.util.Date;

public class Keanggotaan {
    private String id;
    private String idPelanggan;
    private Date tanggalMulai;
    private String status;

    public Keanggotaan(String id, String idPelanggan, Date tanggalMulai, String status) throws KikoopiException {
        if (!status.equals("Active") && !status.equals("Inactive")) {
            throw new KikoopiException("Status keanggotaan harus Active atau Inactive!");
        }
        this.id = id;
        this.idPelanggan = idPelanggan;
        this.tanggalMulai = tanggalMulai;
        this.status = status;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) throws KikoopiException {
        if (!status.equals("Active") && !status.equals("Inactive")) {
            throw new KikoopiException("Status keanggotaan harus Active atau Inactive!");
        }
        this.status = status;
    }
}