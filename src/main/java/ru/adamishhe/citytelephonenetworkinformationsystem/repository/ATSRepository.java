package ru.adamishhe.citytelephonenetworkinformationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.ATS;

import java.util.List;

@Repository
public interface ATSRepository extends JpaRepository<ATS, Long> {

    @Query(value = "SELECT serial_no, total_debt AS max_total_debt " +
            "FROM ( " +
            "    SELECT a.serial_no, " +
            "           SUM(EXTRACT(MONTH FROM AGE(CURRENT_DATE, sc.payment_date)) * s.service_cost) AS total_debt, " +
            "           RANK() OVER (ORDER BY SUM(EXTRACT(MONTH FROM AGE(CURRENT_DATE, sc.payment_date)) * s.service_cost) DESC) AS r " +
            "    FROM ats a " +
            "    JOIN phones pnv ON a.ats_id = pnv.ats_id " +
            "    JOIN subscriptions subs ON pnv.phone_id = subs.phone_id " +
            "    JOIN subscribers sub ON subs.subscriber_id = sub.subscriber_id " +
            "    JOIN service_connection sc ON subs.subscription_id = sc.subscription_id " +
            "    JOIN services s ON sc.service_id = s.service_id " +
            "    WHERE (CURRENT_DATE - sc.payment_date) >= 31 " +
            "    GROUP BY a.serial_no " +
            ") subquery " +
            "WHERE r = 1", nativeQuery = true)
    List<Object[]> findMaxTotalDebt();

    @Query(value = "SELECT serial_no, debtors_count AS max_debtors_count " +
            "FROM ( " +
            "    SELECT a.serial_no, " +
            "           COUNT(*) AS debtors_count, " +
            "           RANK() OVER (ORDER BY COUNT(*) DESC) AS r " +
            "    FROM ats a " +
            "    JOIN phones pnv ON a.ats_id = pnv.ats_id " +
            "    JOIN subscriptions subs ON pnv.phone_id = subs.phone_id " +
            "    JOIN subscribers sub ON subs.subscriber_id = sub.subscriber_id " +
            "    JOIN service_connection sc ON subs.subscription_id = sc.subscription_id " +
            "    JOIN services s ON sc.service_id = s.service_id " +
            "    WHERE (CURRENT_DATE - sc.payment_date) >= 31 " +
            "    GROUP BY a.serial_no " +
            ") subquery " +
            "WHERE r = 1", nativeQuery = true)
    List<Object[]> findMaxDebtorsCount();

    @Query(value = "SELECT serial_no, debtors_count AS min_debtors_count " +
            "FROM ( " +
            "    SELECT a.serial_no, " +
            "           COUNT(*) AS debtors_count, " +
            "           RANK() OVER (ORDER BY COUNT(*) ASC) AS r " +
            "    FROM ats a " +
            "    JOIN phones pnv ON a.ats_id = pnv.ats_id " +
            "    JOIN subscriptions subs ON pnv.phone_id = subs.phone_id " +
            "    JOIN subscribers sub ON subs.subscriber_id = sub.subscriber_id " +
            "    JOIN service_connection sc ON subs.subscription_id = sc.subscription_id " +
            "    JOIN services s ON sc.service_id = s.service_id " +
            "    WHERE (CURRENT_DATE - sc.payment_date) >= 31 " +
            "    GROUP BY a.serial_no " +
            ") subquery " +
            "WHERE r = 1", nativeQuery = true)
    List<Object[]> findMinDebtorsCount();
}
