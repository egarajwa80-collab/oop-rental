package id.ac.polinema.uts.rental;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Dependensi: RentalAgent hanya memakai Customer dan Car lewat parameter. */
class P04RentalAgentDependencyTest {

    private static Object agent() throws Throwable {
        return R.create("RentalAgent");
    }

    @Test
    @DisplayName("RentalAgent tidak menyimpan apa pun sebagai field (dependensi, bukan asosiasi)")
    void noFields() {
        Class<?> c = R.cls("RentalAgent");
        assertEquals(0, c.getDeclaredFields().length,
                "RentalAgent hanya BERGANTUNG pada Customer dan Car lewat parameter, jangan simpan sebagai field");
    }

    @Test
    @DisplayName("rent berhasil: mobil disewa dan jumlah sewa Customer naik")
    void rentSuccess() throws Throwable {
        Object l = agent();
        Customer m = new Customer("C001", "Dewi");
        Car b = new Car("N 1 A", "Avanza", 2021);
        assertEquals(true, R.call(l, "rent", m, b));
        assertFalse(b.isAvailable());
        assertEquals(1, m.getRentalCount());
    }

    @Test
    @DisplayName("rent gagal jika mobil sudah disewa; jumlah sewa tidak berubah")
    void rentCarTaken() throws Throwable {
        Object l = agent();
        Customer a = new Customer("C001", "Dewi");
        Customer b = new Customer("C002", "Rudi");
        Car car = new Car("N 1 A", "Avanza", 2021);
        R.call(l, "rent", a, car);
        assertEquals(false, R.call(l, "rent", b, car));
        assertEquals(0, b.getRentalCount());
    }

    @Test
    @DisplayName("rent gagal jika Customer sudah menyewa 2 mobil; mobil tetap tersedia")
    void rentCustomerAtLimit() throws Throwable {
        Object l = agent();
        Customer m = new Customer("C001", "Dewi");
        for (int i = 1; i <= 2; i++) {
            R.call(l, "rent", m, new Car("N " + i, "Car " + i, 2020));
        }
        Car third = new Car("N 3 C", "Car 3", 2020);
        assertEquals(false, R.call(l, "rent", m, third));
        assertTrue(third.isAvailable());
        assertEquals(2, m.getRentalCount());
    }

    @Test
    @DisplayName("giveBack berhasil: mobil tersedia lagi dan jumlah sewa turun")
    void giveBackSuccess() throws Throwable {
        Object l = agent();
        Customer m = new Customer("C001", "Dewi");
        Car b = new Car("N 1 A", "Avanza", 2021);
        R.call(l, "rent", m, b);
        assertEquals(true, R.call(l, "giveBack", m, b));
        assertTrue(b.isAvailable());
        assertEquals(0, m.getRentalCount());
    }

    @Test
    @DisplayName("giveBack gagal untuk mobil yang tidak sedang disewa")
    void giveBackNotRented() throws Throwable {
        Object l = agent();
        Customer m = new Customer("C001", "Dewi");
        assertEquals(false, R.call(l, "giveBack", m, new Car("N 1 A", "Avanza", 2021)));
        assertEquals(0, m.getRentalCount());
    }
}
