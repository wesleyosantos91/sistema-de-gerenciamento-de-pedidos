package io.github.wesleyosantos91.domain.repository;

import io.github.wesleyosantos91.domain.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {


    @Query("SELECT c FROM CustomerEntity c WHERE "
            + "(:#{#query.id} IS NULL OR c.id = :#{#query.id}) "
            + "AND (:#{#query.name} IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :#{#query.name}, '%'))) "
            + "AND (:#{#query.email} IS NULL OR LOWER(c.email) = LOWER(:#{#query.email}))")
    Page<CustomerEntity> search(@Param("query") CustomerEntity query, Pageable pageable);

    @Query("SELECT c FROM CustomerEntity c WHERE c.id = :id AND c.tbOrders IS NOT EMPTY")
    Optional<CustomerEntity> findCustomerWithoutOrdersById(@Param("id") UUID id);
}
