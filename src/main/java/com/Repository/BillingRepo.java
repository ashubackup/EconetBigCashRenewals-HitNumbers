package com.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Entity.Billing;

@Repository
public interface BillingRepo extends JpaRepository<Billing,Long>
{

}