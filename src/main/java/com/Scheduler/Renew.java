package com.Scheduler;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.Entity.BillingRenewals;
import com.Repository.BillingRenewalsRepo;
import com.Service.NewSdpService;
import com.Service.RenewalsService;

@Component
public class Renew 
{
	@Autowired
	private BillingRenewalsRepo billingRepo;
	
	@Autowired
	private NewSdpService service;
	
     @Scheduled(fixedDelay = 5000L,initialDelay = 2000L)
	public void renewScheduler1()
	{
		try
		{
			List<BillingRenewals> renewals = billingRepo.getDataByStatus("2");
			if(!renewals.isEmpty())
			{
				for(BillingRenewals billingRenewals : renewals)
				{
					service.renewalService(billingRenewals);
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
//     @Scheduled(fixedDelay = 1000L,initialDelay = 4000L)
//	public void renewScheduler2()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("2");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 3000L,initialDelay = 5000L)
//	public void renewScheduler3()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("3");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 1000L,initialDelay = 6000L)
//	public void renewScheduler4()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("4");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 2000L,initialDelay = 7000L)
//	public void renewScheduler5()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("5");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 1000L,initialDelay = 3000L)
//	public void renewScheduler6()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("6");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 4000L,initialDelay = 8000L)
//	public void renewScheduler7()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("7");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 1000L,initialDelay = 9000L)
//	public void renewScheduler8()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("8");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 3000L,initialDelay = 1000L)
//	public void renewScheduler9()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("9");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 1000L,initialDelay = 3000L)
//	public void renewScheduler10()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("10");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 5000L,initialDelay = 12000L)
//	public void renewScheduler11()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("11");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 1000L,initialDelay = 14000L)
//	public void renewScheduler12()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("12");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 5000L,initialDelay = 15000L)
//	public void renewScheduler13()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("13");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 1000L,initialDelay = 13000L)
//	public void renewScheduler14()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("14");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 3000L,initialDelay = 16000L)
//	public void renewScheduler15()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("15");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 1000L,initialDelay = 17000L)
//	public void renewScheduler16()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("16");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 5000L,initialDelay = 19000L)
//	public void renewScheduler17()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("17");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 9000L,initialDelay = 17000L)
//	public void renewScheduler18()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("18");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 5000L,initialDelay =20000L)
//	public void renewScheduler19()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("19");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 1000L,initialDelay = 18000L)
//	public void renewScheduler20()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("20");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 3000L,initialDelay = 21000L)
//	public void renewScheduler21()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("21");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 1000L,initialDelay = 22000L)
//	public void renewScheduler22()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("22");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 5000L,initialDelay = 23000L)
//	public void renewScheduler23()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("23");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 1000L,initialDelay = 24000L)
//	public void renewScheduler24()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("24");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 2000L,initialDelay = 7000L)
//	public void renewScheduler25()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("25");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 1000L,initialDelay = 23000L)
//	public void renewScheduler26()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("26");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 3000L,initialDelay = 24000L)
//	public void renewScheduler27()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("27");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 1000L,initialDelay = 25000L)
//	public void renewScheduler28()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("28");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 5000L,initialDelay = 26000L)
//	public void renewScheduler29()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("29");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//     @Scheduled(fixedDelay = 1000L,initialDelay = 27000L)
//	public void renewScheduler30()
//	{
//		try
//		{
//			List<BillingRenewals> renewals = billingRepo.findByStatus("30");
//			service.renewalsService(renewals);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
}