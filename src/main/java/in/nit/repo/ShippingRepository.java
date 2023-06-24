package in.nit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nit.model.Shipping;

public interface ShippingRepository extends JpaRepository<Shipping, Integer> {

}
