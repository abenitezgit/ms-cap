package com.sinbugs.controllers;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 
 * @author admin
 * 
 * Esta clase será reconocida solo si se encuentra en el package que fue especificado en el main application
 * 
 * @SpringBootApplication(scanBasePackages = {"com.sinbugs.controllers"})
 *
 *
 */

@Component
public class Scheduler {
   @Scheduled(cron = "2,3,4 * 11 * * ?")
   public void cronJobSch() {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
      Date now = new Date();
      String strDate = sdf.format(now);
      System.out.println("Java cron job expression:: " + strDate);
   }
}