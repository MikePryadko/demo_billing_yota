package local.pryadko.demo_billing_yota.repository;

import local.pryadko.demo_billing_yota.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query("select c from Customer c order by c.lastName, c.firstName")
    List<Customer> getAll();
}
