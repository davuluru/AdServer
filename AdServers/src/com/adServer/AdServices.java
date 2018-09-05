package com.adServer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import net.sf.json.JSONObject;
@Path("/AdServices")
public class AdServices {
   
	@Context private HttpServletRequest request;	
	@Context private HttpServletResponse response;
	@POST
	@Path("/createAd")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Map createAd(@FormParam("ad_content") String ad_content,
		   @FormParam("partner_id") String partner_id, 
		   @FormParam("duration") int duration){
      
       
       List adsList = new ArrayList();
       JSONObject obj = new JSONObject();
       Map resultMap = new HashMap();
       try{
           
    	   Calendar calendar = Calendar.getInstance();
           HttpSession session = request.getSession();
           if(session.getAttribute("AdsList") != null)
               adsList = (List)session.getAttribute("AdsList");
           
           String statusMsg = "Success";
          
           boolean found=false;
           AdPojo adPojo = new AdPojo();
           for(int i=0; i<adsList.size();i++){
               adPojo = (AdPojo)adsList.get(i);
               if(partner_id.equalsIgnoreCase(adPojo.getPartner_id())){
                   statusMsg="Already Partner ID Available";
                   found = true;
                   break;
               }
               
           }
               if(!found){
	               AdPojo pojoObj = new AdPojo();
	               pojoObj.setAd_content(ad_content);
	               pojoObj.setDuration(duration);
	               pojoObj.setPartner_id(partner_id);	               
	               pojoObj.setCurrent_time(calendar.getTimeInMillis() / 1000L);
	               adsList.add(pojoObj);
               }
               session.setAttribute("AdsList", adsList);
               obj.put("Status","Success");
               obj.put("AdsResultList", adsList);
               resultMap.put("StatusMessage", statusMsg);
               resultMap.put("AdsResultList", adsList);
                
           }catch(Exception e){
               e.printStackTrace();
           }
          
        
           return resultMap;
       }    
       
       @GET
       @Path("/{param}")
       public List getAds(@PathParam("param") String partner_id){           
           List adsResultList = new ArrayList();
           try{
               HttpSession session = request.getSession();
               List adsList = new ArrayList();
               if(session.getAttribute("AdsList")!=null)
            	   adsList = (List)session.getAttribute("AdsList");
               JSONObject obj = new JSONObject();            
               AdPojo adPojo = new AdPojo();
               Calendar calendar = Calendar.getInstance();
               long current_time = calendar.getTimeInMillis() / 1000L;
              
               for(int i=0; i<adsList.size();i++){
               		adPojo = (AdPojo)adsList.get(i);
               		
               		if(partner_id.equalsIgnoreCase(adPojo.getPartner_id())
               			&&current_time<=(adPojo.getCurrent_time()+adPojo.getDuration())){
               				adsResultList.add(adPojo);
               		}
               }
           
           obj.put("AdsResultList",adsResultList);
         }catch(Exception e){
           e.printStackTrace();
       }      
       return adsResultList;
      
   }
}
               