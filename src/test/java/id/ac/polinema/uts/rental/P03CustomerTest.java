package id.ac.polinema.uts.rental;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class P03CustomerTest {

    @Test
    @DisplayName("Semua field Customer private")
    void fieldsPrivate() {
        R.assertAllFieldsPrivate(Customer.class);
    }

    @Test
    @DisplayName("Constructor menyimpan id dan nama; jumlah sewa awal 0")
    void constructor() {
        Customer m = new Customer("C001", "Dewi");
        assertEquals("C001", m.getCustomerId());
        assertEquals("Dewi", m.getName());
        assertEquals(0, m.getRentalCount());
    }

    @Test
    @DisplayName("Constructor menolak id atau nama null/kosong")
    void constructorValidates() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("", "Dewi"));
        assertThrows(IllegalArgumentException.class, () -> new Customer(null, "Dewi"));
        assertThrows(IllegalArgumentException.class, () -> new Customer("C001", "  "));
        assertThrows(IllegalArgumentException.class, () -> new Customer("C001", null));
    }

    @Test
    @DisplayName("customerId dan rentalCount read-only: tidak ada setter")
    void readOnlyFields() {
        assertFalse(R.hasPublicMethod(Customer.class, "setCustomerId"));
        assertFalse(R.hasPublicMethod(Customer.class, "setRentalCount"));
    }

    @Test
    @DisplayName("setName menolak nama kosong dan menjaga nama lama")
    void setName() {
        Customer m = new Customer("C001", "Dewi");
        assertThrows(IllegalArgumentException.class, () -> m.setName(""));
        assertThrows(IllegalArgumentException.class, () -> m.setName(null));
        assertEquals("Dewi", m.getName());
        m.setName("Dewi Lestari");
        assertEquals("Dewi Lestari", m.getName());
    }

    @Test
    @DisplayName("addRental sampai batas 2; sewa ke-3 ditolak")
    void addRentalLimit() {
        Customer m = new Customer("C001", "Dewi");
        assertTrue(m.addRental());
        assertTrue(m.addRental());
        assertFalse(m.addRental());
        assertEquals(2, m.getRentalCount());
    }

    @Test
    @DisplayName("canRent dihitung dari jumlah sewa (derived getter)")
    void canRentIsDerived() {
        Customer m = new Customer("C001", "Dewi");
        assertTrue(m.canRent());
        m.addRental();
        assertTrue(m.canRent());
        m.addRental();
        assertFalse(m.canRent());
        m.returnRental();
        assertTrue(m.canRent());
    }

    @Test
    @DisplayName("returnRental tidak boleh membuat jumlah sewa negatif")
    void returnRentalFloor() {
        Customer m = new Customer("C001", "Dewi");
        assertFalse(m.returnRental());
        assertEquals(0, m.getRentalCount());
        m.addRental();
        assertTrue(m.returnRental());
        assertEquals(0, m.getRentalCount());
    }
}
