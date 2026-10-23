package id.ac.polinema.uts.rental;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Agregasi: Garage menyimpan Car, tetapi Car ada di luar Garage juga. */
class P04GarageAggregationTest {

    private static Object garage() throws Throwable {
        return R.create("Garage", "G1");
    }

    @Test
    @DisplayName("Garage ada, semua field-nya private, dan menyimpan kode garasi")
    void basics() throws Throwable {
        R.assertAllFieldsPrivate(R.cls("Garage"));
        Object s = garage();
        assertEquals("G1", R.call(s, "getCode"));
        assertEquals(0, R.call(s, "getCarCount"));
    }

    @Test
    @DisplayName("addCar menambah mobil dan getCarCount ikut naik")
    void addCar() throws Throwable {
        Object s = garage();
        assertEquals(true, R.call(s, "addCar", new Car("N 1 A", "Avanza", 2021)));
        assertEquals(true, R.call(s, "addCar", new Car("N 2 B", "Brio", 2018)));
        assertEquals(2, R.call(s, "getCarCount"));
    }

    @Test
    @DisplayName("Kapasitas garasi 5 mobil; mobil ke-6 ditolak")
    void capacity() throws Throwable {
        Object s = garage();
        for (int i = 1; i <= 5; i++) {
            assertEquals(true, R.call(s, "addCar", new Car("N " + i, "Car " + i, 2020)));
        }
        assertEquals(false, R.call(s, "addCar", new Car("N 6 F", "Car 6", 2020)));
        assertEquals(5, R.call(s, "getCarCount"));
    }

    @Test
    @DisplayName("addCar(null) ditolak")
    void rejectsNull() throws Throwable {
        Object s = garage();
        assertEquals(false, R.call(s, "addCar", (Object) null));
        assertEquals(0, R.call(s, "getCarCount"));
    }

    @Test
    @DisplayName("findByPlate mengembalikan objek Car yang sama (agregasi), atau null")
    void findByPlate() throws Throwable {
        Object s = garage();
        Car avanza = new Car("L 111 A", "Avanza", 2021);
        Car brio = new Car("L 222 B", "Brio", 2018);
        R.call(s, "addCar", avanza);
        R.call(s, "addCar", brio);
        assertSame(brio, R.call(s, "findByPlate", "L 222 B"));
        assertSame(avanza, R.call(s, "findByPlate", "L 111 A"));
        assertNull(R.call(s, "findByPlate", "L 999 Z"));
    }

    @Test
    @DisplayName("countAvailable mengikuti status mobil yang disewa lewat referensi luar")
    void countAvailable() throws Throwable {
        Object s = garage();
        Car a = new Car("N 1 A", "Avanza", 2021);
        Car b = new Car("N 2 B", "Brio", 2018);
        R.call(s, "addCar", a);
        R.call(s, "addCar", b);
        assertEquals(2, R.call(s, "countAvailable"));
        assertTrue(a.rentOut());
        assertEquals(1, R.call(s, "countAvailable"));
        a.returnVehicle();
        assertEquals(2, R.call(s, "countAvailable"));
        assertTrue(b.isAvailable());
    }
}
