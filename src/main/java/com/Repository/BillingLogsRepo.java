package com.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.Entity.BillingLogs;

@Repository
public interface BillingLogsRepo extends JpaRepository<BillingLogs,Integer>
{
	@Query(value="SELECT count(ani) FROM tbl_billinglogs WHERE DATE(process_datetime)=CURDATE() AND recordStatus='1' AND ani=:ani",nativeQuery = true)
	int countAni(@Param("ani") Long ani);
}