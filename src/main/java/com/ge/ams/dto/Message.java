
package com.ge.ams.dto;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Message
 * <p>
 * Error message returned in case of error
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "message",
    "errors",
    "errorId",
    "externalErrorId"
})
public class Message {

    /**
     * message
     * (Required)
     * 
     */
    @JsonProperty("message")
    private String message;
    /**
     * List of error and reason
     * (Required)
     * 
     */
    @JsonProperty("errors")
    private Object errors;
    /**
     * unique error id to match exception at server
     * 
     */
    @JsonProperty("errorId")
    private String errorId;
    /**
     * unique error id to match exception at the external server
     * 
     */
    @JsonProperty("externalErrorId")
    private String externalErrorId;

    /**
     * message
     * (Required)
     * 
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     * message
     * (Required)
     * 
     */
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * List of error and reason
     * (Required)
     * 
     */
    @JsonProperty("errors")
    public Object getErrors() {
        return errors;
    }

    /**
     * List of error and reason
     * (Required)
     * 
     */
    @JsonProperty("errors")
    public void setErrors(Object errors) {
        this.errors = errors;
    }

    /**
     * unique error id to match exception at server
     * 
     */
    @JsonProperty("errorId")
    public String getErrorId() {
        return errorId;
    }

    /**
     * unique error id to match exception at server
     * 
     */
    @JsonProperty("errorId")
    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    /**
     * unique error id to match exception at the external server
     * 
     */
    @JsonProperty("externalErrorId")
    public String getExternalErrorId() {
        return externalErrorId;
    }

    /**
     * unique error id to match exception at the external server
     * 
     */
    @JsonProperty("externalErrorId")
    public void setExternalErrorId(String externalErrorId) {
        this.externalErrorId = externalErrorId;
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
