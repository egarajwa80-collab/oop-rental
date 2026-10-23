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

    /**
     * Membuat mobil baru yang masih tersedia (belum disewa).
     * Bagian 3: plate tidak boleh kosong; model dan year divalidasi seperti setter-nya
     * (tahun minimal 1990).
     */
    public Car(String plate, String model, int year) {
        // TODO Bantuan: isi semua field. Mobil baru: apakah sedang disewa atau tidak?
        // Bagian 3: aturan validasi model dan tahun sama dengan setter. Bisakah constructor
        // memakai setter-nya?
        //
        // Kerangka (Bagian 2):
        //   this.plate = plate;
        //   this.model = ____;
        //   this.year = ____;
        //   this.available = ____;
        //
        // Kerangka (Bagian 3): ganti dua baris model dan year dengan pemanggilan setter.
        //   setModel(____);
        //   setYear(____);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getPlate() {
        // TODO Bantuan: kembalikan data yang disimpan constructor.
        //
        // Kerangka:
        //   return ____;
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getModel() {
        // TODO Bantuan: sama seperti getPlate.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getYear() {
        // TODO Bantuan: sama seperti getPlate.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** True jika mobil ada di garasi (tidak sedang disewa). */
    public boolean isAvailable() {
        // TODO Bantuan: field apa yang menyimpan status sewa?
        //
        // Kerangka:
        //   return ____;
        throw new UnsupportedOperationException("Not implemented yet");
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
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** Mobil dikembalikan: mobil bisa disewa lagi. */
    public void returnVehicle() {
        // TODO Bantuan: satu baris saja. Status apa yang berubah?
        //
        // Kerangka:
        //   ____ = true;
        throw new UnsupportedOperationException("Not implemented yet");
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
        throw new UnsupportedOperationException("Not implemented yet");
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
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
