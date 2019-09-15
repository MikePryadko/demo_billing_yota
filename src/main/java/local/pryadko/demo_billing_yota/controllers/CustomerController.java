package local.pryadko.demo_billing_yota.controllers;

import local.pryadko.demo_billing_yota.entities.Customer;
import local.pryadko.demo_billing_yota.entities.Simcard;
import local.pryadko.demo_billing_yota.exceptions.BadRequestException;
import local.pryadko.demo_billing_yota.exceptions.ConflictException;
import local.pryadko.demo_billing_yota.exceptions.NotFoundException;
import local.pryadko.demo_billing_yota.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.management.StandardEmitterMBean;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping
    public Iterable<Customer> getAll() {
        return customerRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomer(@RequestParam String firstName, @RequestParam String lastName) {
        if (firstName.length() < 2)
            throw new BadRequestException("Too short first name");
        if (lastName.length() < 2)
            throw new BadRequestException("Too short last name");

        try {
            customerRepository.save(new Customer(firstName, lastName));
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Customer " + firstName + " " + lastName + " already exists");
        }
    }

    @PostMapping("/simcard")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSimcard(@RequestParam long customerId, @RequestParam long phoneNumber) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer with ID " + customerId + " not exists"));
        customer.getSimcards().add(new Simcard(phoneNumber));
        try {
            customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Simcard with number +" + phoneNumber + " already exists");
        }
    }
}
