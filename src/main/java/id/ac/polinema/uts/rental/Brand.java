package id.ac.polinema.uts.rental;

/**
 * Bagian 2: merek kendaraan (contoh: Toyota dari Japan).
 *
 * Jangan ubah nama kelas, nama method, atau signature-nya. Autograder
 * memanggilnya persis seperti yang tertulis di sini.
 *
 * Field tidak dideklarasikan di sini: baca diagram kelas di README.
 * Di Bagian 2 field boleh public. Di Bagian 3 kamu akan menutupnya.
 *
 * Kerangka field (lengkapi tipe dan nama dari diagram), letakkan di atas constructor:
 *   public ____ name;
 *   public ____ country;
 */
public class Brand {

    private String name;
    private String country;

    /** Membuat merek dengan nama dan negara asal. */
    public Brand(String name, String country) {
        // TODO Bantuan: nama parameter sama dengan nama field. Kata kunci apa yang
        // menunjuk ke objek yang sedang dibuat?
        //
        // Kerangka:
        //   this.name = name;
        //   this.country = country;
        this.name = name;
        this.country = country;
    }

    public String getName() {
        // TODO Bantuan: kembalikan data yang disimpan constructor.
        //
        // Kerangka:
        //   return ____;
        return this.name;
    }

    public String getCountry() {
        // TODO Bantuan: sama seperti getName.
        return this.country;
    }

    /** Teks berbentuk "nama (negara)". Contoh: Wuling (China). */
    public String getInfo() {
        // TODO Bantuan: gabungkan dua data dengan operator + dan tanda kurung dalam String.
        //
        // Kerangka:
        //   return name + " (" + ____ + ")";
        return this.name + " (" + this.country + ")";
    }

}
//cek rgsg