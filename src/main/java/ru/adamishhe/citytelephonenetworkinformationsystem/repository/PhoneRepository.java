package ru.adamishhe.citytelephonenetworkinformationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Phone;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    @Query(value = "SELECT pnv.phone_no, st.street_name, addr.house_no " +
            "FROM phones pnv " +
            "LEFT JOIN subscriptions subs ON pnv.phone_id = subs.phone_id " +
            "JOIN addresses addr ON pnv.address_id = addr.address_id " +
            "JOIN streets st ON addr.street_id = st.street_id " +
            "JOIN districts d ON st.district_id = d.district_id " +
            "JOIN cities c ON d.city_id = c.city_id " +
            "JOIN ats a ON pnv.ats_id = a.ats_id " +
            "WHERE subs.phone_id IS NULL " +
            "AND a.serial_no = :serialNo " +
            "AND c.city_name = :cityName " +
            "AND d.district_name = :districtName", nativeQuery = true)
    List<Object[]> findFreePhonesByAtsCityDistrict(
            @Param("serialNo") String serialNo,
            @Param("cityName") String cityName,
            @Param("districtName") String districtName
    );

    @Query(value = "SELECT c.city_name, d.district_name, a.serial_no, p.phone_no, COUNT(p.phone_no) AS total_payphones " +
            "FROM ats a " +
            "JOIN phones p ON a.ats_id = p.ats_id " +
            "JOIN addresses addr ON p.address_id = addr.address_id " +
            "JOIN streets s ON addr.street_id = s.street_id " +
            "JOIN districts d ON s.district_id = d.district_id " +
            "JOIN cities c ON d.city_id = c.city_id " +
            "WHERE a.serial_no = :serialNo " +
            "AND p.phone_type = 'PAYPHONE' " +
            "AND c.city_name = :cityName " +
            "AND d.district_name = :districtName " +
            "GROUP BY c.city_name, d.district_name, a.serial_no, p.phone_no " +
            "ORDER BY c.city_name, d.district_name, a.serial_no, p.phone_no", nativeQuery = true)
    List<Object[]> findPayphonesByAtsCityDistrict(
            @Param("serialNo") String serialNo,
            @Param("cityName") String cityName,
            @Param("districtName") String districtName
    );

    @Query(value = "SELECT DISTINCT p.phone_no, str.street_name, addr.house_no " +
            "FROM phones p " +
            "JOIN subscriptions subs ON p.phone_id = subs.phone_id " +
            "JOIN service_connection sc ON subs.subscription_id = sc.subscription_id " +
            "JOIN services s ON sc.service_id = s.service_id " +
            "JOIN addresses addr ON p.address_id = addr.address_id " +
            "JOIN streets str ON addr.street_id = str.street_id " +
            "JOIN districts d ON str.district_id = d.district_id " +
            "JOIN cities c ON d.city_id = c.city_id " +
            "WHERE s.service_name = 'Междугородний звонок' AND " +
            "str.street_name = 'Авиастроителей' AND " +
            "addr.house_no = 15 " +
            "ORDER BY p.phone_no ASC", nativeQuery = true)
    List<Object[]> findIntercityCallPhones();

    @Query(value = "SELECT DISTINCT p.phone_no, str.street_name, addr.house_no " +
            "FROM phones p " +
            "JOIN addresses addr ON p.address_id = addr.address_id " +
            "JOIN streets str ON addr.street_id = str.street_id " +
            "JOIN districts d ON str.district_id = d.district_id " +
            "JOIN cities c ON d.city_id = c.city_id " +
            "WHERE str.street_name = 'Авиастроителей' AND " +
            "addr.house_no = 15 " +
            "ORDER BY p.phone_no ASC", nativeQuery = true)
    List<Object[]> findPhonesByAddress();

    @Query(value = "SELECT sub.subscriber_id, sub.first_name, sub.last_name, sub.surname, sub.age, sub.gender, " +
            "sub.benefit, p.phone_no, p.phone_type, addr.street_id, addr.house_no, subs.apartment, " +
            "str.street_name, d.district_name, c.city_name, a.serial_no AS atsSerialNo " +
            "FROM subscribers sub " +
            "JOIN subscriptions subs ON sub.subscriber_id = subs.subscriber_id " +
            "JOIN phones p ON subs.phone_id = p.phone_id " +
            "JOIN addresses addr ON p.address_id = addr.address_id " +
            "JOIN streets str ON addr.street_id = str.street_id " +
            "JOIN districts d ON str.district_id = d.district_id " +
            "JOIN cities c ON d.city_id = c.city_id " +
            "JOIN ats a ON p.ats_id = a.ats_id " +
            "WHERE p.phone_no = :phoneNo", nativeQuery = true)
    List<Object[]> findSubscriberByPhoneNo(@Param("phoneNo") String phoneNo);

    @Query(value = "WITH free_phones AS ( " +
            "    SELECT pnv.phone_no AS availablePhoneNo, pnv.ats_id, addr.address_id " +
            "    FROM phones pnv " +
            "    LEFT JOIN subscriptions subs ON pnv.phone_id = subs.phone_id " +
            "    JOIN addresses addr ON pnv.address_id = addr.address_id " +
            "    JOIN streets st ON addr.street_id = st.street_id " +
            "    JOIN districts d ON st.district_id = d.district_id " +
            "    JOIN cities c ON d.city_id = c.city_id " +
            "    JOIN ats a ON pnv.ats_id = a.ats_id " +
            "    WHERE subs.phone_id IS NULL " +
            ") " +
            "SELECT p.phone_no AS pairedPhoneNo, str.street_name, addr.house_no, a.serial_no AS atsSerialNo, free_phones.availablePhoneNo " +
            "FROM phones p " +
            "JOIN addresses addr ON p.address_id = addr.address_id " +
            "JOIN streets str ON addr.street_id = str.street_id " +
            "JOIN ats a ON p.ats_id = a.ats_id " +
            "JOIN free_phones ON free_phones.ats_id = a.ats_id AND free_phones.address_id = addr.address_id " +
            "WHERE p.phone_type = 'PAIRED' " +
            "ORDER BY p.phone_no ASC", nativeQuery = true)
    List<Object[]> findPairedPhonesWithFreePhones();

    @Query(value = "SELECT sub.subscriber_id, sub.first_name, sub.last_name, sub.surname, sub.age, sub.gender, " +
            "sub.benefit, p.phone_no, addr.street_id, addr.house_no, subs.apartment, " +
            "str.street_name, d.district_name, c.city_name, a.serial_no AS atsSerialNo, " +
            "CASE " +
            "    WHEN (CURRENT_DATE - sc.payment_date) > 1 THEN 'Письменное уведомление' " +
            "    WHEN (CURRENT_DATE - sc.payment_date) > 10 THEN 'Отключить телефон' " +
            "    WHEN (CURRENT_DATE - sc.payment_date) > 3 THEN 'Отключить выход на межгород' " +
            "END AS actionRequired, " +
            "COUNT(*) OVER () AS totalDebtors " +
            "FROM subscribers sub " +
            "JOIN subscriptions subs ON sub.subscriber_id = subs.subscriber_id " +
            "JOIN phones p ON subs.phone_id = p.phone_id " +
            "JOIN addresses addr ON p.address_id = addr.address_id " +
            "JOIN streets str ON addr.street_id = str.street_id " +
            "JOIN districts d ON str.district_id = d.district_id " +
            "JOIN cities c ON d.city_id = c.city_id " +
            "JOIN ats a ON p.ats_id = a.ats_id " +
            "JOIN service_connection sc ON subs.subscription_id = sc.subscription_id " +
            "JOIN services s ON sc.service_id = s.service_id " +
            "WHERE a.serial_no = :serialNo " +
            "AND d.district_name = :districtName " +
            "AND (CURRENT_DATE - sc.payment_date) > 1 " +
            "ORDER BY sub.last_name, sub.first_name, sub.surname", nativeQuery = true)
    List<Object[]> findDebtorsByAtsSerialAndDistrict(@Param("serialNo") String serialNo, @Param("districtName") String districtName);
}

