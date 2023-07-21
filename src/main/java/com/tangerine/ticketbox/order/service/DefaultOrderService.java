package com.tangerine.ticketbox.order.service;

import com.tangerine.ticketbox.order.model.Email;
import com.tangerine.ticketbox.order.repository.OrderRepository;
import com.tangerine.ticketbox.order.service.dto.OrderParam;
import com.tangerine.ticketbox.order.service.dto.OrderResult;
import com.tangerine.ticketbox.order.service.mapper.OrderServiceMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultOrderService implements OrderService {

    private final OrderRepository repository;

    public DefaultOrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createOrder(OrderParam param) {
        repository.insert(OrderServiceMapper.INSTANCE.paramToDomain(param));
    }

    @Override
    public void updateOrder(OrderParam param) {
        repository.update(OrderServiceMapper.INSTANCE.paramToDomain(param));
    }

    @Override
    public void deleteAllOrders() {
        repository.deleteAll();
    }

    @Override
    public void deleteOrderById(UUID orderId) {
        repository.deleteById(orderId);
    }

    @Override
    public List<OrderResult> findAllOrders() {
        return repository.findAll()
                .stream()
                .map(OrderServiceMapper.INSTANCE::domainToResult)
                .toList();
    }

    @Override
    public OrderResult findOrderById(UUID orderId) {
        return OrderServiceMapper.INSTANCE.domainToResult(repository.findById(orderId));
    }

    @Override
    public OrderResult findOrderByEmail(Email email) {
        return OrderServiceMapper.INSTANCE.domainToResult(repository.findByEmail(email));
    }
    
}
