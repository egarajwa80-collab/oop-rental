package id.ac.polinema.uts.rental;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** RentalShop berisi logika saja: tidak boleh mencetak apa pun. */
@Timeout(10)
class P08RentalShopTest {

    private static Object shop() throws Throwable {
        return R.create("RentalShop", "Polinema Rental");
    }

    private static Object motorcycle(String model) throws Throwable {
        return R.create("Motorcycle", model, 2022, 100);
    }

    private static Object truck(String model) throws Throwable {
        return R.create("Truck", model, 2020, 1);
    }

    @Test
    @DisplayName("RentalShop menyimpan nama dan semua field-nya private")
    void basics() throws Throwable {
        R.assertAllFieldsPrivate(R.cls("RentalShop"));
        assertEquals("Polinema Rental", R.call(shop(), "getName"));
    }

    @Test
    @DisplayName("addVehicle menerima Car, Motorcycle, dan Truck dalam satu array")
    void mixedVehicles() throws Throwable {
        Object shop = shop();
        Car car = new Car("N 1 A", "Avanza", 2021);
        Object motorcycle = motorcycle("Vario");
        Object truck = truck("Canter");
        assertEquals(true, R.call(shop, "addVehicle", car));
        assertEquals(true, R.call(shop, "addVehicle", motorcycle));
        assertEquals(true, R.call(shop, "addVehicle", truck));
        Object[] vehicles = (Object[]) R.call(shop, "getVehicles");
        assertEquals(3, vehicles.length, "getVehicles hanya mengembalikan kendaraan yang terisi");
        assertSame(car, vehicles[0]);
        assertSame(motorcycle, vehicles[1]);
        assertSame(truck, vehicles[2]);
    }

    @Test
    @DisplayName("addVehicle menolak null dan menolak jika sudah penuh (20 kendaraan)")
    void vehicleCapacity() throws Throwable {
        Object shop = shop();
        assertEquals(false, R.call(shop, "addVehicle", (Object) null));
        for (int i = 1; i <= 20; i++) {
            assertEquals(true, R.call(shop, "addVehicle", truck("Truck " + i)));
        }
        assertEquals(false, R.call(shop, "addVehicle", truck("One too many")));
        assertEquals(20, ((Object[]) R.call(shop, "getVehicles")).length);
    }

    @Test
    @DisplayName("addCustomer menolak id kembar dan null; findCustomer mengembalikan objek yang sama")
    void customers() throws Throwable {
        Object shop = shop();
        Customer dewi = new Customer("C001", "Dewi");
        assertEquals(true, R.call(shop, "addCustomer", dewi));
        assertEquals(false, R.call(shop, "addCustomer", new Customer("C001", "Orang Lain")));
        assertEquals(false, R.call(shop, "addCustomer", (Object) null));
        assertEquals(1, ((Object[]) R.call(shop, "getCustomers")).length);
        assertSame(dewi, R.call(shop, "findCustomer", "C001"));
        assertNull(R.call(shop, "findCustomer", "C999"));
    }

    @Test
    @DisplayName("addCustomer menolak jika sudah penuh (10 pelanggan)")
    void customerCapacity() throws Throwable {
        Object shop = shop();
        for (int i = 1; i <= 10; i++) {
            assertEquals(true, R.call(shop, "addCustomer", new Customer("C" + i, "Customer " + i)));
        }
        assertEquals(false, R.call(shop, "addCustomer", new Customer("C11", "Customer 11")));
    }

    @Test
    @DisplayName("findVehicleByModel tidak peduli huruf besar-kecil; null jika tidak ada")
    void findVehicle() throws Throwable {
        Object shop = shop();
        Car car = new Car("N 1 A", "Avanza", 2021);
        R.call(shop, "addVehicle", car);
        assertSame(car, R.call(shop, "findVehicleByModel", "avanza"));
        assertSame(car, R.call(shop, "findVehicleByModel", "AVANZA"));
        assertNull(R.call(shop, "findVehicleByModel", "Unknown"));
    }

