package Version1.Jira_API;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIRequests2 extends BaseClass {

	
	public void fetchissue(String fromdate,String todate,String keyword,String proj,String htmlpath) {
		BaseClass.VelocityTemplate("Date", fromdate+" to "+todate,htmlpath);
		
		BaseClass.VelocityTemplate("Project",BaseClass.projname(proj),htmlpath);
		
		

		BaseClass.BaseURL(BASEURL);
		List<String> list = new ArrayList<String>();
		
		String [] status_arr = {"Backlog","Dev In Progress","Review","To QA","QA In Progress","Released"};
		
		//String[] status_arr2= {"Backlog","Dev In Progress","Review","To QA","QA In Progress","Released"};
		String[] prior_arr = {"Critical","High","Moderate","Low","Lowest"};
		//String[] prior_arr2= {"Critical","High","Moderate","Low","Lowest"};

		
		
		if(keyword.equals("status")) {
		 list.clear();
		 list = Arrays.asList(status_arr); 
		}
//		
		else if(keyword.equals("priority")) {
			list.clear();
			list = Arrays.asList(prior_arr);
		}

		//int x=0;
		

		for(int j=0;j<list.size();j++) {
		Map<Integer, RequestSpecification> reqarr = new HashMap<Integer, RequestSpecification>();
		reqarr.put(j, RestAssured.given());
		reqarr.get(j).header("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
		reqarr.get(j).header("X-Atlassian-Token", "no-check");
		reqarr.get(j).headers("Authorization",BaseClass.createAuth(Username,APIKey));
		
		BaseClass.formparams(reqarr.get(j),"startIndex" , "0");
		String jql = "project = "+proj+" AND issuetype = Bug AND "+keyword+" = \""+list.get(j)+"\" AND created >= "+fromdate+" AND created <= "+todate+" order by created DESC";
		BaseClass.formparams(reqarr.get(j),"jql" ,jql);
		BaseClass.formparams(reqarr.get(j),"layoutKey" , "list-view");
		Response response = reqarr.get(j).post("/issueNav/1/issueTable");
	    
		List<String> issue_list = BaseClass.getlistfromResponse(response, "issueTable.issueKeys");
		System.out.println("Issue count in "+list.get(j)+" is "+issue_list.size());
		
	   /* int y=issue_list.size()+x;
		x=y;
		
		issue_list.add(keyword);*/
						
		//System.out.println(y);
		BaseClass.VelocityTemplate(list.get(j).replaceAll(" ","_"), String.valueOf(issue_list.size()),htmlpath);
		String jqll = "jql=project%20%3D%20"+proj+"%20AND%20issuetype%20%3D%20Bug%20AND%20"+keyword+"%20%3D%20"+keyword_URL(list.get(j))+"%20AND%20created%20%3E%3D%20"+fromdate+"%20AND%20created%20%3C%3D%20"+todate+"%20ORDER%20BY%20created%20DESC";

		
		BaseClass.VelocityTemplate(list.get(j).replaceAll(" ","_")+"_link",BASEURL.replace("rest", "issues/?")+ jqll,htmlpath);


		issue_list.clear();
		}
		
		
		
		
		
		
		
     }
}
	 
	


