package Version1.Jira_API;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.testng.annotations.Test;

public class RunnerClass extends BaseClass {
	@Test
	public static void sendreport() throws Exception {
		//String [] vmpath = {"Input//jira.vm","Input//google.vm"};
		
		String [] vmpath = {"Input//projectss.vm"};
		String [] Proj = {"CBQ","BGC"};
		//String[] htmlpath = {"Input//Jira.html","Input//Google.html"};
		
		String [] htmlpath = {"Input//projects.html"};
		for(int i=0;i<vmpath.length;i++) {
		init_vm(vmpath[i]);
		APIRequests2 api = new APIRequests2();
		//APIRequests api = new APIRequests();
		System.out.println(startDate());
		System.out.println(endDate());
		
//		String[] arr = {"CBQ","BGC"};
//		
//		for (int i = 1; i < arr.length; i++) {
//			api.fetchissue(startDate(),endDate(),"status",arr[i]);
//			api.fetchissue(startDate(),endDate(),"priority",arr[i]);
//		}
		
		api.fetchissue(startDate(),endDate(),"status",Proj[i],htmlpath[i]);
		api.fetchissue(startDate(),endDate(),"priority",Proj[i],htmlpath[i]);
		
		EmailTemplate.SendMail(htmlpath[i]);
		}
	}
	
	

}
