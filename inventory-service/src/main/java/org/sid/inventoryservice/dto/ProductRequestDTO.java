package org.sid.inventoryservice.dto;

//Ici on a utilisé un record aulieu d'une classe. On pouvait bien utiliser une classe comme dans ProductRequestDTO1
public record ProductRequestDTO (
        String id,
        String name,
        double price,
        int quantity,
        Long categoryId
){}
