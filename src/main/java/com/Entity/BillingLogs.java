package com.Entity;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="tbl_billinglogs")
public class BillingLogs 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer pid;
	private Long id;
	private Long ani;
	private String total_amount;
	private String deducted_amount;
	private LocalDateTime datetime;
	private int recordstatus;
	private String errordesc;
	private String type_event;
	private LocalDateTime process_datetime;
	private String mode;
	private String servicename;
	private String subservicename;
	private int product_id;
	private String pack_type;
	private String txnId;
	private String request;
	private String response;
}