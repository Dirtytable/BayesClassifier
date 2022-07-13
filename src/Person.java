public class Person {
    String name;
    Person(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    /*Scanner scanner = new Scanner(System.in);
        boolean isDone = false;
        ArrayList<Employee> employees = new ArrayList<>();
        while (!isDone){
            System.out.println("Введите номер действия");
            System.out.println("1. Запись сотрудника");
            System.out.println("2. Показать список сотрудников");
            System.out.println("3. Выйти");
            int choice = scanner.nextInt();
            switch (choice){
                case 1-> {
                    System.out.println("Запись");
                    System.out.println("Введите Имя сотрудника");
                    String name = scanner.next() + scanner.nextLine();
                    System.out.println("Введите название департамента сотрудника");
                    Department department = new Department(scanner.next());
                    System.out.println("Введите название положение сотрудника");
                    Position position = new Position(scanner.next());
                    System.out.println("Введите зарплату сортрудника");
                    int salary = scanner.nextInt();
                    employees.add(new Employee(name, department, position, salary));
                }
                case 2->{
                    for (int i = 0; i < employees.size(); i++) {
                        System.out.print((i+1) + ". Сотрудник - ");
                        employees.get(i).showEmployee();
                    }
                }
                case 3->
                    isDone = true;
                default ->
                    System.out.println("Непрвалиьный ввод");
            }


        }

         */
    /*
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Асет Кошкын", new Department("Первый"), new Position("Главное"), 666));
        employees.add(new Employee("Журба Максим", new Department("Второе"), new Position("Вице-президент"), 333));
        employees.add(new Employee("Жора Тарлавин", new Department("Второе"), new Position("Капитан"), 222));
        employees.add(new Employee("Едыль Исахмет", new Department("Третье"), new Position("Смотрящий"), 1313));

        for (int i = 0; i < employees.size(); i++) {
            System.out.print((i+1) + ". Сотрудник - ");
            employees.get(i).showEmployee();
        }

         */
}
