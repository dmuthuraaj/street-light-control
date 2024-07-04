package com.opzero.device.mongo.repository;

import com.opzero.device.mongo.entity.Device;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface DeviceRepository extends MongoRepository<Device, String> {
    Optional<Device> findOneByMacAddress(String macAddress);
}
    