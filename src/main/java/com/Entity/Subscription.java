package com.Entity;
import java.time.LocalDateTime;
import javax.persistence.Column;
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
@Table(name="tbl_subscription")
public class Subscription 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long ani;
	private LocalDateTime sub_date_time;
	private LocalDateTime unsub_date_time;
	private String m_act;
	private String m_deact;
	private String lang;
	@Column(name = "service_type")
	private String service;
	private String STATUS;
	private String charging_date;
	private LocalDateTime billing_date;
	private LocalDateTime next_billed_date;
	private String last_billed_date;
	private String default_amount;
	private String RECORDSTATUS;
	private String product_id;
	@Column(name="pack_type")
	private String pack;
	private String provider;
}