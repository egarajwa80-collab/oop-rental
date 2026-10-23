package id.ac.polinema.uts.rental;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class P03EncapsulationTest {

    @Test
    @DisplayName("Semua field Car private")
    void carFieldsPrivate() {
        R.assertAllFieldsPrivate(Car.class);
    }

    @Test
    @DisplayName("Semua field Brand private")
    void brandFieldsPrivate() {
        R.assertAllFieldsPrivate(Brand.class);
    }

    @Test
    @DisplayName("plate read-only: Car tidak punya setPlate, dan status tidak bisa di-set langsung")
    void readOnlyFields() {
        assertFalse(R.hasPublicMethod(Car.class, "setPlate"), "plate tidak boleh punya setter");
        assertFalse(R.hasPublicMethod(Car.class, "setAvailable"),
                "status tersedia hanya boleh berubah lewat rentOut dan returnVehicle");
    }

    @Test
    @DisplayName("setModel menolak null, kosong, dan hanya spasi; model lama tetap")
    void setModelRejectsBlank() {
        Car b = new Car("N 1 A", "Avanza", 2021);
        assertThrows(IllegalArgumentException.class, () -> b.setModel(null));
        assertThrows(IllegalArgumentException.class, () -> b.setModel(""));
        assertThrows(IllegalArgumentException.class, () -> b.setModel("   "));
        assertEquals("Avanza", b.getModel());
    }

    @Test
    @DisplayName("setModel menerima model yang valid")
    void setModelAcceptsValid() {
        Car b = new Car("N 1 A", "Avanza", 2021);
        b.setModel("Avanza Veloz");
        assertEquals("Avanza Veloz", b.getModel());
    }

    @Test
    @DisplayName("setYear menolak tahun di bawah 1990 (termasuk 0 dan negatif); tahun lama tetap")
    void setYearRejectsBefore1990() {
        Car b = new Car("N 1 A", "Avanza", 2021);
        assertThrows(IllegalArgumentException.class, () -> b.setYear(0));
        assertThrows(IllegalArgumentException.class, () -> b.setYear(-5));
        assertThrows(IllegalArgumentException.class, () -> b.setYear(1989));
        assertEquals(2021, b.getYear());
        b.setYear(1990);
        assertEquals(1990, b.getYear());
    }

    @Test
    @DisplayName("Constructor Car memvalidasi plate, model, dan year")
    void constructorValidates() {
        assertThrows(IllegalArgumentException.class, () -> new Car("", "Avanza", 2021));
        assertThrows(IllegalArgumentException.class, () -> new Car(null, "Avanza", 2021));
        assertThrows(IllegalArgumentException.class, () -> new Car("N 1 A", " ", 2021));
        assertThrows(IllegalArgumentException.class, () -> new Car("N 1 A", "Avanza", 0));
        assertThrows(IllegalArgumentException.class, () -> new Car("N 1 A", "Avanza", 1989));
    }
}
