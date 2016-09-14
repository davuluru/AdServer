package com.adServer;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class AdPojo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int duration;
    private String partner_id;
    private String ad_content;       
    private long current_time;
   
   
    public long getCurrent_time() {
        return current_time;
    }
    public void setCurrent_time(long current_time) {
        this.current_time = current_time;
    }
    public AdPojo(){
        
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String getPartner_id() {
        return partner_id;
    }
    public void setPartner_id(String partner_id) {
        this.partner_id = partner_id;
    }
    public String getAd_content() {
        return ad_content;
    }
    public void setAd_content(String ad_content) {
        this.ad_content = ad_content;
    }
       
}