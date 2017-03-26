package ua.malibu.ostpc.utils;

import be.quodlibet.boxable.*;
import be.quodlibet.boxable.text.WrappingFunction;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReportGenerator {
    private static final Logger logger = Logger.getLogger(ReportGenerator.class);

    private static final float MARGIN = 30.0f;
    private static final int HEADER_FONT_SIZE = 16;
    private static final int TABLE_FONT_SIZE = 10;
    private static final WrappingFunction WRAPPING_FUNCTION
            = t -> t.split("(?<=\\s|,|:|;)");

    private Schedule schedule;
    private Report report = new Report();

    public ReportGenerator(Schedule schedule) {
        this.schedule = schedule;
    }

    public void generateReport() {
        generateReport(schedule.getStartDate(), schedule.getEndDate());
    }

    public void generateReport(DateTime startDate, DateTime endDate) {
        Map<User, ReportUnit> reportUnits = new HashMap<>();
        Set<User> users = schedule.getWorkingDays().stream()
                                    .filter(day -> day.getDate().isBefore(endDate.withTime(23, 59, 59, 999))
                                            && day.getDate().isAfter(startDate.withTime(0, 0, 0, 0)))
                                    .flatMap(day -> day.getShifts().stream())
                                    .flatMap(shift -> shift.getWorkersOnShift().stream())
                                    .collect(Collectors.toSet());
        users.forEach(user -> reportUnits.put(user, new ReportUnit()));

        for (WorkDay day : schedule.getWorkingDays()
                .stream()
                .filter(day -> day.getDate().isBefore(endDate.withTime(23, 59, 59, 999))
                        && day.getDate().isAfter(startDate.withTime(0, 0, 0, 0)))
                .collect(Collectors.toList())) {
           Club curClub = day.getClub();
           day.getShifts().forEach(shift ->
                    shift.getWorkersOnShift().forEach(user ->
                            reportUnits.get(user).addShift(curClub)));
        }
        report.setReportUnits(reportUnits);
        report.setStatus(schedule.getStatus());
        report.setStartTime(schedule.getStartDate());
        report.setEndTime((schedule.getEndDate().isBefore(DateTime.now().withTime(23, 59, 59, 999)))
                          ? schedule.getEndDate()
                          : DateTime.now());
        generatePdfReport();
    }

    private PDDocument generatePdfReport() {
        try(PDDocument pdDocument = new PDDocument()) {
            PDPage page = new PDPage();
            String begString = "Отчёт по работе сотрудников согласно расписанию ";
            String numberString = "№" + schedule.getId();
            float width = page.getMediaBox().getWidth();
            float curHeight = page.getMediaBox().getHeight() - MARGIN;
            PDFont font = PDType0Font.load(pdDocument,
                    new File("./src/main/resources/PTM55F.ttf"));
            PDFont fontBold = PDType0Font.load(pdDocument,
                    new File("./src/main/resources/PTM75F.ttf"));

            pdDocument.addPage(page);
            PDPageContentStream stream = new PDPageContentStream(pdDocument, page);
            stream.setFont(font, HEADER_FONT_SIZE);

            /* Header generation */
            stream.beginText();
            if ((getStringWidth(begString + numberString, font, HEADER_FONT_SIZE)) < (width - 2 * MARGIN)) {
                stream.newLineAtOffset(width / 2 - getStringWidth
                                (begString + numberString, font, HEADER_FONT_SIZE) / 2,
                        curHeight);
                stream.showText(begString);
            } else {
                stream.newLineAtOffset(width / 2 - getStringWidth
                                (begString, font, HEADER_FONT_SIZE) / 2,
                        curHeight);
                stream.showText(begString);
                stream.newLineAtOffset(getStringWidth(begString, font, HEADER_FONT_SIZE) / 2
                                - getStringWidth(numberString, font, HEADER_FONT_SIZE) / 2,
                        - MARGIN);
                curHeight -= MARGIN;
            }
            stream.setFont(fontBold, HEADER_FONT_SIZE);
            stream.showText(numberString);
            stream.setFont(font, HEADER_FONT_SIZE);
            curHeight -= MARGIN;
            stream.endText();
            stream.beginText();
            stream.newLineAtOffset(MARGIN, curHeight);
            stream.showText("Дата начала: " + report.getStartTime().toString("dd-MM-yyyy"));
            stream.newLineAtOffset(width - MARGIN * 2 - getStringWidth
                    ("Дата окончания: " + report.getEndTime().toString("dd-MM-yyyy"),
                            font, HEADER_FONT_SIZE), 0);
            stream.showText("Дата окончания: " + report.getEndTime().toString("dd-MM-yyyy"));
            curHeight -= MARGIN * 2;
            stream.endText();
            stream.beginText();
            stream.newLineAtOffset(MARGIN, curHeight);
            stream.showText("Статус расписания: ");
            switch (schedule.getStatus()) {
                case CURRENT: stream.showText("ТЕКУЩИЙ"); break;
                case PAST: stream.showText("ПРОШЕДШИЙ");
            }
            curHeight -= MARGIN;
            stream.endText();

            /* Table generation */
            float firstColWidthRel = 40f;
            float secondColWidthRel = 60f;
            Row<PDPage> curRow;
            BaseTable table = new BaseTable(curHeight, page.getMediaBox().getHeight() - MARGIN,
                    MARGIN, MARGIN, width - MARGIN * 2, MARGIN, pdDocument, page,
                    true, true);
            float firstColWidthAbs;
            float secondColWidthAbs;
            Row<PDPage> headerRow = table.createRow(MARGIN);
            table.addHeaderRow(headerRow);

            headerRow.createCell(firstColWidthRel, "Администратор",
                    HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
            firstColWidthAbs = headerRow.getCells().get(0).getInnerWidth();
            headerRow.createCell(secondColWidthRel, "Занятость",
                    HorizontalAlignment.CENTER, VerticalAlignment.MIDDLE);
            secondColWidthAbs = headerRow.getCells().get(1).getInnerWidth();
            for (Map.Entry<User, ReportUnit> entry : report.getReportUnits().entrySet()) {
                curRow = table.createRow(10);
                Cell<PDPage> userCell = curRow.createCell(firstColWidthRel, "");
                fillUserCell(userCell, firstColWidthAbs, entry.getKey(), font);
                Cell<PDPage> activityCell = curRow.createCell(secondColWidthRel, "");
                fillActivityCell(activityCell, secondColWidthAbs, entry.getValue(), font);
            }
            table.getRows().forEach(row -> row.getCells()
                    .stream()
                    .peek(cell -> cell.setFont(font))
                    .peek(cell -> cell.setFontBold(fontBold))
                    .peek(cell -> cell.setFontSize(TABLE_FONT_SIZE))
                    .forEach(cell -> cell.setWrappingFunction(WRAPPING_FUNCTION)));
            stream.beginText();
            curHeight = table.draw() - MARGIN * 2;
            stream.newLineAtOffset(MARGIN, curHeight);
            stream.showText("Дата и время составления отчёта: "
                    + DateTime.now().toString("dd-MM-yyyy HH:mm:ss"));
            stream.endText();
            stream.close();
            pdDocument.save("D://report.pdf");
            return pdDocument;
        } catch (IOException e) {
            logger.error("IOException happened during report generation." +
                    " Wrapped by runtime exception.", e);
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, 50000,
                    "Exception duting report generation.");
        }

    }

    private float getStringWidth(String str, PDFont font, int fontSize) throws IOException{
        return font.getStringWidth(str) / 1000 * fontSize;
    }

    private float getSpaceWidth(PDFont font, int fontSize) {
        return font.getSpaceWidth() / 1000 * fontSize;
    }

    private void fillUserCell(Cell<PDPage> cell, float firstColWidth, User curUser,
                                      PDFont font) throws IOException{
        String curString;

        curString = "Имя: " + curUser.getName() + " " + curUser.getSurname();
        cell.setText(curString + breakLine(curString, firstColWidth, font));
        if (curUser.getBirthday() != null) {
            curString = "Дата рождения: " + curUser.getBirthday().toString("dd-MM-yyyy");
            cell.setText(cell.getText() + curString
                    + breakLine(curString, firstColWidth, font));
        }
        curString = "E-mail: " + curUser.getEmail();
        cell.setText(cell.getText() + curString
                + breakLine(curString, firstColWidth, font));
        if (curUser.getPhoneNumber() != null) {
            curString = "Телефонный номер: " + curUser.getPhoneNumber();
            cell.setText(cell.getText() + curString);
        }
    }

    private void fillActivityCell(Cell<PDPage> cell, float secondColWidth, ReportUnit unit,
                                         PDFont font) throws IOException {
        Map<Club, Integer> shifts = unit.getShifts();
        String curString;
        Club club;
        long hoursOverall = 0;
        Iterator<Club> iter = shifts.keySet().iterator();

        while (iter.hasNext()){
            club = iter.next();
            curString = "Название клуба: " + club.getTitle();
            cell.setText(cell.getText() + curString
                    + breakLine(curString, secondColWidth, font));
            if (club.getAddress() != null) {
                curString = "Адрес клуба: " + club.getAddress();
                cell.setText(cell.getText() + curString
                        + breakLine(curString, secondColWidth, font));
            }
            curString = "Количество отработанных смен: " + shifts.get(club);
            cell.setText(cell.getText() + curString
                    + breakLine(curString, secondColWidth, font));
            curString = "Количество отработанных часов: "
                    + shifts.get(club) * club.getShiftDuration();
            hoursOverall += shifts.get(club) * club.getShiftDuration();
            cell.setText(cell.getText() + curString
                    + breakLine(curString, secondColWidth, font));
            cell.setText(cell.getText() + breakLine("", secondColWidth, font));
        }
        curString = "Всего отработано смен: " + shifts.values().stream().reduce((a, b) -> a + b).get();
        cell.setText(cell.getText() + curString + breakLine(curString, secondColWidth, font));
        curString = "Всего отработано часов: " + hoursOverall;
        cell.setText(cell.getText() + curString + breakLine(curString, secondColWidth, font));
    }

    private String breakLine(String str, float cellWidth, PDFont font) throws IOException {
        float lastLineWidth = getLastLineWidth(str, font, cellWidth);
        int amount = (int)((cellWidth - lastLineWidth) / getSpaceWidth(font, TABLE_FONT_SIZE));
        char[] spaces = new char[amount];

        Arrays.fill(spaces, ' ');
        return new String(spaces);
    }

    private float getLastLineWidth(String str, PDFont font, float cellWidth) throws IOException{
        float strWidth = getStringWidth(str, font, TABLE_FONT_SIZE);
        float lastLineWidth = 0;
        float curWidth;
        String curToken;

        if (strWidth > cellWidth) {
            Deque<String> tokens = new LinkedList<>(Arrays.asList(WRAPPING_FUNCTION.getLines(str)));
            while (!tokens.isEmpty()) {
                curToken = tokens.pollFirst();
                curWidth = getStringWidth(curToken, font, TABLE_FONT_SIZE);
                lastLineWidth += curWidth;
                if (curWidth > cellWidth) {
                    lastLineWidth = lastLineWidth - cellWidth * (int)(lastLineWidth / cellWidth);
                } else if (lastLineWidth > cellWidth) {
                    lastLineWidth = curWidth;
                }
            }
        } else {
            lastLineWidth = strWidth;
        }
        return lastLineWidth;
    }
}
