package com.blog.insight_lane.exceptions;

public class DuplicateResourceException extends RuntimeException {
    String resourceName;
    String fieldName;
    String fieldValue;

    public DuplicateResourceException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s already exists with %s : '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}
