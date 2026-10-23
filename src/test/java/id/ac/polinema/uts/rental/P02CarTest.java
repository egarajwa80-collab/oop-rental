package id.ac.polinema.uts.rental;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class P02CarTest {

    @Test
    @DisplayName("Constructor menyimpan plate, model, dan year")
    void constructorAndGetters() {
        Car b = new Car("N 1234 AB", "Avanza", 2021);
        assertEquals("N 1234 AB", b.getPlate());
        assertEquals("Avanza", b.getModel());
        assertEquals(2021, b.getYear());
    }

    @Test
    @DisplayName("Mobil baru langsung tersedia")
    void newCarIsAvailable() {
        assertTrue(new Car("N 1 A", "Avanza", 2021).isAvailable());
    }

    @Test
    @DisplayName("rentOut: berhasil sekali, lalu gagal karena sudah disewa")
    void rentOut() {
        Car b = new Car("N 1 A", "Avanza", 2021);
        assertTrue(b.rentOut());
        assertFalse(b.isAvailable());
        assertFalse(b.rentOut());
    }

    @Test
    @DisplayName("returnVehicle membuat mobil tersedia lagi")
    void returnVehicle() {
        Car b = new Car("N 1 A", "Avanza", 2021);
        b.rentOut();
        b.returnVehicle();
        assertTrue(b.isAvailable());
        assertTrue(b.rentOut());
    }

    @Test
    @DisplayName("Menyewa satu mobil tidak mengubah mobil lain")
    void objectsAreIndependent() {
        Car a = new Car("N 1 A", "Avanza", 2021);
        Car b = new Car("N 2 B", "Brio", 2018);
        a.rentOut();
        assertFalse(a.isAvailable());
        assertTrue(b.isAvailable());
        assertEquals("Brio", b.getModel());
    }
}
