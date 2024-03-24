package com.group1.ecocredit.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class PickupCancelRequest {

    Long id;

    @JsonCreator
    public static PickupCancelRequest fromJson(@JsonProperty("id") Long id) {
        return new PickupCancelRequest(id);
    }
}
