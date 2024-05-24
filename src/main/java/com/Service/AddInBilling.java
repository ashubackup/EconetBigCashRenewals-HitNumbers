package com.Service;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Entity.BillingRenewals;
import com.Entity.Subscription;
import com.Repository.BillingRenewalsRepo;
import com.Repository.SubscriptionRepo;

@Service
public class AddInBilling 
{
	@Autowired
	private BillingRenewalsRepo billingRepo;
	
	@Autowired
	private SubscriptionRepo subRepo;
	
	public void addInTblBillingRenewals(List<Subscription> subs,String status)
	{
		try
		{
			for(Subscription sub:subs)
			{
				BillingRenewals check = billingRepo.findByAni(String.valueOf(sub.getAni()));
				
				if(check==null)
				{
					//Number not already present so can add
					
					BillingRenewals billing=new BillingRenewals();
					billing.setAni(String.valueOf(sub.getAni()));
					billing.setDatetime(LocalDateTime.now());
					billing.setPack(sub.getPack());
					billing.setStatus(status);
					
					billingRepo.save(billing);
					System.out.println(sub.getAni()+" added in tbl_billing_renewals");
					
					sub.setBilling_date(LocalDateTime.now());
					subRepo.save(sub);
				}
				else
				{
					System.out.println("Number already present in billing");
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void addInTblBillingRenewalsNew(Subscription sub,int status)
	{
		try
		{
			BillingRenewals check = billingRepo.findByAni(String.valueOf(sub.getAni()));
			
			if(check==null)
			{
				//Number not already present so can add
				
				BillingRenewals billing=new BillingRenewals();
				billing.setAni(String.valueOf(sub.getAni()));
				billing.setDatetime(LocalDateTime.now());
				billing.setPack(sub.getPack());
				billing.setStatus(String.valueOf(status));
				
				billingRepo.save(billing);
				System.out.println(sub.getAni()+" added in tbl_billing_renewals");
				
				sub.setBilling_date(LocalDateTime.now());
				subRepo.save(sub);
			}
			else
			{
				System.out.println("Number already present in billing");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}