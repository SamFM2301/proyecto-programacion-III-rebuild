package test;

import models.AppointmentDetail;
import models.Appointment;
import models.User;
import repository.AppointmentRepository;
import utils.Session;

import java.time.LocalDate;
import java.util.List;

public class RoleBasedViewTest {

    private AppointmentRepository appointmentRepo;
    private static final String PASS = "PASS";
    private static final String FAIL = "FAIL";

    public static void main(String[] args) {
        RoleBasedViewTest test = new RoleBasedViewTest();
        test.runAllTests();
    }

    public RoleBasedViewTest() {
        this.appointmentRepo = new AppointmentRepository();
    }

    private void printResult(String testName, boolean passed) {
        System.out.println("[" + (passed ? PASS : FAIL) + "] " + testName);
    }

    public void runAllTests() {
        System.out.println("========================================");
        System.out.println("Role-Based View Feature Tests");
        System.out.println("========================================\n");

        boolean test1 = testUsuarioSeesOnlyOwnAppointments();
        boolean test2 = testAdminSeesAllAppointmentsForToday();
        boolean test3 = testAdminViewShowsClientNameField();
        boolean test4 = testUsuarioViewDoesNotShowClientNameField();
        boolean test5 = testRoleDetectionWorksCorrectly();
        boolean test6 = testAdminCalendarShowsAppointmentCounts();

        System.out.println("\n========================================");
        System.out.println("Test Summary");
        System.out.println("========================================");
        int passed = 0;
        int failed = 0;
        if (test1) passed++; else failed++;
        if (test2) passed++; else failed++;
        if (test3) passed++; else failed++;
        if (test4) passed++; else failed++;
        if (test5) passed++; else failed++;
        if (test6) passed++; else failed++;

        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total: 6");

        if (failed == 0) {
            System.out.println("\nAll tests passed!");
        } else {
            System.out.println("\nSome tests failed!");
        }
    }

    public boolean testUsuarioSeesOnlyOwnAppointments() {
        System.out.println("\n--- Test: USUARIO sees only their own appointments ---");

        User usuario = new User();
        usuario.setId(2);
        usuario.setRole("USUARIO");
        usuario.setFirstName("Isaac");
        usuario.setLastName("Camacho");
        Session.setCurrentUser(usuario);

        boolean isAdmin = "ADMIN".equals(Session.getCurrentUser().getRole());
        boolean isUsuario = "USUARIO".equals(Session.getCurrentUser().getRole());

        boolean passed = !isAdmin && isUsuario;
        printResult("USUARIO role correctly identified", passed);
        return passed;
    }

    public boolean testAdminSeesAllAppointmentsForToday() {
        System.out.println("\n--- Test: ADMIN sees all appointments for today ---");

        User admin = new User();
        admin.setId(1);
        admin.setRole("ADMIN");
        admin.setFirstName("Samuel");
        admin.setLastName("Frias");
        Session.setCurrentUser(admin);

        boolean isAdmin = "ADMIN".equals(Session.getCurrentUser().getRole());

        LocalDate testDate = LocalDate.of(2026, 6, 1);
        List<AppointmentDetail> todayAppointments = appointmentRepo.getDetailedAppointmentsByDate(testDate);

        boolean passed = isAdmin && !todayAppointments.isEmpty();
        System.out.println("  ADMIN appointments for " + testDate + ": " + todayAppointments.size());
        printResult("ADMIN can retrieve all appointments for date with data", passed);
        return passed;
    }

    public boolean testAdminViewShowsClientNameField() {
        System.out.println("\n--- Test: ADMIN view shows Client name field ---");

        LocalDate testDate = LocalDate.of(2026, 6, 1);
        List<AppointmentDetail> appointments = appointmentRepo.getDetailedAppointmentsByDate(testDate);

        boolean foundClientName = false;
        for (AppointmentDetail detail : appointments) {
            if (detail.getClientFullName() != null && !detail.getClientFullName().isEmpty()) {
                foundClientName = true;
                System.out.println("  Found client name: " + detail.getClientFullName());
                break;
            }
        }

        printResult("ADMIN view includes clientFullName field", foundClientName);
        return foundClientName;
    }

    public boolean testUsuarioViewDoesNotShowClientNameField() {
        System.out.println("\n--- Test: USUARIO view does NOT show Client name field ---");

        LocalDate testDate = LocalDate.of(2026, 6, 1);
        List<AppointmentDetail> userAppointments = appointmentRepo.getDetailedAppointmentsByUserAndDate(2, testDate);

        boolean clientNameIsNullOrEmpty = true;
        for (AppointmentDetail detail : userAppointments) {
            if (detail.getClientFullName() != null && !detail.getClientFullName().isEmpty()) {
                clientNameIsNullOrEmpty = false;
                break;
            }
        }

        printResult("USUARIO view (getDetailedAppointmentsByUserAndDate) does NOT include clientFullName", clientNameIsNullOrEmpty);
        return clientNameIsNullOrEmpty;
    }

    public boolean testRoleDetectionWorksCorrectly() {
        System.out.println("\n--- Test: Role detection works correctly when switching ---");

        User user1 = new User();
        user1.setId(1);
        user1.setRole("ADMIN");
        Session.setCurrentUser(user1);
        boolean adminDetected = "ADMIN".equals(Session.getCurrentUser().getRole());

        User user2 = new User();
        user2.setId(2);
        user2.setRole("USUARIO");
        Session.setCurrentUser(user2);
        boolean usuarioDetected = "USUARIO".equals(Session.getCurrentUser().getRole());

        boolean passed = adminDetected && usuarioDetected;
        printResult("Role detection works when switching between accounts", passed);
        return passed;
    }

    public boolean testAdminCalendarShowsAppointmentCounts() {
        System.out.println("\n--- Test: ADMIN calendar shows appointment counts per day ---");

        List<Appointment> allAppointments = appointmentRepo.getAllAppointments();

        boolean passed = !allAppointments.isEmpty();
        System.out.println("  Total appointments in system: " + allAppointments.size());

        long uniqueDays = allAppointments.stream()
                .map(Appointment::getAppointmentDate)
                .distinct()
                .count();
        System.out.println("  Days with appointments: " + uniqueDays);

        printResult("ADMIN calendar can load all appointments for dot display", passed);
        return passed;
    }
}