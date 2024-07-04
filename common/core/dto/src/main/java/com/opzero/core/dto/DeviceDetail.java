package com.opzero.core.dto;

import java.util.Map;

import lombok.Data;

@Data
public class DeviceDetail {
    private String deviceId;
    private String productId;
    private String name;
    private String batchId;
    private String fingerprint;
    private String created;
    private Map<String, String> tags;
    private String privateKey;
    private String pairingSCT;
    private String pairingPassword;
    private String eccKey;
    private String serverConnectToken;
    private String serverKey;
    private boolean attached;
    private boolean deleted;
    private String lastAttach;
    private String lastDetach;

    public DeviceDetail() {

    }

    public DeviceDetail(String deviceId, String productId, String name, String batchId, String fingerprint,
            String created, Map<String, String> tags, String privateKey,
            String pairingSCT, String pairingPassword, String eccKey, String serverConnectToken, String serverKey,
            boolean attached, boolean deleted, String lastAttach, String lastDetach) {
        this.deviceId = deviceId;
        this.productId = productId;
        this.name = name;
        this.batchId = batchId;
        this.fingerprint = fingerprint;
        this.created = created;
        this.tags = tags;
        this.privateKey = privateKey;
        this.pairingSCT = pairingSCT;
        this.pairingPassword = pairingPassword;
        this.eccKey = eccKey;
        this.serverConnectToken = serverConnectToken;
        this.serverKey = serverKey;
        this.attached = attached;
        this.deleted = deleted;
        this.lastAttach = lastAttach;
        this.lastDetach = lastDetach;
    }
}
