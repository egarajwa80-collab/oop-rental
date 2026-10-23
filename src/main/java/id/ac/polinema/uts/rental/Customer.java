package id.ac.polinema.uts.rental;

/**
 * Bagian 3: pelanggan rental.
 *
 * Jangan ubah nama kelas, nama method, atau signature-nya. Autograder
 * memanggilnya persis seperti yang tertulis di sini.
 *
 * Field tidak dideklarasikan di sini: baca diagram kelas di README
 * (semua field harus private).
 *
 * Kerangka field (lengkapi dari diagram), letakkan di atas constructor:
 *   private ____ customerId;
 *   private ____ name;
 *   private int rentalCount;   // nilai awal 0
 */
public class Customer {
    private String customerId;
    private String name;
    private int rentalCount;

    /**
     * Membuat pelanggan yang belum punya sewa aktif.
     * customerId dan name tidak boleh null atau kosong: lempar
     * IllegalArgumentException jika tidak valid.
     */
    public Customer(String customerId, String name) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID cannot be empty");
        }
        
        // TODO Bantuan: validasi dulu sebelum mengisi field. Aturan nama sama dengan setName.
        // Bagian 4: kartu rental juga dibuat di sini (lihat diagram Bagian 4).
        //
        // Kerangka:
        //   if (customerId == null || ____) {
        //       throw new IllegalArgumentException("____");
        //   }
        //   this.customerId = customerId;
        //   setName(____);
        this.customerId = customerId;
        setName(name);
    }

    public String getCustomerId() {
        // TODO Bantuan: kembalikan data yang disimpan constructor.
        return this.customerId;
    }

    public String getName() {
        // TODO Bantuan: sama seperti getCustomerId.
        return this.name;
    }

    /** Nama tidak boleh null atau kosong: lempar IllegalArgumentException dan biarkan nama lama. */
    public void setName(String name) {
        // TODO Bantuan: periksa dulu, baru isi field.
        //
        // Kerangka:
        //   if (name == null || ____) {
        //       throw new IllegalArgumentException("____");
        //   }
        //   this.name = name;
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public int getRentalCount() {
        // TODO Bantuan: kembalikan jumlah sewa aktif saat ini.
        return this.rentalCount;
    }

    /** True jika pelanggan masih boleh menyewa (batasnya 2 sewa aktif). */
    public boolean canRent() {
        // TODO Bantuan: apakah perlu field baru, atau cukup dihitung dari data yang sudah ada?
        //
        // Kerangka:
        //   return rentalCount < 2;
        return this.rentalCount < 2;
    }

    /** Menambah jumlah sewa. Jika sudah di batas, tidak berubah dan hasilnya false. */
    public boolean addRental() {
        // TODO Bantuan: periksa batas dulu. Adakah method lain di kelas ini yang sudah
        // menjawab pertanyaan "boleh atau tidak"?
        //
        // Kerangka:
        //   if (!____) {
        //       return false;
        //   }
        //   rentalCount++;
        //   return true;
        if (!canRent()) {
            return false;
        }
        this.rentalCount++;
        return true;
    }

    /** Mengurangi jumlah sewa. Jika sudah tidak ada sewa, tidak berubah dan hasilnya false. */
    public boolean returnRental() {
        // TODO Bantuan: batas bawah jumlah sewa adalah berapa?
        //
        // Kerangka:
        //   if (rentalCount == ____) {
        //       return false;
        //   }
        //   rentalCount--;
        //   return true;
        if (this.rentalCount == 0) {
            return false;
        }
        this.rentalCount--;
        return true;
    }
}
