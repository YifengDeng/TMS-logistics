package com.yydscm.Entity;

public class T_logistics_vehicle {
    private Long uuid;
    private Long logistics_uuid;
    private String driver_name;
    private String driver_phone;
    private String driver_id;
    private String license_plate;
    private String vehicle_type;
    private String carrying_capacity;
    private String carriage_volume;
    private java.sql.Timestamp create_time;
    private java.sql.Timestamp update_time;
    private Long vehicle_status;

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getLogistics_uuid() {
        return logistics_uuid;
    }

    public void setLogistics_uuid(Long logistics_uuid) {
        this.logistics_uuid = logistics_uuid;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_phone() {
        return driver_phone;
    }

    public void setDriver_phone(String driver_phone) {
        this.driver_phone = driver_phone;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getCarrying_capacity() {
        return carrying_capacity;
    }

    public void setCarrying_capacity(String carrying_capacity) {
        this.carrying_capacity = carrying_capacity;
    }

    public String getCarriage_volume() {
        return carriage_volume;
    }

    public void setCarriage_volume(String carriage_volume) {
        this.carriage_volume = carriage_volume;
    }

    public java.sql.Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(java.sql.Timestamp create_time) {
        this.create_time = create_time;
    }

    public java.sql.Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(java.sql.Timestamp update_time) {
        this.update_time = update_time;
    }

    public Long getVehicle_status() {
        return vehicle_status;
    }

    public void setVehicle_status(Long vehicle_status) {
        this.vehicle_status = vehicle_status;
    }
}
