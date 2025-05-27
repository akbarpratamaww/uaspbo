public class ItemPesanan {
    private Produk produk;
    private int kuantitas;

    public ItemPesanan(Produk produk, int kuantitas) throws KikoopiException {
        if (produk == null) {
            throw new KikoopiException("Produk tidak boleh null!");
        }
        if (kuantitas <= 0) {
            throw new KikoopiException("Kuantitas harus lebih dari 0!");
        }
        this.produk = produk;
        this.kuantitas = kuantitas;
    }

    public double getSubtotal() {
        return produk.getHarga() * kuantitas;
    }
}