package com.Api;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Entity.BillingLogs;
import com.Entity.BillingRenewals;
import com.Entity.BillingSuccess;
import com.Entity.Subscription;
import com.Entity.TblLogInInfor;
import com.Repository.BillingLogsRepo;
import com.Repository.BillingRenewalsRepo;
import com.Repository.BillingSuccessRepo;
import com.Repository.LoginInfoRepo;
import com.Repository.SubscriptionRepo;

@Service
public class Api {

	@Autowired
	TokenApi tokenApi;

	@Value("${billingApi}")
	private String subUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BillingLogsRepo billingLogsRepo;

	@Autowired
	private LoginInfoRepo loginInfoRepo;
	
	@Autowired
	private SubscriptionRepo subscriptionRepo;
	
	@Autowired
	private BillingSuccessRepo billingSuccessRepo;
	
	@Autowired
	private BillingRenewalsRepo billingRenewalsRepo;

	public void hitBillingApi(Long ani, String json, String pack, String amount,BillingRenewals renewals) {
		try {

			HttpEntity<String> requestEntity = null;
			try {

				TblLogInInfor logInInfo = loginInfoRepo.findByStatus("1");

				String accessToken = logInInfo.getToken();
//				String accessToken = tokenApi.generateToken();

				// Set request headers
				HttpHeaders headers = new HttpHeaders();
				headers.add("Connection", "keep-alive");
				headers.add("X-Authorization", "Bearer " + accessToken);
				headers.add("Content-Type", "application/json");
				headers.add("Accept-Encoding", "application/gzip");
				headers.add("Accept", "*/*");

				// Build request entity
				requestEntity = new HttpEntity<>(json, headers);

				System.out.println("request" + requestEntity);
				ResponseEntity<String> responseEntity = restTemplate.exchange(subUrl, HttpMethod.POST, requestEntity,
						String.class);
				JSONObject responeVlue = new JSONObject(responseEntity);

				String body = responeVlue.get("body").toString();
				System.out.println("Response " + body);
				JSONObject jsonObject2 = new JSONObject(body);
				String resultCode = jsonObject2.get("resultCode").toString();

				saveResponse(ani, amount, requestEntity.toString(), body, pack, resultCode,renewals);

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			// saveResponse(ani, amount, requestEntity.toString(), body, pack,resultCode);
		}
	}

	public void saveResponse(Long ani, String amount, String request, String response, String pack, String resultCOde,BillingRenewals renewals) {
		try {
			int status = 988;
			String errorDesc = "116";
			int days = 1;
			if (resultCOde.equalsIgnoreCase("0")) {
				
				
				if(pack.equalsIgnoreCase("BIGCASH_D"))
				{
					days=1;
				}
				else if(pack.equalsIgnoreCase("BIGCASH_W"))
				{
					days=7;
				}
				else if (pack.equalsIgnoreCase("BIGCASH_M"))
				{
					days=30;
				}
				
				status = 1;
				errorDesc = "ONLINE CHARGING SUCCESS";
				
				Subscription subscription  = subscriptionRepo.findByAni(ani);
				if(subscription==null)
				{
					Subscription sub = new Subscription();
					sub.setAni(ani);
					sub.setBilling_date(LocalDateTime.now());
					sub.setDefault_amount(amount);
					sub.setLast_billed_date(LocalDateTime.now().toString());
					sub.setM_act("USSD");
					sub.setNext_billed_date(LocalDateTime.now().plusDays(days));
					sub.setPack(pack);
					sub.setService("games");
					sub.setSub_date_time(LocalDateTime.now());
					subscriptionRepo.save(sub);
				}
				else
				{
					subscription.setLast_billed_date(LocalDateTime.now().toString());
					subscription.setNext_billed_date(LocalDateTime.now().plusDays(days));
					subscription.setBilling_date(LocalDateTime.now());
					subscriptionRepo.save(subscription);
				}
				
				BillingSuccess sucess = new BillingSuccess();
				sucess.setAni(ani);
				sucess.setDatetime(LocalDateTime.now());
				sucess.setDeducted_amount(amount);
				sucess.setErrordesc("ONLINE CHARGING SUCCESS");
				sucess.setMode("USSD");
				sucess.setProcess_datetime(LocalDateTime.now());
				sucess.setPack_type(pack);
				sucess.setRecordstatus(1);
				sucess.setServicename("games");
				sucess.setSubservicename("games");
				sucess.setTotal_amount(amount);
				sucess.setType_event("ren");
				billingSuccessRepo.save(sucess);
				
			}
			BillingLogs billingLogs = BillingLogs.builder().ani(Long.valueOf(ani)).datetime(LocalDateTime.now())
					.deducted_amount(amount).errordesc(errorDesc).mode("USSD").pack_type(pack)
					.process_datetime(LocalDateTime.now()).recordstatus(status).servicename("games")
					.subservicename("games").total_amount(amount).request(request).response(response).type_event("ren")
					.build();

			billingLogsRepo.save(billingLogs);
			System.out.println("Data Added in table Billinglogs");
			billingRenewalsRepo.delete(renewals);
			System.out.println("Deleted from tbl_billing_renewals");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}