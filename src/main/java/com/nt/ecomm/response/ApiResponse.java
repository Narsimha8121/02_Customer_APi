package com.nt.ecomm.response;

import lombok.Data;

@Data
public class ApiResponse<T> {
private Integer status;
private String message;
private T data;
}
