package Client.utils;

import Client.collectionElements.Coordinates;
import Client.collectionElements.FuelType;
import Client.collectionElements.Vehicle;
import Client.exceptions.WrongDataException;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Класс управляющий чтением объекта Vehicle
 * @author Андрей
 * */
public class VehicleReader {

    /**
     * Метод для чтения объекта из консоли
     * @param scanner - сканер для чтения из консоли
     * @return vehicle - прочитанный объект
     * */
    public static Vehicle readVehicle(Scanner scanner) {
        boolean incorrectData = true;
        Vehicle vehicle = null;
        while (incorrectData) {
            System.out.println("Введите название транспорта (название не может отсутствовать):");
            String vehicleName;
            while (true) {
                vehicleName = scanner.nextLine();
                if (!vehicleName.isEmpty()) {
                    break;
                } else {
                    System.out.println("Неверное значение поля, введите заново");
                }
            }

            System.out.println("Введите координату x транспортного средства (x <= 232, x - целое число):");
            Integer x;
            while (true) {
                try {
                    x = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Неверное значение поля, введите заново");
                } finally {
                    scanner.nextLine();
                }
            }

            System.out.println("Введите координату y транспортного средства (y <= 281, y - целое число):");
            Long y;
            while (true) {
                try {
                    y = scanner.nextLong();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Неверное значение поля, введите заново");
                } finally {
                    scanner.nextLine();
                }
            }

            System.out.println("Введите мощность двигателя транспортного средства или пустую строку, если двигатель отсутствует (мощность - целое число > 0)");
            Long enginePower = null;
            String pow;
            while (true) {
                pow = scanner.nextLine();
                if (!pow.isEmpty()) {
                    try {
                        enginePower = Long.parseLong(pow);
                        break;
                    } catch (InputMismatchException | NumberFormatException e) {
                        System.out.println("Неверное значение поля, введите заново");
                    }
                } else {
                    break;
                }
            }

            System.out.println("Введите вместительность транспортного средства (вместительность - число > 0, необязательно целое):");
            double capacity;
            while (true) {
                try {
                    capacity = scanner.nextDouble();
                    break;
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Неверное значение поля, введите заново");
                } finally {
                    scanner.nextLine();
                }
            }

            System.out.println("Введите пробег транспортного средства или пустую строку, если пробега нет (пробег - целое число > 0):");
            Long distanceTravelled = null;
            String distTr;
            while (true) {
                distTr = scanner.nextLine();
                if (!distTr.isEmpty()) {
                    try {
                        distanceTravelled = Long.parseLong(distTr);
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Неверное значение поля, введите заново");
                    }
                } else {
                    break;
                }
            }

            System.out.println("Введите тип топлива (ELECTRICITY, DIESEL, MANPOWER, NUCLEAR, ANTIMATTER) (Строка или номер):");
            FuelType fuelType;
            String fuel;
            while (true) {
                fuel = scanner.nextLine();
                try {
                    if (fuel.matches("\\d+")) {
                        fuelType = FuelType.values()[Integer.parseInt(fuel) - 1];
                    }
                    else {
                        fuelType = FuelType.valueOf(fuel);
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Неверное значение поля, введите заново");
                }
            }
            Coordinates coordinates = new Coordinates(x, y);
            vehicle = new Vehicle(vehicleName, coordinates, enginePower, capacity, distanceTravelled, fuelType);
            try {
                vehicle.validate();
                incorrectData = false;
            } catch (WrongDataException e) {
                System.out.println(e.getMessage());
            }
        }
        return vehicle;
    }
}