    @Test
    @DisplayName("searchByKeyword mengembalikan semua kendaraan yang modelnya memuat kata kunci")
    void search() throws Throwable {
        Object shop = shop();
        Car avanza = new Car("N 1 A", "Avanza", 2021);
        Car veloz = new Car("N 2 B", "Avanza Veloz", 2004);
        Object motorcycle = motorcycle("Vario");
        R.call(shop, "addVehicle", avanza);
        R.call(shop, "addVehicle", motorcycle);
        R.call(shop, "addVehicle", veloz);
        Object[] found = (Object[]) R.call(shop, "searchByKeyword", "avanza");
        assertEquals(2, found.length);
        assertSame(avanza, found[0]);
        assertSame(veloz, found[1]);
        Object[] none = (Object[]) R.call(shop, "searchByKeyword", "zzz");
        assertEquals(0, none.length, "Tanpa hasil, kembalikan array kosong, bukan null");
    }

    @Test
    @DisplayName("rent berhasil: kendaraan disewa dan jumlah sewa Customer naik")
    void rentSuccess() throws Throwable {
        Object shop = shop();
        Customer dewi = new Customer("C001", "Dewi");
        Car car = new Car("N 1 A", "Avanza", 2021);
        R.call(shop, "addCustomer", dewi);
        R.call(shop, "addVehicle", car);
        assertEquals(true, R.call(shop, "rent", "C001", "Avanza"));
        assertEquals(false, car.isAvailable());
        assertEquals(1, dewi.getRentalCount());
    }

    @Test
    @DisplayName("rent gagal untuk Customer atau model yang tidak ada, dan kendaraan yang sudah disewa")
    void rentFailures() throws Throwable {
        Object shop = shop();
        R.call(shop, "addCustomer", new Customer("C001", "Dewi"));
        R.call(shop, "addCustomer", new Customer("C002", "Rudi"));
        R.call(shop, "addVehicle", new Car("N 1 A", "Avanza", 2021));
        assertEquals(false, R.call(shop, "rent", "C999", "Avanza"));
        assertEquals(false, R.call(shop, "rent", "C001", "Unknown"));
        assertEquals(true, R.call(shop, "rent", "C001", "Avanza"));
        assertEquals(false, R.call(shop, "rent", "C002", "Avanza"));
    }

    @Test
    @DisplayName("rent gagal jika Customer sudah menyewa 2 kendaraan")
    void rentLimit() throws Throwable {
        Object shop = shop();
        Customer dewi = new Customer("C001", "Dewi");
        R.call(shop, "addCustomer", dewi);
        for (int i = 1; i <= 3; i++) {
            R.call(shop, "addVehicle", truck("Truck " + i));
        }
        assertEquals(true, R.call(shop, "rent", "C001", "Truck 1"));
        assertEquals(true, R.call(shop, "rent", "C001", "Truck 2"));
        assertEquals(false, R.call(shop, "rent", "C001", "Truck 3"));
        assertEquals(2, dewi.getRentalCount());
        Object third = R.call(shop, "findVehicleByModel", "Truck 3");
        assertEquals(true, R.call(third, "isAvailable"));
    }

    @Test
    @DisplayName("giveBack berhasil lalu gagal jika kendaraan tidak sedang disewa atau data tidak ada")
    void giveBack() throws Throwable {
        Object shop = shop();
        Customer dewi = new Customer("C001", "Dewi");
        Object motorcycle = motorcycle("Vario");
        R.call(shop, "addCustomer", dewi);
        R.call(shop, "addVehicle", motorcycle);
        assertEquals(false, R.call(shop, "giveBack", "C001", "Vario"));
        R.call(shop, "rent", "C001", "Vario");
        assertEquals(true, R.call(shop, "giveBack", "C001", "Vario"));
        assertEquals(true, R.call(motorcycle, "isAvailable"));
        assertEquals(0, dewi.getRentalCount());
        assertEquals(false, R.call(shop, "giveBack", "C999", "Vario"));
        assertEquals(false, R.call(shop, "giveBack", "C001", "Unknown"));
    }

    @Test
    @DisplayName("RentalShop tidak boleh mencetak ke layar (pisahkan logika dan tampilan)")
    void shopDoesNotPrint() throws Throwable {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        try {
            Object shop = shop();
            R.call(shop, "addCustomer", new Customer("C001", "Dewi"));
            R.call(shop, "addVehicle", new Car("N 1 A", "Avanza", 2021));
            R.call(shop, "rent", "C001", "Avanza");
            R.call(shop, "giveBack", "C001", "Avanza");
            R.call(shop, "searchByKeyword", "avanza");
            R.call(shop, "getVehicles");
        } finally {
            System.setOut(original);
        }
        assertTrue(buffer.toString(StandardCharsets.UTF_8).isEmpty(),
                "RentalShop hanya berisi logika. Semua System.out.println ada di RentalApp");
    }
}
