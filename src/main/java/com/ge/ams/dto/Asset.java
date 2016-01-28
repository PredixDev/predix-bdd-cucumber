
package com.ge.ams.dto;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Asset
 * <p>
 * Asset which is the instance of Type
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "uri",
    "description",
    "obsolete",
    "assetId"
})
public class Asset {

    /**
     * System generated uri which can uniquely identify Asset, uri must be null or omitted when send JSon request for creating new Asset, uri must be sent when send JSon request for updating  new Asset, uri is always returned for creating, updating and retrieving Asset
     * 
     */
    @JsonProperty("uri")
    private String uri;
    /**
     * Description for the domain object
     * 
     */
    @JsonProperty("description")
    private String description;
    /**
     * Obsolete/non-obsolete Asset
     * (Required)
     * 
     */
    @JsonProperty("obsolete")
    private Boolean obsolete = false;
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
     * Description for the domain object
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * Description for the domain object
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obsolete/non-obsolete Asset
     * (Required)
     * 
     */
    @JsonProperty("obsolete")
    public Boolean getObsolete() {
        return obsolete;
    }

    /**
     * Obsolete/non-obsolete Asset
     * (Required)
     * 
     */
    @JsonProperty("obsolete")
    public void setObsolete(Boolean obsolete) {
        this.obsolete = obsolete;
    }

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
