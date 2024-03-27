package com.group1.ecocredit.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class PickupActionRequest {

    Long id;

    @JsonCreator
    public static PickupActionRequest fromJson(@JsonProperty("id") Long id) {
        return new PickupActionRequest(id);
    }
}
