package me.dismadical.modsuite.Reports;

import me.dismadical.modsuite.ModSuite;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author Dismadical (me.dismadical.modsuite.Reports)
 * 7/13/2022
 * 7:55 PM
 * ModSuite
 * me.dismadical.modsuite.Reports
 */

public class ReportHandler {

    public static void createReport(Player reporter, Player reported, String reason) {
        Random random = new Random();
        int id = random.nextInt(100000);
        while (getReports().keySet().contains(id)) {
            id = random.nextInt(100000);
        }
        ModSuite.getInstance().getConfig().set("Reports." + id + ".Reporter", reporter.getName());
        ModSuite.getInstance().getConfig().set("Reports." + id + ".Reported", reported.getName());
        ModSuite.getInstance().getConfig().set("Reports." + id + ".Reason", reason);
    }

    public static void deleteReport(int id) {
        ModSuite.getInstance().getConfig().set("Reports." + id, null);
    }

    public static HashMap<Integer, Report> getReports() {
        HashMap<Integer, Report> reports = new HashMap<>();
        if (ModSuite.getInstance().getConfig().getConfigurationSection("Reports") == null) return new HashMap<>();
        List<String> ids = new ArrayList<>(ModSuite.getInstance().getConfig().getConfigurationSection("Reports").getKeys(false));
        ids.forEach(id -> reports.put(Integer.parseInt(id), new Report(Integer.parseInt(id))));
        return reports;
    }

    public static List<Report> getReportList() {
        List<Report> reports = new ArrayList<>();
        getReports().forEach((id, report) -> reports.add(report));
        return reports;
    }

}
