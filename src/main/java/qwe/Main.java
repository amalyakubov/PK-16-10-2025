/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package qwe;

abstract class Aircraft {
    private final String model;
    private final double capacity;
    private final double speed;
    private String status;

    public abstract void performFlight();

    public double getCapacity() {
        return capacity;
    }

    public String getModel() {
        return model;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        String s = "Model: " + this.model + "\n Capacity: " + this.capacity + "\n Speed: " + this.speed;
        return s;
    }

    public Aircraft(String model, double capacity, double speed) {
        this.model = model;
        this.capacity = capacity;
        this.speed = speed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

}

interface Flyable {
    public void fly();
}

interface Serviceable {
    public void service();
}

class PassengerPlane extends Aircraft implements Flyable, Serviceable {
    @Override
    public void performFlight() {
        Thread t = new Thread(() -> {
            try {
                System.out.println("Samolot " + this.getModel() + " wystartował z lotniska");
                Thread.sleep(2000);
                System.out.println("Samolot " + this.getModel() + " jest w locie");
                Thread.sleep(2000);
                System.out.println("Samolot " + this.getModel() + " wylądował na lotnisku docelowym");
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        });
        t.start();
    }

    @Override
    public void fly() {
        performFlight();
    }

    @Override
    public void service() {

    }

    public PassengerPlane(String model, double capacity, double speed) {
        super(model, capacity, speed);
    }

}

class CargoPlane extends Aircraft implements Flyable {
    @Override
    public void performFlight() {
        Thread t = new Thread(() -> {
            try {
                System.out.println("Samolot " + this.getModel() + " wystartował z lotniska z pokładem");
                Thread.sleep(2000);
                System.out.println("Samolot " + this.getModel() + " jest w locie");
                Thread.sleep(2000);
                System.out.println(
                        "Samolot " + this.getModel() + " wylądował na lotnisku docelowym i oczekiuje rozładunku");
                Thread.sleep(2000);
                System.out.println("Samolot " + this.getModel() + " został rozładowany");
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        });
        t.start();
    }

    @Override
    public void fly() {
        performFlight();
    }

    public CargoPlane(String model, double capacity, double speed) {
        super(model, capacity, speed);
    }
}

/**
 *
 * @author Amal Yakubov 4A, Akademickie Liceum przy PJATK 16.10.2025
 */
public class Main {

    public static void main(String[] args) {

    }

}
