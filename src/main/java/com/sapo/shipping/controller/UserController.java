package com.sapo.shipping.controller;

import com.sapo.shipping.dto.UserDto;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    GeneralResponse<?> getAllUsers(@RequestParam int pageNumber,
                                            @RequestParam int pageSize) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                userService.getAll(pageNumber, pageSize));
    }

    @GetMapping("{id}")
    GeneralResponse<?> getUserById(@PathVariable int id) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                userService.getById(id));
    }

    @GetMapping("/getFilterShippingOrders")
    GeneralResponse<?> getFilterShippingOrders(@RequestParam(name = "shipperId") Integer shipperId, @RequestParam(name = "statusFilter") String statusFilter) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                userService.getFilteredShippingOrders(shipperId,statusFilter));
    }

    @GetMapping("/statisticAllWarehouses")
    GeneralResponse<?> statisticAllWarehouses() {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                userService.statisticAllWarehouses());
    }

    @PostMapping
    GeneralResponse<?> createUser(@RequestBody UserDto userDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                userService.create(userDto));
    }

    @PutMapping("{id}")
    GeneralResponse<?> updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                userService.update(id, userDto));
    }

    @DeleteMapping("{id}")
    GeneralResponse<?> deleteUser(@PathVariable int id) {
        return GeneralResponse.ok("success",
                "Successfully deleted",
                userService.delete(id));
    }
}
