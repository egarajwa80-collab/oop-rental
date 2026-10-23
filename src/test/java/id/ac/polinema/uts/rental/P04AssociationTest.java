package id.ac.polinema.uts.rental;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/** Asosiasi: Car mengenal Brand, tetapi Brand hidup sendiri. */
class P04AssociationTest {

    @Test
    @DisplayName("Mobil baru belum punya merek (null)")
    void newCarHasNoBrand() throws Throwable {
        Car b = new Car("N 1 A", "Avanza", 2021);
        assertNull(R.call(b, "getBrand"));
    }

    @Test
    @DisplayName("getBrand mengembalikan objek Brand yang sama (asosiasi)")
    void sameBrandObject() throws Throwable {
        Car b = new Car("N 1 A", "Avanza", 2021);
        Brand toyota = new Brand("Toyota", "Japan");
        R.call(b, "setBrand", toyota);
        assertSame(toyota, R.call(b, "getBrand"));
    }

    @Test
    @DisplayName("Dua mobil bisa berbagi satu Brand")
    void carsShareBrand() throws Throwable {
        Car a = new Car("N 1 A", "Avanza", 2021);
        Car b = new Car("N 2 B", "Avanza Veloz", 2023);
        Brand toyota = new Brand("Toyota", "Japan");
        R.call(a, "setBrand", toyota);
        R.call(b, "setBrand", toyota);
        assertSame(R.call(a, "getBrand"), R.call(b, "getBrand"));
        assertEquals("Toyota", toyota.getName());
    }

    @Test
    @DisplayName("setBrand(null) melepas merek")
    void clearBrand() throws Throwable {
        Car b = new Car("N 1 A", "Avanza", 2021);
        R.call(b, "setBrand", new Brand("Toyota", "Japan"));
        R.call(b, "setBrand", (Object) null);
        assertNull(R.call(b, "getBrand"));
    }
}
