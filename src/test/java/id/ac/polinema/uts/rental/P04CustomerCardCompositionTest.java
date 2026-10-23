package id.ac.polinema.uts.rental;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

/** Komposisi: RentalCard dibuat di dalam Customer dan tidak datang dari luar. */
class P04CustomerCardCompositionTest {

    @Test
    @DisplayName("RentalCard ada dan field-nya private")
    void cardClass() {
        R.assertAllFieldsPrivate(R.cls("RentalCard"));
    }

    @Test
    @DisplayName("Customer punya kartu bernomor 'RC-' + customerId")
    void customerHasCard() throws Throwable {
        Customer m = new Customer("C001", "Dewi");
        Object card = R.call(m, "getCard");
        assertNotNull(card, "Customer harus membuat RentalCard sendiri di constructor");
        assertEquals("RC-C001", R.call(card, "getNumber"));
    }

    @Test
    @DisplayName("getCard selalu mengembalikan kartu yang sama untuk satu Customer")
    void sameCardEveryTime() throws Throwable {
        Customer m = new Customer("C001", "Dewi");
        assertSame(R.call(m, "getCard"), R.call(m, "getCard"));
    }

    @Test
    @DisplayName("Dua Customer punya dua kartu berbeda")
    void differentCustomersDifferentCards() throws Throwable {
        Customer a = new Customer("C001", "Dewi");
        Customer b = new Customer("C002", "Rudi");
        assertNotSame(R.call(a, "getCard"), R.call(b, "getCard"));
        assertEquals("RC-C002", R.call(R.call(b, "getCard"), "getNumber"));
    }

    @Test
    @DisplayName("Constructor Customer tidak menerima RentalCard dari luar (komposisi)")
    void cardIsNotPassedIn() {
        Class<?> card = R.cls("RentalCard");
        for (Constructor<?> k : Customer.class.getConstructors()) {
            for (Class<?> p : k.getParameterTypes()) {
                org.junit.jupiter.api.Assertions.assertNotEquals(card, p,
                        "Customer harus MEMBUAT RentalCard sendiri, bukan menerimanya dari parameter");
            }
        }
    }
}
