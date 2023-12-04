import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

class Laptop {
    int ram;
    int hardDriveSize;
    String operatingSystem;
    String color;

    public Laptop(int ram, int hardDriveSize, String operatingSystem, String color) {
        this.ram = ram;
        this.hardDriveSize = hardDriveSize;
        this.operatingSystem = operatingSystem;
        this.color = color;
    }

    public int getRam() {
        return ram;
    }

    public int getHardDriveSize() {
        return hardDriveSize;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public String getColor() {
        return color;
    }

    public boolean satisfiesFilter(Map<String, Object> filters) {
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String criterion = entry.getKey();
            Object value = entry.getValue();

            switch (criterion) {
                case "ram":
                    if (ram < (int) value) {
                        return false;
                    }
                    break;
                case "hardDriveSize":
                    if (hardDriveSize < (int) value) {
                        return false;
                    }
                    break;
                case "operatingSystem":
                    if (!operatingSystem.equalsIgnoreCase((String) value)) {
                        return false;
                    }
                    break;
                case "color":
                    if (!color.equalsIgnoreCase((String) value)) {
                        return false;
                    }
                    break;
                default:
                    System.out.println("Неподдерживаемый критерий фильтрации: " + criterion);
                    return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("RAM: %d GB, HDD: %d GB, OS: %s, Цвет: %s", ram, hardDriveSize, operatingSystem,
                color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ram, hardDriveSize, operatingSystem, color);
    }
}

public class Store {
    public static void main(String[] args) {
        Store store = new Store();

        store.addLaptop("Workstation", new Laptop(8, 500, "Windows 10", "Black"));
        store.addLaptop("Laptop", new Laptop(16, 1000, "macOS", "Silver"));
        store.addLaptop("Server", new Laptop(4, 256, "Linux", "White"));
        store.addLaptop("GamingPC", new Laptop(32, 2000, "Windows 10", "Blue"));
        store.addLaptop("BusinessLaptop", new Laptop(12, 750, "Windows 10", "Silver"));
        store.addLaptop("Ultrabook", new Laptop(8, 512, "Windows 10", "Gray"));
        store.addLaptop("DeveloperWorkstation", new Laptop(32, 2000, "Linux", "Black"));
        store.addLaptop("MultimediaLaptop", new Laptop(16, 1500, "Windows 10", "Red"));

        store.filterLaptops();
    }

    private Map<String, Laptop> laptopMap = new HashMap<>();

    public void addLaptop(String name, Laptop laptop) {
        laptopMap.put(name, laptop);
    }

    public void filterLaptops() {
        Scanner scanner = new Scanner(System.in);
        Map<String, Object> filters = new HashMap<>();

        System.out.println("Введите критерии фильтрации:");
        System.out.println("1 - ОЗУ");
        System.out.println("2 - Объем ЖД");
        System.out.println("3 - Операционная система");
        System.out.println("4 - Цвет");

        int criterion = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите минимальные значения для выбранного критерия:");

        switch (criterion) {
            case 1:
                System.out.print("Минимальный объем ОЗУ (в ГБ): ");
                filters.put("ram", scanner.nextInt());
                break;
            case 2:
                System.out.print("Минимальный объем жесткого диска (в ГБ): ");
                filters.put("hardDriveSize", scanner.nextInt());
                break;
            case 3:
                System.out.print("Операционная система: ");
                filters.put("operatingSystem", scanner.nextLine());
                break;
            case 4:
                System.out.print("Цвет: ");
                filters.put("color", scanner.nextLine());
                break;
            default:
                System.out.println("Неверный ввод");
                return;
        }

        laptopMap.entrySet().stream()
                .filter(entry -> entry.getValue().satisfiesFilter(filters))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

    }
}