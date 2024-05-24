package com.Helper;
import java.time.LocalDateTime;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.Entity.BillingLogs;
import com.Entity.BillingRenewals;
import com.Entity.Subscription;
import com.Entity.BillingSuccess;
import com.Repository.BillingLogsRepo;
import com.Repository.BillingRenewalsRepo;
import com.Repository.BillingSuccessRepo;
import com.Repository.SubscriptionRepo;

@Service
public class RenewalsHelper 
{
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private BillingLogsRepo billingLogsRepo;
	
	@Autowired
	private BillingSuccessRepo successRepo;
	
	@Autowired
	private SubscriptionRepo subRepo;
	
	@Autowired
	private BillingRenewalsRepo billingRepo;
	
	public void billingHelper(String ani,String packType,String channelId,String amount,String UUID,String cpId,
			String username,String password,String shortCode,BillingRenewals billing)
	{
		try
		{
			String xmlData = "msisdn=263" + ani + "|productCode=" + packType + "|channelID=" +channelId
			+ "|chargeAmount=" + amount + "|clientTransId=" + UUID + "|cpID=" + cpId
			+ "|username=" + username + "|password=" + password
			+ "|language=0|shortCode=" + shortCode;
			
			String requestType="ACTIVATE";
			
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://ws.apache.org/axis2/com/sixdee/imp/axis2/dto/Request/xsd/\" xmlns:xsd1=\"http://dto.axis2.imp.sixdee.com/xsd\">"
					+ "<soap:Body>" + "<xsd:ServiceExecutor>" + "<xsd:request>" + "<xsd1:billingText>" + xmlData
					+ "</xsd1:billingText>" + "<xsd1:operationCode>" + requestType + "</xsd1:operationCode>"
					+ "</xsd:request>" + "</xsd:ServiceExecutor>" + "</soap:Body>" + "</soap:Envelope>";

			hitForCharging(xml,ani,amount,packType,billing);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void hitForCharging(String body,String ani,String amount,String packType,BillingRenewals billing)
	{
		try
		{
			String url="http://172.27.100.12:9080/BL/services/SDP?wsdl";
//			SDP_TEST_URL=http://172.27.100.12:9080/BL/services/SDP?wsdl

			HttpHeaders headers=new HttpHeaders();
			headers.set("Content-Type","text/xml");
			headers.set("Accept","*/*");
			headers.set("User-Agent","Java/1.6.0_21");
			
			HttpEntity<String> entity=new HttpEntity<>(body,headers);
			
			System.out.println("\nURL is "+url);
			System.out.println("\nRequest is "+body);
			
			ResponseEntity<String>response
			=restTemplate.exchange(url,HttpMethod.POST,entity,String.class);
			
			System.out.println("\nResponse is "+response.getBody());
			
			handleResponse(response.getBody(),ani,amount,packType,body,billing);
//			saveResponse(response.getBody(),ani,amount,packType,body,billing);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//Method to save response
//	public void saveResponse(String response,String ani,String amount,String pack,
//							 String request,BillingRenewals billing)
//	{
//		
//		int min=2;
//		int max=40;
//		
//		int status = (int)(Math.random()*(max-min+1)+min);  
//		System.out.println("number "+status);
//		
//		BillingLogs billingLogs=BillingLogs.builder().ani(Long.valueOf(ani))
//				.datetime(LocalDateTime.now())
//				.deducted_amount(amount)
//				.errordesc("0")
//				.mode("USSD")
//				.pack_type(pack)
////				.process_datetime(LocalDateTime.now())
//				.recordstatus(status)
//				.servicename("games")
//				.subservicename("games")
//				.total_amount(amount)
//				.request(request)
//				.response(response)
//				.type_event("ren")
//				.build();
//
//			billingLogsRepo.save(billingLogs);
//			System.out.println("Saved in tbl_billinglogs");
//			
//			//delete from tbl_billing_renewals
//			billingRepo.delete(billing);
//			System.out.println("Deleted from tbl_billing");
//	}
	
	public void handleResponse(String response,String ani,String amount,String pack,String request,BillingRenewals billing)
	{
		try
		{
			JSONObject xmlToJson = XML.toJSONObject(response);
			Object nodeData = getNodeData("soapenv:Envelope", xmlToJson.toString());
			Object nodeData2 = getNodeData("soapenv:Body", nodeData.toString());
			Object nodeData3 = getNodeData("ns:ServiceExecutorResponse", nodeData2.toString());
			Object nodeData4 = getNodeData("ns:return", nodeData3.toString());
			Object statusCode = getNodeData("ax21:statusCode", nodeData4.toString());
//			Object statusDesc = getNodeData("ax21:status", nodeData4.toString());
			
			String recordStatus="";
			String errorDesc="";
			
			Subscription sub = subRepo.findByAni(Long.valueOf(ani));
			
			if (statusCode.toString().equalsIgnoreCase("6")) 
			{
				//Charging Fail
				recordStatus="99";
				errorDesc="115";
				
				sub.setBilling_date(LocalDateTime.now());
				subRepo.save(sub);				
			}
			else if (statusCode.toString().equalsIgnoreCase("116")) 
			{
				//Charging Success
				recordStatus="1";
				errorDesc="ONLINE CHARGING SUCCESS";
				
				
				Integer days=1;
				if(pack.equalsIgnoreCase("LGAMING_D"))
				{
					days=1;
				}
				else if(pack.equalsIgnoreCase("LGAMING_W"))
				{
					days=7;
				}
				else if(pack.equalsIgnoreCase("LGAMING_M"))
				{
					days=30;
				}
				
				//Update in tbl_subscription
				
				sub.setLast_billed_date(LocalDateTime.now().toString());
				sub.setNext_billed_date(LocalDateTime.now().plusDays(days));
				sub.setBilling_date(LocalDateTime.now());
				subRepo.save(sub);
				
				System.out.println("Data Updated in tbl_subscription");
				
				//Save in tbl_billing_success
				BillingSuccess success=BillingSuccess.builder()
										.ani(Long.valueOf(ani))
										.total_amount(amount)
										.deducted_amount(amount)
										.recordstatus(Integer.valueOf(recordStatus))
										.errordesc(errorDesc)
										.type_event("ren")
										.process_datetime(LocalDateTime.now())
										.servicename("games")
										.subservicename("games")
										.pack_type(pack)
										.build();
				
				successRepo.save(success);
				System.out.println("Data Saved in tbl_billing_success");
				
				//Send to Oren
//				sendToOren(ani,amount,pack);
			}
			else
			{
				recordStatus="988";
				errorDesc="116";
				
				sub.setBilling_date(LocalDateTime.now());
				subRepo.save(sub);				

			}
						
			BillingLogs billingLogs=BillingLogs.builder().ani(Long.valueOf(ani))
									.datetime(LocalDateTime.now())
									.deducted_amount(amount)
									.errordesc(errorDesc)
									.mode("USSD")
									.pack_type(pack)
									.process_datetime(LocalDateTime.now())
									.recordstatus(Integer.valueOf(recordStatus))
									.servicename("games")
									.subservicename("games")
									.total_amount(amount)
									.request(request)
									.response(response)
									.type_event("ren")
									.build();
			
			billingLogsRepo.save(billingLogs);
			System.out.println("Saved in tbl_billinglogs");
			
			//delete from tbl_billing_renewals
			billingRepo.delete(billing);
			System.out.println("Deleted from tbl_billing");
			
		}catch(Exception e)
		{
			e.printStackTrace();
			billingRepo.delete(billing);
		}
	}
	
	
	//in bigcash we dont need to send any data to oren 
//	public void sendToOren(String ani,String amount,String pack)
//	{
//		try
//		{
//			String serviceId="";
//			if(pack.equalsIgnoreCase("LGAMING_D"))
//			{
//				serviceId="750";
//			}
//			else if(pack.equalsIgnoreCase("LGAMING_W"))
//			{
//				serviceId="760";
//			}
//			else if(pack.equalsIgnoreCase("LGAMING_M"))
//			{
//				serviceId="761";
//			}
//			
//			String url ="https://api.ydplatform.com/zw/notification.ashx";
//			JSONObject js = new JSONObject();
//			js.put("msisdn", ani);
//			js.put("amount_billed", amount);
//			js.put("service_id", serviceId);
//			js.put("action", "bill");
//			
//			System.out.println("\nRequest is "+js.toString());
//			
//			//Sending to Oren
//			ResponseEntity<String>response=
//					restTemplate.postForEntity(url,js.toString(),String.class);
//			
//			System.out.println("\nResponse is "+response.getBody());
//
//			SendLogs sendLogs = SendLogs
//								.builder()
//								.datetime(LocalDateTime.now())
//								.request(js.toString())
//								.response(response.getBody())
//								.status(response.getStatusCode().toString())
//								.msisdn(ani)
//								.build();
//			
//			sendLogsRepo.save(sendLogs);
//			System.out.println("Oren Api Logs Saved");
//			
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
	
	public Object getNodeData(String key, String data) 
	{
		try
		{
			return new JSONObject(data).get(key);
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}