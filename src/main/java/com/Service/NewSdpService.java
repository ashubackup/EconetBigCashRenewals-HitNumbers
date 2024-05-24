package com.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.Api.Api;
import com.Api.TokenApi;
import com.Entity.BillingRenewals;
import com.MD5.EncryptedData;
import com.Model.JsonRequest;
import com.Model.RequestParam;
import com.Repository.PriceRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NewSdpService 
{
	
//	@Autowired
//	private BillingSuccessRepo successRepo;
//	
//	@Autowired
//	private SendLogsRepo sendLogsRepo;
//	
//	@Autowired
//	private SubscriptionRepo subRepo;

	
	@Autowired
	private PriceRepo priceRepo;

	@Value("${tokenApiUsername}")
	private String userName;

	@Value("${tokenApiPassword}")
	private String password;

	@Autowired
	TokenApi tokenApi;

	@Value("${billingApi}")
	private String subUrl;

	@Autowired
	EncryptedData encryptPassword;
//
	@Autowired
	Api api;

	public void renewalService(BillingRenewals getNumber)
	{
		try
		{
			String offerCode = priceRepo.findByStatusAndPack("1", getNumber.getPack()).getOffercode();

			String amount = getNumber.getPrice(); //priceRepo.findByStatusAndPack("1", getNumber.getPack()).getPrice();
			String random16DigitNumber = new SecureRandom().ints(16, 0, 10).mapToObj(i -> Integer.toString(i))
					.collect(Collectors.joining());

			LocalDateTime timeStamp = LocalDateTime.now(); //ZoneId.of("Asia/Kolkata")

			String encryptKey = encryptPassword.generateKey(password);

			LocalDateTime nextBilledDateTime = LocalDateTime.now();

			if (getNumber.getPack().equalsIgnoreCase("BIGCASH_D")) {
				nextBilledDateTime = nextBilledDateTime.plusDays(1);
			} else if (getNumber.getPack().equalsIgnoreCase("BIGCASH_W")) {
				nextBilledDateTime = nextBilledDateTime.plusWeeks(1);
			} else if (getNumber.getPack().equalsIgnoreCase("BIGCASH_M")) {
				nextBilledDateTime = nextBilledDateTime.plusMonths(1);
			} else {
				nextBilledDateTime = nextBilledDateTime.plusDays(1);
			}

			JsonRequest json = new JsonRequest();
			json.setRequestId(random16DigitNumber);
			json.setChannel("2");
			json.setRequestTimeStamp(timeStamp.toString());
			json.setSourceNode("SourceNode"); // partner_Tunnel
			json.setSourceAddress("91.205.172.123");
			json.setFeatureId("Payment");
			json.setUsername(userName);
			json.setPassword(encryptKey);
			json.setExternalServiceId(String.valueOf(getNumber.getAni()));
			RequestParam param = new RequestParam();
			// planiD
			param.setSubscriptionOfferID(offerCode);
			param.setCpId("124");
			param.setChargeAmount(amount);
			json.setRequestParam(param);

			ObjectMapper mapper = new ObjectMapper();
			String jsonValue = mapper.writeValueAsString(json);
			api.hitBillingApi(Long.parseLong(getNumber.getAni()), jsonValue, getNumber.getPack(), amount,getNumber);

		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
