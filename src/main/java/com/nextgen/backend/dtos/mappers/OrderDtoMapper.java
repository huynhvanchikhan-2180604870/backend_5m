package com.nextgen.backend.dtos.mappers;

import com.nextgen.backend.dtos.OrderDTO;
import com.nextgen.backend.entites.Order;
import com.nextgen.backend.entites.User;
import com.nextgen.backend.services.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderDtoMapper {
    @Autowired
    private UserServiceInterface userServiceInterface;

    public static OrderDTO entityToDto(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setUser_id(order.getUser().getId());
        return dto;
    }

    public static List<OrderDTO> entityToDtos(List<Order> orders){
        List<OrderDTO> dtos = new ArrayList<>();
        for(Order i : orders){
            OrderDTO dto = entityToDto(i);
            dtos.add(dto);
        }
        return dtos;
    }
}
