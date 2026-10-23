package id.ac.polinema.uts.rental;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Inheritance: Car, Motorcycle, dan Truck adalah Vehicle (IS-A). */
class P06InheritanceTest {

    private static Object motorcycle() throws Throwable {
        return R.create("Motorcycle", "Vario", 2022, 125);
    }

    private static Object truck() throws Throwable {
        return R.create("Truck", "Canter", 2020, 4);
    }

    @Test
    @DisplayName("Car, Motorcycle, dan Truck extends Vehicle")
    void superclass() {
        Class<?> parent = R.cls("Vehicle");
        assertEquals(parent, Car.class.getSuperclass(), "Car harus extends Vehicle");
        assertEquals(parent, R.cls("Motorcycle").getSuperclass(), "Motorcycle harus extends Vehicle");
        assertEquals(parent, R.cls("Truck").getSuperclass(), "Truck harus extends Vehicle");
    }

    @Test
    @DisplayName("Vehicle menyimpan model dan year sebagai protected, dan tidak ada field public")
    void vehicleFields() {
        Class<?> parent = R.cls("Vehicle");
        for (Field f : parent.getDeclaredFields()) {
            if (f.isSynthetic()) {
                continue;
            }
            assertFalse(Modifier.isPublic(f.getModifiers()), "Field " + f.getName() + " tidak boleh public");
        }
        assertTrue(Modifier.isProtected(field(parent, "model").getModifiers()), "model harus protected");
        assertTrue(Modifier.isProtected(field(parent, "year").getModifiers()), "year harus protected");
    }

    private static Field field(Class<?> c, String name) {
        try {
            return c.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return org.junit.jupiter.api.Assertions.fail("Field " + name + " tidak ada di " + c.getSimpleName());
        }
    }

    @Test
    @DisplayName("Car tidak menyimpan ulang model, year, dan available (sudah ada di Vehicle)")
    void carDoesNotRepeatFields() {
        for (Field f : Car.class.getDeclaredFields()) {
            String n = f.getName();
            assertFalse(n.equals("model") || n.equals("year") || n.equals("available"),
                    "Field " + n + " sudah ada di Vehicle. Hapus dari Car.");
        }
        R.assertAllFieldsPrivate(Car.class);
    }

    @Test
    @DisplayName("Method umum dipindah ke Vehicle, bukan ditulis ulang di subclass")
    void methodsMovedUp() {
        for (String name : new String[] {"getModel", "getYear", "isAvailable", "rentOut", "returnVehicle"}) {
            assertFalse(R.declaresMethod(Car.class, name), "Car tidak perlu mendeklarasikan " + name);
            assertFalse(R.declaresMethod(R.cls("Motorcycle"), name), "Motorcycle tidak perlu mendeklarasikan " + name);
            assertTrue(R.declaresMethod(R.cls("Vehicle"), name), "Vehicle harus punya " + name);
        }
    }

    @Test
    @DisplayName("Vehicle(model, year) menyimpan data dan memvalidasinya")
    void vehicleConstructor() throws Throwable {
        Object vehicle = R.create("Vehicle", "Generic", 2000);
        assertEquals("Generic", R.call(vehicle, "getModel"));
        assertEquals(2000, R.call(vehicle, "getYear"));
        assertEquals(true, R.call(vehicle, "isAvailable"));
        assertThrows(IllegalArgumentException.class, () -> R.create("Vehicle", " ", 2000));
        assertThrows(IllegalArgumentException.class, () -> R.create("Vehicle", "Generic", 1989));
    }

    @Test
    @DisplayName("Motorcycle menyimpan kapasitas mesin (cc) dan memakai getter turunan dari Vehicle")
    void motorcycleKeepsEngineCc() throws Throwable {
        Object d = motorcycle();
        assertEquals("Vario", R.call(d, "getModel"));
        assertEquals(2022, R.call(d, "getYear"));
        assertEquals(125, R.call(d, "getEngineCc"));
        R.assertAllFieldsPrivate(R.cls("Motorcycle"));
    }

    @Test
    @DisplayName("Truck menyimpan muatan (ton) dan memakai getter turunan")
    void truckKeepsLoad() throws Throwable {
        Object m = truck();
        assertEquals("Canter", R.call(m, "getModel"));
        assertEquals(4, R.call(m, "getLoadTons"));
        R.assertAllFieldsPrivate(R.cls("Truck"));
    }

    @Test
    @DisplayName("rentOut dan returnVehicle diwarisi oleh semua subclass")
    void inheritedBehaviour() throws Throwable {
        Object[] vehicles = {new Car("N 1 A", "Avanza", 2021), motorcycle(), truck()};
        for (Object vehicle : vehicles) {
            assertEquals(true, R.call(vehicle, "isAvailable"));
            assertEquals(true, R.call(vehicle, "rentOut"));
            assertEquals(false, R.call(vehicle, "rentOut"));
            R.call(vehicle, "returnVehicle");
            assertEquals(true, R.call(vehicle, "isAvailable"));
        }
    }

    @Test
    @DisplayName("Validasi setter ikut diwarisi: setModel kosong ditolak di Motorcycle")
    void validationInherited() throws Throwable {
        Object d = motorcycle();
        assertThrows(IllegalArgumentException.class, () -> R.call(d, "setModel", ""));
        assertThrows(IllegalArgumentException.class, () -> R.call(d, "setYear", 1985));
        assertEquals("Vario", R.call(d, "getModel"));
    }

    @Test
    @DisplayName("Setiap subclass adalah turunan Vehicle (extends)")
    void isInstance() throws Throwable {
        Class<?> parent = R.cls("Vehicle");
        assertTrue(parent.isInstance(new Car("N 1 A", "Avanza", 2021)));
        assertTrue(parent.isInstance(motorcycle()));
        assertTrue(parent.isInstance(truck()));
    }
}
