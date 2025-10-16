/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package qwe;


abstract class Aircraft {
    private String model;
    private double capacity;
    private double speed;

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

    public String toString() {
        String s = "Model: " + this.model + "\n Capacity: " + this.capacity + "\n Speed: " + this.speed;
        return s;
    }

}

class PassengerPlane extends Aircraft {
    @Override 
    public void performFlight() {
        System.out.println("Samolot " + this.getModel() + "wystartował z lotniska");
    }
} 

class CargoPlane extends Aircraft {
    @Override
    public void performFlight() {
        System.out.println("Samolot " + this.getModel() + "wystartował z ładunkiem");
    }
}

/**
 *
 * @author l01052
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
    
}
