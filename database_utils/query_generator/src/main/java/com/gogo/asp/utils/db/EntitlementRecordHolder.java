package com.gogo.asp.utils.db;

import com.gogo.asp.utils.db.model.Col;

/**
 * @author sramaswamy
 */
public class EntitlementRecordHolder {
    @Col(isPrimaryKey = true, includeInUpdate = false)
    private String userId;

    private String physicalMediaId;

    private Long submittedTime;

    private Long expirationTime;

    private String version;

    private String entitlementRecordData;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getSubmittedTime() {
        return this.submittedTime;
    }

    public void setSubmittedTime(Long submittedTime) {
        this.submittedTime = submittedTime;
    }

    public Long getExpirationTime() {
        return this.expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEntitlementRecordData() {
        return this.entitlementRecordData;
    }

    public void setEntitlementRecordData(String entitlementRecordData) {
        this.entitlementRecordData = entitlementRecordData;
    }

    public String getPhysicalMediaId() {
        return this.physicalMediaId;
    }

    public void setPhysicalMediaId(String physicalMediaId) {
        this.physicalMediaId = physicalMediaId;
    }

}
