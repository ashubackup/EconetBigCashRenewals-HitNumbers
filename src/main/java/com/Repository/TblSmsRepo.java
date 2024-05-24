//package com.Repository;
//import java.util.List;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import com.Entity.*;
//
//@Repository
//public interface TblSmsRepo extends JpaRepository<TblSms, Integer>
//{
//	public List<TblSms> findByStatus(String status);
//	
//	@Query(value="SELECT * FROM tbl_sms WHERE DATE(createddatetime)=DATE('2022-09-02') AND STATUS='0'", nativeQuery=true)
//	public List<TblSms> getData1();
//	
//	@Query(value="SELECT * FROM tbl_sms WHERE DATE(createddatetime)=DATE('2022-09-01') AND STATUS='0'", nativeQuery=true)
//	public List<TblSms> getData2();
//
//	@Query(value="SELECT * FROM tbl_sms WHERE DATE(createddatetime)=DATE('2022-08-31') AND STATUS='0'", nativeQuery=true)
//	public List<TblSms> getData3();
//
//	@Query(value="SELECT * FROM tbl_sms WHERE DATE(createddatetime)=DATE('2022-08-30') AND STATUS='0'", nativeQuery=true)
//	public List<TblSms> getData4();
//
//	@Query(value="SELECT * FROM tbl_sms WHERE DATE(createddatetime)=DATE('2022-08-29') AND STATUS='0'", nativeQuery=true)
//	public List<TblSms> getData5();
//	
//	@Query(value="SELECT * FROM tbl_sms WHERE DATE(createddatetime)=DATE('2022-08-28') AND STATUS='0'", nativeQuery=true)
//	public List<TblSms> getData6();
//
//	@Query(value="SELECT * FROM tbl_sms WHERE DATE(createddatetime)=DATE('2022-08-27') AND STATUS='0'", nativeQuery=true)
//	public List<TblSms> getData7();
//
//	@Query(value="SELECT * FROM tbl_sms WHERE DATE(createddatetime)=DATE('2022-08-26') AND STATUS='0'", nativeQuery=true)
//	public List<TblSms> getData8();
//	
//	@Query(value="SELECT * FROM tbl_sms WHERE DATE(createddatetime)=DATE(SUBDATE(NOW(),0)) AND STATUS='0'", nativeQuery=true)
//	public List<TblSms> todayMessages();
//
//	@Query(value="SELECT * FROM tbl_sms WHERE DATE(createddatetime)=DATE(SUBDATE(NOW(),0)) AND STATUS='0'", nativeQuery=true)
//	public List<TblSms> yesterdayMessages();
//	
//	@Query(value="SELECT * FROM tbl_sms WHERE STATUS=:status",nativeQuery=true)
//	public List<TblSms> getPendingSms(@Param("status")String status);
//}