package com.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Entity.BillingSuccess;

@Repository
public interface BillingSuccessRepo extends JpaRepository<BillingSuccess,Long>
{

}