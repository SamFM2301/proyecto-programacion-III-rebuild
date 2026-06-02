package models;

public class EmployeeService {
    private int idEmployee;
    private int idService;

    public EmployeeService() {}

    public EmployeeService(int idEmployee, int idService) {
        this.idEmployee = idEmployee;
        this.idService = idService;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }
}