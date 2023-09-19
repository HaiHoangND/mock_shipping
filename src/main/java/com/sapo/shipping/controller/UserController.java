package com.sapo.shipping.controller;

import com.sapo.shipping.config.JwtService;
import com.sapo.shipping.dto.UserDto;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    private final UserService userService;

    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    GeneralResponse<?> getAllUsers(@RequestParam int pageNumber,
                                            @RequestParam int pageSize) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                userService.getAll(pageNumber, pageSize));
    }

    @GetMapping("/getAllShippers")
//    @PreAuthorize("hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> getAllShippers(@RequestParam int pageNumber,
                                   @RequestParam int pageSize, @RequestParam(required = false) String keyWord) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                userService.getAllShippers(pageNumber, pageSize, keyWord));
    }

    @GetMapping("/getAllShopOwners")
//    @PreAuthorize("hasRole('ADMIN')")
    GeneralResponse<?> getAllShopOwners(@RequestParam int pageNumber,
                                      @RequestParam int pageSize, @RequestParam(required = false) String keyWord) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                userService.getAllShopOwners(pageNumber, pageSize, keyWord));
    }

    @GetMapping("{id}")
//    @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
//            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> getUserById(@PathVariable int id) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                userService.getById(id));
    }

    @GetMapping("/getFilterShippingOrders")
//    @PreAuthorize("hasRole('SHIPPER') and #shipperId == principal.id")
    GeneralResponse<?> getFilterShippingOrders(
            @RequestParam(name = "shipperId") Integer shipperId, @RequestParam(name = "statusFilter") String statusFilter) {
            return GeneralResponse.ok("success",
                    "Successfully fetched",
                    userService.getFilteredShippingOrders(shipperId,statusFilter));
    }

    @GetMapping("/getAllShopOwnerNoPage")
//    @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
//            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> getAllShopOwnerNoPage( ) {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                userService.getAllShopOwnerNoPage());
    }

    @GetMapping("/getShippersWithStatus")
//    @PreAuthorize("hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> getShippersWithStatus() {
        return GeneralResponse.ok("success",
                "Successfully fetched",
                userService.getShippersWithStatus());
    }

    @PostMapping
//    @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
//            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> createUser(@RequestBody UserDto userDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                userService.create(userDto));
    }

    @PutMapping("{id}")
//    @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
//            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        return GeneralResponse.ok("success",
                "Successfully created",
                userService.update(id, userDto));
    }

    @DeleteMapping("{id}")
//    @PreAuthorize("hasRole('SHIPPER') or hasRole('SHOP') " +
//            "or hasRole('COORDINATOR') or hasRole('ADMIN')")
    GeneralResponse<?> deleteUser(@PathVariable int id) {
        return GeneralResponse.ok("success",
                "Successfully deleted",
                userService.delete(id));
    }
}
