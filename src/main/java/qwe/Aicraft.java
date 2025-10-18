package qwe;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

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
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                publish("Samolot " + getModel() + " wystartował z lotniska");
                Thread.sleep(1000);
                publish("Samolot " + getModel() + " jest w locie");
                Thread.sleep(1000);
                publish("Samolot " + getModel() + " wylądował na lotnisku docelowym");

                return null;

            }

            @Override
            protected void process(List<String> chunks) {
                for (String status : chunks) {
                    setStatus(status);
                }
            }
        };
        worker.execute();
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
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                publish("Samolot " + getModel() + " wystartował z lotniska z pokładem");
                Thread.sleep(1000);
                publish("Samolot " + getModel() + " jest w locie");
                Thread.sleep(1000);
                publish("Samolot " + getModel() + " wylądował na lotnisku docelowym i oczekiwa rozładunku");
                Thread.sleep(1000);
                publish("Samolot " + getModel() + " został rozładowany");
                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                for (String status : chunks) {
                    setStatus(status);
                }
            }
        };
        worker.execute();
    }

    @Override
    public void fly() {
        performFlight();
    }

    public CargoPlane(String model, double capacity, double speed) {
        super(model, capacity, speed);
    }
}
