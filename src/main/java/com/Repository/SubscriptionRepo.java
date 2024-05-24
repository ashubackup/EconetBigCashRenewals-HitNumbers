package com.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.Entity.Subscription;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription,Long>
{
	@Query(value="SELECT * FROM tbl_subscription WHERE DATE(next_billed_date)<=CURDATE()",nativeQuery=true)
	public List<Subscription> getRenewalsData();
	
	@Query(value="SELECT * FROM tbl_subscription WHERE ani='788677584'",nativeQuery=true)
	public List<Subscription> testing();
	
	@Query(value="SELECT * FROM tbl_subscription WHERE DATE(next_billed_date)<=CURDATE() AND STATUS=:status",nativeQuery=true)
	public List<Subscription> getDataByStatus(@Param("status")String status);
	
	@Query(value="SELECT * FROM tbl_subscription WHERE STATUS IS NULL",nativeQuery=true)
	public List<Subscription> getNullStatus();
	
	public Subscription findByAni(Long ani);
	
	@Query(value="SELECT * FROM tbl_subscription LIMIT 50",nativeQuery=true)
	public List<Subscription> getNumbers();
}