package qwe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.SwingUtilities;

abstract class Aircraft {
    private final String model;
    private final double capacity;
    private final double speed;
    private volatile String status;
    private Thread flightThread;
    private final PropertyChangeSupport support;

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
        return "Model: " + this.model + "\nCapacity: " + this.capacity + "\nSpeed: " + this.speed;
    }

    public Aircraft(String model, double capacity, double speed) {
        this.model = model;
        this.capacity = capacity;
        this.speed = speed;
        this.status = "Oczekuje";
        this.support = new PropertyChangeSupport(this);
    }

    public synchronized String getStatus() {
        return status;
    }

    public synchronized void setStatus(String newStatus) {
        String oldStatus = this.status;
        this.status = newStatus;
        SwingUtilities.invokeLater(() -> support.firePropertyChange("status", oldStatus, newStatus));
    }

    public boolean isFlying() {
        return flightThread != null && flightThread.isAlive();
    }

    protected void setFlightThread(Thread thread) {
        this.flightThread = thread;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
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
                this.setStatus("Samolot " + this.getModel() + " wystartował z lotniska");
                Thread.sleep(2000);
                this.setStatus("Samolot " + this.getModel() + " jest w locie");
                Thread.sleep(2000);
                this.setStatus("Samolot " + this.getModel() + " wylądował na lotnisku docelowym");
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
                this.setStatus("Samolot " + this.getModel() + " wystartował z lotniska z pokładem");
                Thread.sleep(1000);
                this.setStatus("Samolot " + this.getModel() + " jest w locie");
                Thread.sleep(1000);
                this.setStatus(
                        "Samolot " + this.getModel() + " wylądował na lotnisku docelowym i oczekiuje rozładunku");
                Thread.sleep(1000);
                this.setStatus("Samolot " + this.getModel() + " został rozładowany");
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
