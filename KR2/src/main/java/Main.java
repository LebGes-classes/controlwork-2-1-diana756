import java.io.*;
import java.nio.file.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
    public static void main(String[] args) {
        Map<BroadcastsTime, List<Program>> data = new TreeMap<>();
        List<Program> allPrograms = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get("data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String time = line.trim();
                String title = br.readLine().trim();
                String channel = br.readLine().trim();

                Program program = new Program(channel, new BroadcastsTime(time), title);
                allPrograms.add(program);
                data.computeIfAbsent(new BroadcastsTime(time), k -> new ArrayList<>()).add(program);

                // Дополнительное чтение пустой строки после каждой программы
                br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


            // Вывод всех программ
            allPrograms.sort(Comparator.comparing(Program::getChannel)
                    .thenComparing(Program::getTime));
            for (Program program : allPrograms) {
                System.out.println(program);
            }

            // Вывод программ, которые идут сейчас
            BroadcastsTime now = new BroadcastsTime(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            for (Program program : allPrograms) {
                if (program.getTime().equals(now)) {
                    System.out.println("Now showing: " + program);
                }
            }

            // Программы по названию
            String searchTitle = "Some Title";
            for (Program program : allPrograms) {
                if (program.getTitle().equalsIgnoreCase(searchTitle)) {
                    System.out.println("Found program: " + program);
                }
            }

            // Программы определенного канала, которые идут сейчас
            String searchChannel = "Some Channel";
            for (Program program : allPrograms) {
                if (program.getChannel().equalsIgnoreCase(searchChannel) && program.getTime().equals(now)) {
                    System.out.println("Now showing on " + searchChannel + ": " + program);
                }
            }

            // Программы определенного канала в промежутке времени
            BroadcastsTime startTime = new BroadcastsTime("10:00"); // Начало промежутка
            BroadcastsTime endTime = new BroadcastsTime("12:00"); // Конец промежутка
            for (Program program : allPrograms) {
                if (program.getChannel().equalsIgnoreCase(searchChannel) &&
                        program.getTime().between(startTime, endTime)) {
                    System.out.println("Program on " + searchChannel + " between " + startTime + " and " + endTime + ": " + program);
                }
            }


            // Сохранение данных в Excel
            saveToExcel(allPrograms);
        }

        private static void saveToExcel(List<Program> allPrograms) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("ProgramList");

            int rowCount = 0;
            for (Program program : allPrograms) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(program.getChannel());
                row.createCell(1).setCellValue(program.getTime().toString());
                row.createCell(2).setCellValue(program.getTitle());
            }

            try (FileOutputStream outputStream = new FileOutputStream("ProgramList.xlsx")) {
                workbook.write(outputStream);
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



