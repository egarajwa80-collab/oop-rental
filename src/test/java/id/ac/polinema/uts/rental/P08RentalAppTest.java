package id.ac.polinema.uts.rental;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Menjalankan RentalApp dengan input skrip (bukan keyboard sungguhan),
 * lalu memeriksa teks yang dicetak.
 */
@Timeout(10)
class P08RentalAppTest {

    private static Object shop() throws Throwable {
        return R.create("RentalShop", "Polinema Rental");
    }

    private static String run(Object shop, String... lines) throws Throwable {
        String input = String.join("\n", lines) + "\n";
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Object app = R.create("RentalApp", shop, new Scanner(input), out);
        R.call(app, "run");
        return buffer.toString(StandardCharsets.UTF_8);
    }

    private static void assertHas(String output, String expected) {
        assertTrue(output.contains(expected),
                "Output harus memuat \"" + expected + "\". Output sebenarnya:\n" + output);
    }

    @Test
    @DisplayName("Menu tampil lengkap dan pilihan 0 mencetak 'Thank you'")
    void menuAndExit() throws Throwable {
        String out = run(shop(), "0");
        assertHas(out, "=== Polinema Rental ===");
        for (String line : new String[] {"1. List vehicles", "2. Add car", "3. Add motorcycle", "4. Add truck",
                "5. Register customer", "6. Rent vehicle", "7. Return vehicle", "8. Search vehicles",
                "9. List customers", "0. Exit"}) {
            assertHas(out, line);
        }
        assertHas(out, "Thank you");
    }

    @Test
    @DisplayName("Pilihan yang tidak dikenal mencetak 'Unknown option' lalu menu muncul lagi")
    void invalidChoice() throws Throwable {
        String out = run(shop(), "x", "42", "0");
        assertHas(out, "Unknown option");
        assertEquals(3, out.split("0\\. Exit", -1).length - 1, "Menu harus dicetak ulang di setiap putaran");
    }

    @Test
    @DisplayName("Daftar kosong mencetak 'No vehicles' dan 'No customers'")
    void emptyLists() throws Throwable {
        String out = run(shop(), "1", "9", "0");
        assertHas(out, "No vehicles");
        assertHas(out, "No customers");
    }

    @Test
    @DisplayName("Tambah mobil lalu tampilkan: describe, status, dan tarif harian")
    void addCarAndList() throws Throwable {
        Object shop = shop();
        String out = run(shop, "2", "N 1234 AB", "Avanza", "2021", "Toyota", "Japan", "1", "0");
        assertHas(out, "Vehicle added");
        assertHas(out, "Avanza (2021) - Toyota | Available | Rp350000/day");
        assertEquals(1, ((Object[]) R.call(shop, "getVehicles")).length);
    }

    @Test
    @DisplayName("Tambah mobil tanpa merek: nama merek dikosongkan")
    void addCarWithoutBrand() throws Throwable {
        String out = run(shop(), "2", "L 5678 CD", "Brio", "2018", "", "1", "0");
        assertHas(out, "Vehicle added");
        assertHas(out, "Brio (2018) - Unknown | Available | Rp350000/day");
    }

    @Test
    @DisplayName("Tambah motor dan truk")
    void addMotorcycleAndTruck() throws Throwable {
        String out = run(shop(), "3", "Vario", "2022", "125", "4", "Canter", "2020", "4", "1", "0");
        assertHas(out, "Vario (2022) [125 cc] | Available | Rp100000/day");
        assertHas(out, "Canter (2020) | Available | Rp250000/day");
    }

    @Test
    @DisplayName("Tahun bukan angka atau di bawah 1990 mencetak 'Invalid number' dan kendaraan tidak ditambah")
    void invalidYear() throws Throwable {
        Object shop = shop();
        String out = run(shop, "2", "N 1 A", "Avanza", "abc", "3", "Vario", "-5", "100",
                "4", "Canter", "1980", "3", "2", "N 2 B", "Brio", "1989", "0");
        assertHas(out, "Invalid number");
        assertEquals(0, out.split("Vehicle added", -1).length - 1);
        assertEquals(0, ((Object[]) R.call(shop, "getVehicles")).length);
    }

