
package asynchproducers;


import java.sql.Timestamp;
import java.util.Date;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;


public class Main {
   @Resource(lookup = "jms/myConnectionFactory")
   private static ConnectionFactory  connectionFactory;
   @Resource(lookup = "jms/myTopic")
   private  static Topic topic;
    public static void main(String[] args) throws JMSException {
        int count=0;
        Connection connection =connectionFactory.createConnection();
        try{
            
            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer =session.createProducer(topic);
            TextMessage  message =session.createTextMessage();
            System.out.println("Initializing..........");
            
            long startTime = System.nanoTime();
              long delayTime = 10000000000L; 
             while (count<=10) {     
                 long currentTime = System.nanoTime();
            if (currentTime - startTime >= delayTime) {
        // do something
        //System.out.println("Hello World!");
       // Date date=new Date();
       Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        message.setText(timestamp.toString());
                
        producer.send(message);
                System.out.println("Book Produced  On......"+count);
        
        startTime = System.nanoTime();
        count++;
        if(count==11){
            System.out.println("Done");
        }
    }

    }//
            
        }finally{
            if(connection !=null){
                try{
                    
                connection.close();}
                catch(JMSException e){
                    System.out.println(e.getMessage());
                }
                
                
            }
        }
    }
    
}
