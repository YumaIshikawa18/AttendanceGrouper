package com.yuzukiku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * AttendanceGroupApp
 *
 * A tool for dividing confirmed attendees into groups.
 * If the number of attendees isn't evenly divisible by the group count,
 * the extras are distributed one by one in round-robin fashion so some groups
 * may have one more member.
 *
 * Usage:
 *   java -jar AttendanceGroupApp.jar <入力CSV> <グループ数>
 *
 * CSV columns: 名前,出席,応答
 * Only rows with 応答="承諾済み" are considered. 名前 can include <email>,
 * but output prints only the name.
 */
public class AttendanceGroupApp {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java -jar AttendanceGroupApp.jar <inputCSV> <groupCount>");
            System.exit(1);
        }

        Path csvPath = Path.of(args[0]);
        int groupCount = Integer.parseInt(args[1]);

        // Read CSV and filter accepted attendees
        List<Attendee> accepted = new ArrayList<>(Files.lines(csvPath)
                .skip(1) // skip header
                .map(line -> line.split(",", 3))
                .filter(cols -> cols.length >= 3 && "承諾済み".equals(cols[2].trim()))
                .map(cols -> Attendee.of(cols[0].trim()))
                .toList());

        int total = accepted.size();
        if (total == 0) {
            System.err.println("No confirmed attendees found.");
            System.exit(1);
        }

        // Initialize empty groups
        List<List<Attendee>> groups = IntStream.range(0, groupCount)
                .mapToObj(i -> new ArrayList<Attendee>().reversed())
                .toList();

        // Shuffle and distribute in round-robin
        Collections.shuffle(accepted);
        for (int i = 0; i < total; i++) {
            int gi = i % groupCount;
            groups.get(gi).add(accepted.get(i));
        }

        // Output result
        for (int i = 0; i < groupCount; i++) {
            List<Attendee> group = groups.get(i);
            System.out.printf("=== グループ %d (%d 人) ===%n", i + 1, group.size());
            for (Attendee a : group) {
                System.out.println("  - " + a);
            }
            System.out.println();
        }
    }

    /**
     * Record representing an attendee's name only.
     * Parses "Name <email>" and keeps only the name part.
     */
    public record Attendee(String name) {
        public static Attendee of(String s) {
            int lt = s.indexOf('<');
            if (lt >= 0) {
                return new Attendee(s.substring(0, lt).trim());
            }
            return new Attendee(s);
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
