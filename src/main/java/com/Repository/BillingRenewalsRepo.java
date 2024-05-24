package com.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.Entity.BillingRenewals;

@Repository
public interface BillingRenewalsRepo extends JpaRepository<BillingRenewals,Integer>
{
	public BillingRenewals findByAni(String ani);
//	public List<BillingRenewals> findByStatus(String status);
	@Query(value="Select * from tbl_billing_renewals where status=:status order by datetime limit 30",nativeQuery=true)
	public List<BillingRenewals> getDataByStatus(@Param("status") String status);
}