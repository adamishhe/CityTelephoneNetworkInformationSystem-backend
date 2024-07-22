package ru.adamishhe.citytelephonenetworkinformationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Subscriber;

import java.util.List;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    @Query(value = "SELECT sub.last_name, sub.first_name, sub.surname, sub.gender, sub.age, sub.benefit " +
            "FROM ats a " +
            "JOIN phones pnv ON a.ats_id = pnv.ats_id " +
            "JOIN subscriptions subs ON pnv.phone_id = subs.phone_id " +
            "JOIN subscribers sub ON subs.subscriber_id = sub.subscriber_id " +
            "WHERE a.serial_no = :serialNo " +
            "AND sub.age BETWEEN :ageStart AND :ageEnd " +
            "AND sub.benefit >= :benefit " +
            "ORDER BY sub.last_name ASC", nativeQuery = true)
    List<Object[]> findSubscribersByAtsAndBenefitAndAgeRange(
            @Param("serialNo") String serialNo,
            @Param("benefit") double benefit,
            @Param("ageStart") int ageStart,
            @Param("ageEnd") int ageEnd
    );

    @Query(value = "SELECT sub.last_name, sub.first_name, " +
            "(EXTRACT(MONTH FROM AGE(CURRENT_DATE, sc.payment_date)) * s.service_cost) AS debt_amount " +
            "FROM ats a " +
            "JOIN phones pnv ON a.ats_id = pnv.ats_id " +
            "JOIN subscriptions subs ON pnv.phone_id = subs.phone_id " +
            "JOIN subscribers sub ON subs.subscriber_id = sub.subscriber_id " +
            "JOIN service_connection sc ON subs.subscription_id = sc.subscription_id " +
            "JOIN addresses addr ON pnv.address_id = addr.address_id " +
            "JOIN streets st ON addr.street_id = st.street_id " +
            "JOIN districts d ON st.district_id = d.district_id " +
            "JOIN cities c ON d.city_id = c.city_id " +
            "JOIN services s ON sc.service_id = s.service_id " +
            "WHERE a.serial_no = :serialNo " +
            "AND c.city_name = :cityName " +
            "AND d.district_name = :districtName " +
            "AND (CURRENT_DATE - sc.payment_date) >= 31 " +
            "AND s.service_name = 'Междугородний звонок' " +
            "ORDER BY sub.last_name ASC", nativeQuery = true)
    List<Object[]> findSubscribersWithDebt(
            @Param("serialNo") String serialNo,
            @Param("cityName") String cityName,
            @Param("districtName") String districtName
    );

    @Query(value = "SELECT a.type AS ats_type, " +
            "SUM(CASE WHEN sub.benefit = 0 THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS percentage_regular, " +
            "SUM(CASE WHEN sub.benefit > 0 THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS percentage_benefit " +
            "FROM ats a " +
            "JOIN phones p ON a.ats_id = p.ats_id " +
            "JOIN subscriptions subs ON p.phone_id = subs.phone_id " +
            "JOIN subscribers sub ON subs.subscriber_id = sub.subscriber_id " +
            "WHERE a.type IN ('CITY', 'DEPARTMENT', 'INSTITUTION') " +
            "GROUP BY a.type", nativeQuery = true)
    List<Object[]> findSubscriberPercentagesByAtsType();

    @Query(value = "SELECT a.serial_no, " +
            "SUM(CASE WHEN sub.benefit = 0 THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS percentage_regular, " +
            "SUM(CASE WHEN sub.benefit > 0 THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS percentage_benefit " +
            "FROM ats a " +
            "JOIN phones p ON a.ats_id = p.ats_id " +
            "JOIN subscriptions subs ON p.phone_id = subs.phone_id " +
            "JOIN subscribers sub ON subs.subscriber_id = sub.subscriber_id " +
            "WHERE a.serial_no = :serialNo " +
            "GROUP BY a.serial_no", nativeQuery = true)
    List<Object[]> findSubscriberPercentagesByAtsSerial(@Param("serialNo") String serialNo);

    @Query(value = "SELECT c.city_name, d.district_name, " +
            "SUM(CASE WHEN sub.benefit = 0 THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS percentage_regular, " +
            "SUM(CASE WHEN sub.benefit > 0 THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS percentage_benefit " +
            "FROM districts d " +
            "JOIN streets s ON d.district_id = s.district_id " +
            "JOIN addresses addr ON s.street_id = addr.street_id " +
            "JOIN phones p ON addr.address_id = p.address_id " +
            "JOIN ats a ON p.ats_id = a.ats_id " +
            "JOIN subscriptions subs ON p.phone_id = subs.phone_id " +
            "JOIN subscribers sub ON subs.subscriber_id = sub.subscriber_id " +
            "JOIN cities c ON d.city_id = c.city_id " +
            "WHERE c.city_name = :cityName AND d.district_name = :districtName " +
            "GROUP BY c.city_name, d.district_name", nativeQuery = true)
    List<Object[]> findSubscriberPercentagesByCityDistrict(@Param("cityName") String cityName, @Param("districtName") String districtName);

    @Query(value = "SELECT " +
            "a.type AS ats_type, " +
            "sub.first_name, " +
            "sub.last_name, " +
            "sub.surname, " +
            "COUNT(*) OVER (PARTITION BY a.type) AS total_subscribers " +
            "FROM ats a " +
            "JOIN phones p ON a.ats_id = p.ats_id " +
            "JOIN subscriptions subs ON p.phone_id = subs.phone_id " +
            "JOIN subscribers sub ON subs.subscriber_id = sub.subscriber_id " +
            "WHERE a.type IN ('CITY', 'DEPARTMENT', 'INSTITUTION') " +
            "ORDER BY a.type, sub.last_name, sub.first_name, sub.surname", nativeQuery = true)
    List<Object[]> findSubscribersByAtsType();

    @Query(value = "SELECT " +
            "a.serial_no, " +
            "sub.first_name, " +
            "sub.last_name, " +
            "sub.surname, " +
            "COUNT(*) OVER (PARTITION BY a.serial_no) AS total_subscribers " +
            "FROM ats a " +
            "JOIN phones p ON a.ats_id = p.ats_id " +
            "JOIN subscriptions subs ON p.phone_id = subs.phone_id " +
            "JOIN subscribers sub ON subs.subscriber_id = sub.subscriber_id " +
            "WHERE a.serial_no = :serialNo " +
            "ORDER BY sub.last_name, sub.first_name, sub.surname", nativeQuery = true)
    List<Object[]> findSubscribersByAtsSerial(@Param("serialNo") String serialNo);

    @Query(value = "SELECT " +
            "c.city_name, " +
            "d.district_name, " +
            "sub.first_name, " +
            "sub.last_name, " +
            "sub.surname, " +
            "COUNT(*) OVER (PARTITION BY c.city_name, d.district_name) AS total_subscribers " +
            "FROM cities c " +
            "JOIN districts d ON c.city_id = d.city_id " +
            "JOIN streets s ON d.district_id = s.district_id " +
            "JOIN addresses addr ON s.street_id = addr.street_id " +
            "JOIN phones p ON addr.address_id = p.address_id " +
            "JOIN ats a ON p.ats_id = a.ats_id " +
            "JOIN subscriptions subs ON p.phone_id = subs.phone_id " +
            "JOIN subscribers sub ON subs.subscriber_id = sub.subscriber_id " +
            "WHERE c.city_name = :cityName AND d.district_name = :districtName " +
            "ORDER BY sub.last_name, sub.first_name, sub.surname", nativeQuery = true)
    List<Object[]> findSubscribersByCityAndDistrict(@Param("cityName") String cityName, @Param("districtName") String districtName);

    @Query(value = "SELECT " +
            "sub.first_name, " +
            "sub.last_name, " +
            "sub.surname, " +
            "sub.age, " +
            "sub.benefit, " +
            "p.phone_no " +
            "FROM subscribers sub " +
            "JOIN subscriptions subs ON sub.subscriber_id = subs.subscriber_id " +
            "JOIN phones p ON subs.phone_id = p.phone_id " +
            "JOIN ats a ON p.ats_id = a.ats_id " +
            "WHERE sub.benefit > 0 AND p.phone_type = 'PARALLEL' " +
            "ORDER BY sub.last_name, sub.first_name, sub.surname", nativeQuery = true)
    List<Object[]> findBenefitParallelSubscribers();
}
