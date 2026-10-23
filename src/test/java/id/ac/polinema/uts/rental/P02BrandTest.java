package id.ac.polinema.uts.rental;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class P02BrandTest {

    @Test
    @DisplayName("Constructor menyimpan nama dan negara, getter mengembalikannya")
    void constructorAndGetters() {
        Brand a = new Brand("Toyota", "Japan");
        assertEquals("Toyota", a.getName());
        assertEquals("Japan", a.getCountry());
    }

    @Test
    @DisplayName("getInfo mengembalikan 'nama (negara)'")
    void getInfo() {
        Brand a = new Brand("Wuling", "China");
        assertEquals("Wuling (China)", a.getInfo());
    }

    @Test
    @DisplayName("Dua objek Brand punya data sendiri-sendiri")
    void objectsAreIndependent() {
        Brand a = new Brand("Toyota", "Japan");
        Brand b = new Brand("Wuling", "China");
        assertNotEquals(a.getName(), b.getName());
        assertEquals("Toyota", a.getName());
        assertEquals("China", b.getCountry());
    }
}
