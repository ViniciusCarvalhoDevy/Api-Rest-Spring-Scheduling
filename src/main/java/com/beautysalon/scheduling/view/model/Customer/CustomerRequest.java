package com.beautysalon.scheduling.view.model.Customer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class CustomerRequest {
    private Long id;
    private String name;
    
}
