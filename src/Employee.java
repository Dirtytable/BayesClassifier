public class Employee extends Person {
    Department depName;
    Position posName;
    int salary;
    Employee(String name, Department depName, Position posName, int salary) {
        super(name);
        this.depName = depName;
        this.posName = posName;
        this.salary = salary;
    }

    public Department getDepName() {
        return depName;
    }

    public Position getPosName() {
        return posName;
    }

    public int getSalary() {
        return salary;
    }

    public void showEmployee(){
        System.out.printf("имя: %s; департамент: %s; положение: %s; зарплата: %d %n", getName(), getDepName().getName(), getPosName().getName(), getSalary());
    }
}
