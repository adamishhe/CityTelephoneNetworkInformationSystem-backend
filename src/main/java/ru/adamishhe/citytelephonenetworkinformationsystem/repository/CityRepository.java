package ru.adamishhe.citytelephonenetworkinformationsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.City;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Page<City> findAll(Pageable pageable);

    @Query(value = "SELECT c.city_name, COUNT(*) AS totalIntercityCalls " +
            "FROM services s " +
            "JOIN service_connection sc ON s.service_id = sc.service_id " +
            "JOIN subscriptions subs ON sc.subscription_id = subs.subscription_id " +
            "JOIN phones p ON subs.phone_id = p.phone_id " +
            "JOIN addresses addr ON p.address_id = addr.address_id " +
            "JOIN streets str ON addr.street_id = str.street_id " +
            "JOIN districts d ON str.district_id = d.district_id " +
            "JOIN cities c ON d.city_id = c.city_id " +
            "WHERE s.service_name = 'Междугородний звонок' " +
            "GROUP BY c.city_name " +
            "ORDER BY totalIntercityCalls DESC " +
            "LIMIT 1", nativeQuery = true)
    List<Object[]> findCityWithMostIntercityCalls();
}
