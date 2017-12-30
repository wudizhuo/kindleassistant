package com.kindleassistant.net;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class APIError {

    @SerializedName("message")
    private String message;
}
