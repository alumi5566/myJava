import common.Singleton;

import java.util.*;

enum VehicleType { Bus, Sedan, Bike};
abstract class Vehicle {
    private String licensePlate;
    private VehicleType type;
    Vehicle (VehicleType type, String licensePlate) {
        this.type = type; this.licensePlate = licensePlate;
    }

    public VehicleType getVehicleType() {
        return this.type;
    }
    public String getLicensePlate() {
        return this.licensePlate;
    }
}

class Bus extends Vehicle {
    Bus(String licensePlate){
        super(VehicleType.Bus, licensePlate);
    }
}

class Sedan extends Vehicle {
    Sedan(String licensePlate){
        super(VehicleType.Sedan, licensePlate);
    }
}

class Bike extends Vehicle {
    Bike(String licensePlate){
        super(VehicleType.Bike, licensePlate);
    }
}

class ParkingSpace {
    private int index;
    private VehicleType type;
    private Vehicle vehicle;
    ParkingSpace(int index, VehicleType type){
        this.index = index; this.type = type;
    }
    public boolean Park(Vehicle vehicle){
        if (vehicle.getVehicleType() != this.type) {
            System.out.println("Illegal vehicle type!!");
            return false;
        }
        if (this.vehicle != null) {
            System.out.println("Parking space is occupied!!");
            return false;
        }
        this.vehicle = vehicle;
        return true;
    }
    public boolean Release(){
        if (this.vehicle == null ) {
            System.out.println("Release empty parking space");
            return true;
        }
        this.vehicle = null;
        return true;
    }
    public boolean IsOccupied(){
        return this.vehicle == null;
    }
}

class ParkingLot {
    private HashMap<VehicleType, Queue<ParkingSpace>> availableSpaces;
    private HashMap<Vehicle, ParkingSpace> vehicleSpaces;
    ParkingLot(int BusSpaces, int SedanSpaces, int BikeSpaces){
        availableSpaces = new HashMap<>();
        availableSpaces.put(VehicleType.Bus, new LinkedList<>());
        availableSpaces.put(VehicleType.Sedan, new LinkedList<>());
        availableSpaces.put(VehicleType.Bike, new LinkedList<>());
        for (int i=0;i<BusSpaces;i++){ availableSpaces.get(VehicleType.Bus).add(new ParkingSpace(i, VehicleType.Bus)); }
        for (int i=0;i<SedanSpaces;i++){ availableSpaces.get(VehicleType.Sedan).add(new ParkingSpace(i, VehicleType.Sedan)); }
        for (int i=0;i<BikeSpaces;i++){ availableSpaces.get(VehicleType.Bike).add(new ParkingSpace(i, VehicleType.Bike)); }
        vehicleSpaces = new HashMap<>();
    }
    public boolean Park(Vehicle vehicle) {
        if (availableSpaces.get(vehicle.getVehicleType()).size() == 0 ) {
            System.out.println("No available spaces!!");
            return false;
        }
        if (vehicleSpaces.containsKey(vehicle)) {
            System.out.println("Duplicate vehicle!!");
            return false;
        }
        ParkingSpace ps = availableSpaces.get(vehicle.getVehicleType()).poll();
//        availableSpaces.get(vehicle.getVehicleType()).remove(0);
        ps.Park(vehicle);
        vehicleSpaces.put(vehicle, ps);
        System.out.println("Park[" + vehicle.getVehicleType() + "]: " + vehicle.getLicensePlate());
        return true;
    }
    public boolean Release (Vehicle vehicle){
        if (!vehicleSpaces.containsKey(vehicle)) {
            System.out.println("Vehicle not found!!");
            return false;
        }
        ParkingSpace ps = vehicleSpaces.get(vehicle);
        ps.Release();
        availableSpaces.get(vehicle.getVehicleType()).add(ps);
        System.out.println("Release[" + vehicle.getVehicleType() + "]: " + vehicle.getLicensePlate());
        return true;
    }
//    public boolean IsSpaceAvailable(VehicleType vehicleType){
//
//    }
}

public class OOParkingLot {
    public static void main(String[] args) {
        System.out.println("OOParkingLot!!");
        ParkingLot lot = new ParkingLot(1, 2, 2);
        Vehicle car1 = new Sedan("CAR-123");
        Vehicle car2 = new Sedan("CAR-456");
        Vehicle bike1 = new Bike("BIKE-111");
        Vehicle truck1 = new Bus("TRUCK-999");

        lot.Park(car1);
        lot.Park(car2);
        lot.Park(bike1);
        lot.Park(truck1);

        lot.Park(new Sedan("CAR-789")); // no spot

        lot.Release(car1);
        lot.Park(new Sedan("CAR-789")); // now it can park
    }
}
