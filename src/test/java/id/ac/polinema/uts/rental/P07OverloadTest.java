package id.ac.polinema.uts.rental;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Overloading: nama method atau constructor sama, parameter berbeda. */
class P07OverloadTest {

    @Test
    @DisplayName("Vehicle punya dua method extendRental dengan parameter berbeda")
    void twoExtendRental() {
        int count = 0;
        for (Method m : R.cls("Vehicle").getDeclaredMethods()) {
            if (m.getName().equals("extendRental")) {
                count++;
            }
        }
        assertEquals(2, count, "Harus ada extendRental() dan extendRental(int days)");
    }

    @Test
    @DisplayName("extendRental() menambah 2 hari")
    void extendDefault() throws Throwable {
        Object motorcycle = R.create("Motorcycle", "Vario", 2022, 125);
        assertEquals(1, R.call(motorcycle, "getTotalRentalDays"));
        R.call(motorcycle, "extendRental");
        assertEquals(3, R.call(motorcycle, "getTotalRentalDays"));
    }

    @Test
    @DisplayName("extendRental(int) menambah sebanyak hari yang diberikan dan bisa berulang")
    void extendDays() throws Throwable {
        Car c = new Car("N 1 A", "Avanza", 2021);
        assertEquals(1, R.call(c, "getTotalRentalDays"));
        R.call(c, "extendRental", 3);
        R.call(c, "extendRental", 2);
        assertEquals(6, R.call(c, "getTotalRentalDays"));
    }

    @Test
    @DisplayName("extendRental(int) menolak 0 dan negatif; total tidak berubah")
    void extendRejectsNonPositive() throws Throwable {
        Object t = R.create("Truck", "Canter", 2020, 4);
        assertThrows(IllegalArgumentException.class, () -> R.call(t, "extendRental", 0));
        assertThrows(IllegalArgumentException.class, () -> R.call(t, "extendRental", -4));
        assertEquals(1, R.call(t, "getTotalRentalDays"));
    }

    @Test
    @DisplayName("Constructor Car(plate, model, year, brand) langsung mengisi merek")
    void carConstructorWithBrand() throws Throwable {
        Brand toyota = new Brand("Toyota", "Japan");
        Object c = R.create("Car", "N 1 A", "Avanza", 2021, toyota);
        assertSame(toyota, R.call(c, "getBrand"));
        assertEquals("Avanza", R.call(c, "getModel"));
        assertEquals("N 1 A", R.call(c, "getPlate"));
        assertEquals(true, R.call(c, "isAvailable"));
    }

    @Test
    @DisplayName("Constructor Truck(model, year) memberi muatan 5 ton; versi 3 parameter tetap jalan")
    void truckConstructors() throws Throwable {
        Object a = R.create("Truck", "Canter", 2020);
        assertEquals(5, R.call(a, "getLoadTons"));
        Object b = R.create("Truck", "Canter", 2020, 9);
        assertEquals(9, R.call(b, "getLoadTons"));
    }
}
