package com.bluemobi.utils;

import java.text.DecimalFormat;

import javax.servlet.http.HttpSession;

import org.apache.cassandra.thrift.Cassandra.system_add_column_family_args;
import org.apache.commons.fileupload.ProgressListener;

import sun.security.util.Debug;

public class PJProgressListener implements ProgressListener{  
    private HttpSession session;  
    public PJProgressListener() {  
    }  
    public PJProgressListener(HttpSession _session) {  
        session=_session;  
        ProgressEntity ps = new ProgressEntity();  
        session.setAttribute("upload_ps", ps);  
    }  
    public void update(long pBytesRead, long pContentLength, int pItems) {  
        ProgressEntity ps = (ProgressEntity) session.getAttribute("upload_ps");  
        ps.setpBytesRead(pBytesRead);  
        ps.setpContentLength(pContentLength);  
        ps.setpItems(pItems);  
        //更新  
       // session.setAttribute("upload_ps", ps);  
        
        double mBytes = pBytesRead ;  
        double total=pContentLength; 
        if(total<=0)return;
        double read=(mBytes/total); 
        read = read*100;
        DecimalFormat df = new DecimalFormat("#");  
        session.setAttribute("read", Integer.parseInt(df.format(read)));  
    }  
    
    /** 
     * 文件上传进度信息 
     * @author Van 
     * 
     */  
    public class ProgressEntity {  
          
          
        private long pBytesRead = 0L;  
        private long pContentLength = 0L;  
        private int pItems;  
        public long getpBytesRead() {  
            return pBytesRead;  
        }  
        public void setpBytesRead(long pBytesRead) {  
            this.pBytesRead = pBytesRead;  
        }  
        public long getpContentLength() {  
            return pContentLength;  
        }  
        public void setpContentLength(long pContentLength) {  
            this.pContentLength = pContentLength;  
        }  
        public int getpItems() {  
            return pItems;  
        }  
        public void setpItems(int pItems) {  
            this.pItems = pItems;  
        }  
        @Override  
        public String toString() {  
            return "ProgressEntity [pBytesRead=" + pBytesRead + ", pContentLength="  
                    + pContentLength + ", pItems=" + pItems + "]";  
        }  
    }  
  
}  
