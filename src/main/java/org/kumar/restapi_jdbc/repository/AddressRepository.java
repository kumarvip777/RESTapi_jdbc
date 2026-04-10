package org.kumar.restapi_jdbc.repository;

import org.kumar.restapi_jdbc.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}