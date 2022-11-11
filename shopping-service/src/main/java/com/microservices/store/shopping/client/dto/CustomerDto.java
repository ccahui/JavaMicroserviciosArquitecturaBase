package com.microservices.store.shopping.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {


    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}