package com.Service;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Entity.BillingRenewals;
import com.Entity.Price;
import com.Helper.RenewalsHelper;
import com.Repository.BillingLogsRepo;
import com.Repository.BillingRenewalsRepo;
import com.Repository.PriceRepo;

@Service
public class RenewalsService 
{	
	@Autowired
	private RenewalsHelper helper;
	
	@Autowired
	private PriceRepo priceRepo;
	
	@Autowired
	private BillingLogsRepo billingLogsRepo;
	
	@Autowired
	private BillingRenewalsRepo billingRenewalsRepo;
	
	public void renewalsService(List<BillingRenewals> renewals)
	{
		try
		{
//			List<Subscription>renewals=subRepo.getRenewalsData();
//			List<Subscription>renewals=subRepo.testing();
			System.out.println("Renewals List is "+renewals.size());
			
			String cpId="347";
			String channelID="1";
			String username="BigCash";
			String password="Big341C";
			String shortCode="55223";
			String uuid=String.valueOf(new Random().nextInt());
			
			Integer count=0;
			for(BillingRenewals billing:renewals)
			{
				count=count+1;
				System.out.println("pack"+billing.getPack());
				System.out.println("Count "+count);
				//Get Amount
				Price price=priceRepo.findByStatusAndPack("1",billing.getPack());
				int numberalreadyChargedOrNot = billingLogsRepo.countAni(Long.parseLong(billing.getAni()));
				if(numberalreadyChargedOrNot==0)
				{
					helper.billingHelper(billing.getAni().toString(),billing.getPack(),channelID,price.getPrice(),uuid,cpId,username,password,shortCode,billing);
				}
				else
				{
					System.out.println("Number Alerady Charged SO Delete frmo TblBillingRenewals:::");
					billingRenewalsRepo.delete(billing);
				}
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}