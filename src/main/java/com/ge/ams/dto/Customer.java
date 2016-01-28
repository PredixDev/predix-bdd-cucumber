
package com.ge.ams.dto;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Customer
 * <p>
 * Customer DTO
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "assetId",
    "uri",
    "tStamp",
    "name",
    "phone"
})
public class Customer {

    /**
     * serial number for Asset
     * 
     */
    @JsonProperty("assetId")
    private String assetId;
    /**
     * System generated uri which can uniquely identify Asset, uri must be null or omitted when send JSon request for creating new Asset, uri must be sent when send JSon request for updating  new Asset, uri is always returned for creating, updating and retrieving Asset
     * 
     */
    @JsonProperty("uri")
    private String uri;
    /**
     * time stamp
     * (Required)
     * 
     */
    @JsonProperty("tStamp")
    private String tStamp;
    /**
     * Name
     * (Required)
     * 
     */
    @JsonProperty("name")
    private String name;
    /**
     * Phone
     * (Required)
     * 
     */
    @JsonProperty("phone")
    private String phone;

    /**
     * serial number for Asset
     * 
     */
    @JsonProperty("assetId")
    public String getAssetId() {
        return assetId;
    }

    /**
     * serial number for Asset
     * 
     */
    @JsonProperty("assetId")
    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    /**
     * System generated uri which can uniquely identify Asset, uri must be null or omitted when send JSon request for creating new Asset, uri must be sent when send JSon request for updating  new Asset, uri is always returned for creating, updating and retrieving Asset
     * 
     */
    @JsonProperty("uri")
    public String getUri() {
        return uri;
    }

    /**
     * System generated uri which can uniquely identify Asset, uri must be null or omitted when send JSon request for creating new Asset, uri must be sent when send JSon request for updating  new Asset, uri is always returned for creating, updating and retrieving Asset
     * 
     */
    @JsonProperty("uri")
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * time stamp
     * (Required)
     * 
     */
    @JsonProperty("tStamp")
    public String getTStamp() {
        return tStamp;
    }

    /**
     * time stamp
     * (Required)
     * 
     */
    @JsonProperty("tStamp")
    public void setTStamp(String tStamp) {
        this.tStamp = tStamp;
    }

    /**
     * Name
     * (Required)
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Name
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Phone
     * (Required)
     * 
     */
    @JsonProperty("phone")
    public String getPhone() {
        return phone;
    }

    /**
     * Phone
     * (Required)
     * 
     */
    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

}
