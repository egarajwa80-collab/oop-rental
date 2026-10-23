package id.ac.polinema.uts.rental;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Overriding: subclass mengganti perilaku method yang diwarisi. */
class P07OverrideTest {

    private static Object vehicle() throws Throwable {
        return R.create("Vehicle", "Generic", 2000);
    }

    private static Object motorcycle() throws Throwable {
        return R.create("Motorcycle", "Vario", 2022, 125);
    }

    private static Object truck() throws Throwable {
        return R.create("Truck", "Canter", 2020, 4);
    }

    @Test
    @DisplayName("dailyRate: Vehicle 250000, Car 350000, Motorcycle 100000, Truck mewarisi 250000")
    void dailyRate() throws Throwable {
        assertEquals(250000, R.call(vehicle(), "dailyRate"));
        assertEquals(350000, R.call(new Car("N 1 A", "Avanza", 2021), "dailyRate"));
        assertEquals(100000, R.call(motorcycle(), "dailyRate"));
        assertEquals(250000, R.call(truck(), "dailyRate"));
    }

    @Test
    @DisplayName("Car dan Motorcycle meng-override dailyRate; Truck tidak")
    void whoOverrides() {
        assertTrue(R.declaresMethod(Car.class, "dailyRate"), "Car harus override dailyRate");
        assertTrue(R.declaresMethod(R.cls("Motorcycle"), "dailyRate"), "Motorcycle harus override dailyRate");
        assertFalse(R.declaresMethod(R.cls("Truck"), "dailyRate"),
                "Truck tidak perlu override dailyRate, ia memakai versi Vehicle");
    }

    @Test
    @DisplayName("describe di Vehicle: 'model (year)'")
    void vehicleDescribe() throws Throwable {
        assertEquals("Generic (2000)", R.call(vehicle(), "describe"));
    }

    @Test
    @DisplayName("describe di Car memakai super.describe() lalu menambah ' - merek'")
    void carDescribe() throws Throwable {
        Car c = new Car("N 1 A", "Avanza", 2021);
        assertEquals("Avanza (2021) - Unknown", R.call(c, "describe"));
        R.call(c, "setBrand", new Brand("Toyota", "Japan"));
        assertEquals("Avanza (2021) - Toyota", R.call(c, "describe"));
    }

    @Test
    @DisplayName("describe di Motorcycle menambah kapasitas mesin")
    void motorcycleDescribe() throws Throwable {
        assertEquals("Vario (2022) [125 cc]", R.call(motorcycle(), "describe"));
    }

    @Test
    @DisplayName("describe di Truck memakai versi Vehicle")
    void truckDescribe() throws Throwable {
        assertEquals("Canter (2020)", R.call(truck(), "describe"));
    }

    @Test
    @DisplayName("toString mengembalikan describe() untuk semua jenis kendaraan")
    void toStringUsesDescribe() throws Throwable {
        Object[] vehicles = {vehicle(), new Car("N 1 A", "Avanza", 2021), motorcycle(), truck()};
        for (Object v : vehicles) {
            assertEquals(R.call(v, "describe"), v.toString(),
                    "toString harus mengembalikan describe() pada " + v.getClass().getSimpleName());
        }
    }
}
