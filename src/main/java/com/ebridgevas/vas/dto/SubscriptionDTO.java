package com.ebridgevas.vas.dto;

import java.math.BigDecimal;

public class SubscriptionDTO {

    private Long id;
    private String subscriberId;
    private String serviceId;
    private String bundleId;
    private String bundleName;
    private BigDecimal bundlePrice;
    private Long balanceSeconds;
    private BigDecimal balanceValue;

    public SubscriptionDTO(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public SubscriptionDTO(String subscriberId,
                           String serviceId,
                           String bundleId,
                           Long balanceSeconds,
                           BigDecimal balanceValue) {

        this.subscriberId = subscriberId;
        this.serviceId = serviceId;
        this.bundleId = bundleId;
        this.balanceSeconds = balanceSeconds;
        this.balanceValue = balanceValue;
        this.bundleName = bundleName;
        this.bundlePrice = bundlePrice;
    }
    public SubscriptionDTO(
                           Long id,
                           String subscriberId,
                           String serviceId,
                           String bundleId,
                           Long balanceSeconds,
                           BigDecimal balanceValue,
                           String bundleName,
                           BigDecimal bundlePrice) {

        this.id = id;
        this.subscriberId = subscriberId;
        this.serviceId = serviceId;
        this.bundleId = bundleId;
        this.balanceSeconds = balanceSeconds;
        this.balanceValue = balanceValue;
        this.bundleName = bundleName;
        this.bundlePrice = bundlePrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public Long getBalanceSeconds() {
        return balanceSeconds;
    }

    public void setBalanceSeconds(Long balanceSeconds) {
        this.balanceSeconds = balanceSeconds;
    }

    public BigDecimal getBalanceValue() {
        return balanceValue;
    }

    public void setBalanceValue(BigDecimal balanceValue) {
        this.balanceValue = balanceValue;
    }

    public String getBundleName() {
        return bundleName;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }

    public BigDecimal getBundlePrice() {
        return bundlePrice;
    }

    public void setBundlePrice(BigDecimal bundlePrice) {
        this.bundlePrice = bundlePrice;
    }

    @Override
    public String toString() {
        return "SubscriptionDTO{" +
                "subscriberId='" + subscriberId + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", bundleId='" + bundleId + '\'' +
                ", bundleName='" + bundleName + '\'' +
                ", bundlePrice=" + bundlePrice +
                ", balanceSeconds=" + balanceSeconds +
                ", balanceValue=" + balanceValue +
                '}';
    }
}
