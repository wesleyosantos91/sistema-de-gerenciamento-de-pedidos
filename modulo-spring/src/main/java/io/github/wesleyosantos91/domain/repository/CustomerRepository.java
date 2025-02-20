package io.github.wesleyosantos91.domain.repository;

import io.github.wesleyosantos91.domain.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    @Query("SELECT c FROM CustomerEntity c WHERE c.id = :id AND c.tbOrders IS NOT EMPTY")
    Optional<CustomerEntity> findCustomerWithoutOrdersById(@Param("id") UUID id);
}
