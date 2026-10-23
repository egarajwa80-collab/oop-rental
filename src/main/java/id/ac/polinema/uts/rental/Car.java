package id.ac.polinema.uts.rental;

/**
 * Bagian 2 dan 3: mobil rental.
 *
 * Jangan ubah nama kelas, nama method, atau signature-nya. Autograder
 * memanggilnya persis seperti yang tertulis di sini.
 *
 * Field tidak dideklarasikan di sini: baca diagram kelas di README.
 * Method untuk bagian berikutnya (setBrand, getBrand, dan seterusnya) kamu
 * tambahkan sendiri sesuai diagram di bagian tersebut.
 *
 * Kerangka field (lengkapi tipe dan nama dari diagram), letakkan di atas constructor:
 *   public String plate;
 *   public ____ model;
 *   public ____ year;
 *   public ____ available;
 */
public class Car {

    private String plate;
    private String model;      
    private int year;
    private boolean available;

    /**
     * Membuat mobil baru yang masih tersedia (belum disewa).
     * Bagian 3: plate tidak boleh kosong; model dan year divalidasi seperti setter-nya
     * (tahun minimal 1990).
     */
    public Car(String plate, String model, int year) {
        if (plate == null || plate.isBlank()) {
            throw new IllegalArgumentException("Plate cannot be empty");
        }
        this.plate = plate;
        // TODO Bantuan: isi semua field. Mobil baru: apakah sedang disewa atau tidak?
        // Bagian 3: aturan validasi model dan tahun sama dengan setter. Bisakah constructor
        // memakai setter-nya?
        setModel(model);
        setYear(year);
        this.available = true;
        // Kerangka (Bagian 2):
        //   this.plate = plate;
        //   this.model = ____;
        //   this.year = ____;
        //   this.available = ____;
        //
        // Kerangka (Bagian 3): ganti dua baris model dan year dengan pemanggilan setter.
        //   setModel(____);
        //   setYear(____);
       
    }

    public String getPlate() {
        // TODO Bantuan: kembalikan data yang disimpan constructor.
        return this.plate;
        // Kerangka:
        //   return ____;
        
    }

    public String getModel() {
        // TODO Bantuan: sama seperti getPlate.
        return this.model;
    }

    public int getYear() {
        // TODO Bantuan: sama seperti getPlate.
        return this.year;
    }
    

    /** True jika mobil ada di garasi (tidak sedang disewa). */
    public boolean isAvailable() {
        // TODO Bantuan: field apa yang menyimpan status sewa?
        //
        // Kerangka:
        //   return ____;
        return this.available;
    }

    /**
     * Menyewa mobil. Berhasil (true) jika mobil tersedia. Jika mobil sedang
     * disewa, hasilnya false dan keadaan mobil tidak berubah.
     */
    public boolean rentOut() {
        // TODO Bantuan: apa yang harus kamu periksa lebih dulu sebelum mengubah status?
        // Method ini punya dua jalur return yang berbeda.
        //
        // Kerangka:
        //   if (____) {
        //       return false;
        //   }
        //   ____ = false;
        //   return true;
        if (!this.available) {
            return false;
        }
        this.available = false;
        return true;
    }

    /** Mobil dikembalikan: mobil bisa disewa lagi. */
    public void returnVehicle() {
        // TODO Bantuan: satu baris saja. Status apa yang berubah?
        //
        // Kerangka:
        //   ____ = true;
        this.available = true;
    }

    /**
     * Bagian 3. Mengubah model. Model null, kosong, atau hanya spasi ditolak
     * dengan IllegalArgumentException, dan model lama tetap.
     */
    public void setModel(String model) {
        // TODO Bantuan: periksa dulu, baru isi field. Class String punya method untuk
        // memeriksa teks kosong atau hanya spasi. Jangan lupa kemungkinan null.
        //
        // Kerangka:
        //   if (model == null || ____) {
        //       throw new IllegalArgumentException("____");
        //   }
        //   this.model = model;
        if (model == null || model.isBlank()) {
            throw new IllegalArgumentException("Model cannot be empty");
        }
        this.model = model;
    }

    /**
     * Bagian 3. Mengubah tahun produksi. Tahun di bawah 1990 ditolak dengan
     * IllegalArgumentException, dan tahun lama tetap.
     */
    public void setYear(int year) {
        // TODO Bantuan: pola yang sama dengan setModel. Apa syarat tahun yang valid?
        //
        // Kerangka:
        //   if (year ____ 1990) {
        //       throw new IllegalArgumentException("____");
        //   }
        //   this.year = year;
        if (year < 1990) {
            throw new IllegalArgumentException("Year must be 1990 or later");
        }
        this.year = year;
    }
}
