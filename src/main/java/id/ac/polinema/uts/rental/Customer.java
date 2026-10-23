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

    /**
     * Membuat pelanggan yang belum punya sewa aktif.
     * customerId dan name tidak boleh null atau kosong: lempar
     * IllegalArgumentException jika tidak valid.
     */
    public Customer(String customerId, String name) {
        // TODO Bantuan: validasi dulu sebelum mengisi field. Aturan nama sama dengan setName.
        // Bagian 4: kartu rental juga dibuat di sini (lihat diagram Bagian 4).
        //
        // Kerangka:
        //   if (customerId == null || ____) {
        //       throw new IllegalArgumentException("____");
        //   }
        //   this.customerId = customerId;
        //   setName(____);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getCustomerId() {
        // TODO Bantuan: kembalikan data yang disimpan constructor.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getName() {
        // TODO Bantuan: sama seperti getCustomerId.
        throw new UnsupportedOperationException("Not implemented yet");
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
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getRentalCount() {
        // TODO Bantuan: kembalikan jumlah sewa aktif saat ini.
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /** True jika pelanggan masih boleh menyewa (batasnya 2 sewa aktif). */
    public boolean canRent() {
        // TODO Bantuan: apakah perlu field baru, atau cukup dihitung dari data yang sudah ada?
        //
        // Kerangka:
        //   return rentalCount ____ 2;
        throw new UnsupportedOperationException("Not implemented yet");
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
        throw new UnsupportedOperationException("Not implemented yet");
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
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
