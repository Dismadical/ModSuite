package me.dismadical.modsuite.Reports;

import me.dismadical.modsuite.ModSuite;

/**
 * @author Dismadical (me.dismadical.modsuite.Reports)
 * 7/13/2022
 * 7:53 PM
 * ModSuite
 * me.dismadical.modsuite.Reports
 */

public class Report {

    private int id;
    private String reporter;
    private String reported;
    private String reason;

   public Report(int id) {
       this.id = id;
       this.reporter = ModSuite.getInstance().getConfig().getString("Reports." + id + ".Reporter");
       this.reported = ModSuite.getInstance().getConfig().getString("Reports." + id + ".Reported");
       this.reason = ModSuite.getInstance().getConfig().getString("Reports." + id + ".Reason");
   }

   public int getId() {
       return id;
   }

    public String getReporter() {
         return reporter;
    }

    public String getReported() {
        return reported;
    }

    public String getReason() {
        return reason;
    }

}
