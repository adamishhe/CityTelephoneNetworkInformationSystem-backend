package ru.adamishhe.citytelephonenetworkinformationsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Subscription;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    // вероятно этот запрос должен быть не в этом репозитории...
//    @Query(value = "SELECT s.last_name, s.first_name, " +
//            "date_diff('day', sc.debt_formation_date, sc.payment_date) * sv.service_cost AS debt_amount " +
//            "FROM subscriptions sub " +
//            "JOIN phone_numbers pn ON sub.phone_id = pn.phone_id " +
//            "JOIN ats a ON pn.ats_id = a.ats_id " +
//            "JOIN subscribers s ON sub.subscriber_id = s.subscriber_id " +
//            "JOIN service_connection sc ON sub.subscription_id = sc.subscription_id " +
//            "JOIN services sv ON sc.service_id = sv.service_id " +
//            "JOIN current_city cc ON pn.address_id = cc.address_id " +
//            "WHERE a.serial_no = '6IBVG115145' " +
//            "AND cc.district_name = 'Дзержинский' " +
//            "AND date_diff('day', sc.debt_formation_date, sc.payment_date) >= 31 " +
//            "AND sv.service_name = 'Междугородний звонок' " +
//            "ORDER BY s.last_name ASC", nativeQuery = true)
//    List<Object[]> findDebtorsInDistrict();
}