    @Test
    @DisplayName("Plat atau model kosong mencetak 'Invalid input'")
    void blankInput() throws Throwable {
        Object shop = shop();
        String out = run(shop, "2", "", "Avanza", "2021", "0");
        assertHas(out, "Invalid input");
        assertEquals(0, ((Object[]) R.call(shop, "getVehicles")).length);
    }

    @Test
    @DisplayName("Daftar pelanggan baru, ID kembar ditolak")
    void registerCustomer() throws Throwable {
        String out = run(shop(), "5", "C001", "Dewi", "5", "C001", "Rudi", "9", "0");
        assertHas(out, "Customer registered");
        assertHas(out, "Customer ID already exists");
        assertHas(out, "C001 - Dewi (0 rentals)");
    }

    @Test
    @DisplayName("Alur lengkap: tambah pelanggan dan kendaraan, sewa, lihat status, kembalikan")
    void rentAndReturn() throws Throwable {
        Object shop = shop();
        String out = run(shop,
                "5", "C001", "Dewi",
                "3", "Vario", "2022", "125",
                "6", "C001", "Vario",
                "1", "9",
                "7", "C001", "Vario",
                "1", "9",
                "0");
        assertHas(out, "Rental successful");
        assertHas(out, "Vario (2022) [125 cc] | Rented | Rp100000/day");
        assertHas(out, "C001 - Dewi (1 rentals)");
        assertHas(out, "Return successful");
        assertHas(out, "Vario (2022) [125 cc] | Available | Rp100000/day");
        assertHas(out, "C001 - Dewi (0 rentals)");
    }

    @Test
    @DisplayName("Sewa dan kembalikan yang tidak valid mencetak 'Rental failed' dan 'Return failed'")
    void failures() throws Throwable {
        String out = run(shop(), "6", "X", "Y", "7", "X", "Y", "0");
        assertHas(out, "Rental failed");
        assertHas(out, "Return failed");
    }

    @Test
    @DisplayName("Pelanggan ketiga sewa ditolak: batas 2 kendaraan")
    void rentLimit() throws Throwable {
        String out = run(shop(),
                "5", "C001", "Dewi",
                "3", "Vario", "2022", "125",
                "3", "Beat", "2021", "110",
                "3", "Scoopy", "2023", "110",
                "6", "C001", "Vario",
                "6", "C001", "Beat",
                "6", "C001", "Scoopy",
                "0");
        assertEquals(2, out.split("Rental successful", -1).length - 1);
        assertHas(out, "Rental failed");
    }

    @Test
    @DisplayName("Cari kendaraan: tampilkan yang cocok, atau 'No vehicles found'")
    void search() throws Throwable {
        Object shop = shop();
        R.call(shop, "addVehicle", new Car("N 1 A", "Avanza", 2021));
        R.call(shop, "addVehicle", R.create("Motorcycle", "Vario", 2022, 125));
        String out = run(shop, "8", "avanza", "8", "zzz", "0");
        assertHas(out, "Avanza (2021) - Unknown");
        assertHas(out, "No vehicles found");
        assertTrue(!out.contains("Vario (2022)"), "Vario tidak cocok dengan kata kunci 'avanza'");
    }

    @Test
    @DisplayName("Main mencetak sapaan, lalu menjalankan RentalApp dengan data contoh")
    void mainStartsTheApp() {
        PrintStream originalOut = System.out;
        InputStream originalIn = System.in;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        System.setIn(new ByteArrayInputStream("1\n0\n".getBytes(StandardCharsets.UTF_8)));
        try {
            Main.main(new String[0]);
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
        String out = buffer.toString(StandardCharsets.UTF_8);
        assertTrue(out.startsWith("Welcome to Polinema Rental"),
                "Baris pertama output Main harus sapaan. Output sebenarnya:\n" + out);
        assertHas(out, "=== Polinema Rental ===");
        assertHas(out, "Thank you");
        assertTrue(!out.contains("No vehicles"),
                "Main harus mengisi data contoh sebelum menjalankan RentalApp");
    }
}
