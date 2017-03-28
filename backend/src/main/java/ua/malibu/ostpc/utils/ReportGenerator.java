package ua.malibu.ostpc.utils;

import be.quodlibet.boxable.*;
import be.quodlibet.boxable.text.WrappingFunction;
import be.quodlibet.boxable.utils.FontUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.cos.COSInputStream;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPageable;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import ua.malibu.ostpc.exceptions.rest.RestException;
import ua.malibu.ostpc.models.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReportGenerator {
    private static final Logger logger = Logger.getLogger(ReportGenerator.class);

    private static final float MARGIN = 30.0f;
    private static final int HEADER_FONT_SIZE = 16;
    private static final int TABLE_FONT_SIZE = 10;
    private static final WrappingFunction WRAPPING_FUNCTION
            = t -> t.split("(?<=\\s|,|:|;)");

    private Schedule schedule;
    private Report report = new Report();
    private PDDocument pdDocument;
    private float curHeight;
    private float width;
    private float tableLineSpacing;
    private PDFont font;
    private PDFont fontBold;

    public ReportGenerator(Schedule schedule) {
        this.schedule = schedule;
    }

    public InputStream generateReport() {
        return generateReport(schedule.getStartDate(), schedule.getEndDate());
    }

    public InputStream generateReport(DateTime startDate, DateTime endDate) {
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
        return generatePdfReport();
    }

    private InputStream generatePdfReport() {
        try {
            pdDocument = new PDDocument();
            PDPage page = new PDPage();
            String begString = "Отчёт по работе сотрудников согласно расписанию ";
            String numberString = "№" + schedule.getId();
            width = page.getMediaBox().getWidth();
            curHeight = page.getMediaBox().getHeight() - MARGIN;
            font = PDType0Font.load(pdDocument,
                    new File("./src/main/resources/PTM55F.ttf"));
            fontBold = PDType0Font.load(pdDocument,
                    new File("./src/main/resources/PTM75F.ttf"));

            pdDocument.addPage(page);
            PDPageContentStream stream = new PDPageContentStream(pdDocument, page);
            stream.setFont(font, HEADER_FONT_SIZE);

            /* Header generation */
            stream.beginText();
            stream.setLeading(1);
            if ((getStringWidth(begString + numberString, HEADER_FONT_SIZE)) < (width - 2 * MARGIN)) {
                stream.newLineAtOffset(width / 2 - getStringWidth
                                (begString + numberString, HEADER_FONT_SIZE) / 2,
                        curHeight);
                stream.showText(begString);
            } else {
                stream.newLineAtOffset(width / 2 - getStringWidth
                                (begString, HEADER_FONT_SIZE) / 2,
                        curHeight);
                stream.showText(begString);
                stream.newLineAtOffset(getStringWidth(begString, HEADER_FONT_SIZE) / 2
                                - getStringWidth(numberString, HEADER_FONT_SIZE) / 2,
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
                            HEADER_FONT_SIZE), 0);
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
            stream.close();

            /* Table generation */
            float firstColWidthRel = 40f;
            float secondColWidthRel = 60f;
            BaseTable table = new BaseTable(curHeight, page.getMediaBox().getHeight(),
                    MARGIN, MARGIN, width - MARGIN * 2, MARGIN, pdDocument, page,
                    true, true);
            float firstColWidthAbs;
            Row<PDPage> headerRow = table.createRow(MARGIN);
            tableLineSpacing = table.getLineSpacing();

            firstColWidthAbs = createHeaderCell(headerRow, "Администратор", firstColWidthRel)
                    .getInnerWidth();
            createHeaderCell(headerRow, "Занятость", secondColWidthRel);
            curHeight -= headerRow.getHeight();
            for (Map.Entry<User, ReportUnit> entry : report.getReportUnits().entrySet()) {
                Row<PDPage> row = table.createRow(1);
                fillUserCell(createCell(row, "", firstColWidthRel), firstColWidthAbs, entry.getKey());
                if (curHeight < table.getMargin()) {
                    curHeight = table.getCurrentPage().getMediaBox().getHeight()
                            - MARGIN - table.getRows().get(0).getHeight();
                } else {
                    curHeight += row.getCells().get(0).getHeight();
                }
                fillActivityCell(table, firstColWidthRel, secondColWidthRel, entry.getValue());
            }
            table.addHeaderRow(headerRow);
            headerRow.getCells().forEach(cell -> {
                cell.setHeaderCell(true);
            });
            curHeight = table.draw() - MARGIN;
            stream = new PDPageContentStream(pdDocument,
                    table.getCurrentPage(),
                    PDPageContentStream.AppendMode.APPEND, true);
            stream.setFont(font, HEADER_FONT_SIZE);
            stream.beginText();
            stream.newLineAtOffset(MARGIN, curHeight);
            stream.showText("Дата и время составления отчёта: "
                    + DateTime.now().toString("dd-MM-yyyy HH:mm:ss"));
            stream.endText();
            stream.close();
            pdDocument.save("D://report.pdf");
            return new PDStream(pdDocument).createInputStream();
        } catch (IOException e) {
            logger.error("IOException happened during report generation." +
                    " Wrapped by runtime exception.", e);
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, 50000,
                    "Exception during report generation.");
        }
        finally {
            try {
                pdDocument.close();
            } catch (IOException e) {
                logger.error("IOException happened during report generation." +
                        " Wrapped by runtime exception.", e);
                throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, 50000,
                        "Exception during document closing.");
            }
        }
    }

    private float getStringWidth(String str, int fontSize) throws IOException{
        return font.getStringWidth(str) / 1000 * fontSize;
    }

    private float getSpaceWidth(int fontSize) {
        return font.getSpaceWidth() / 1000 * fontSize;
    }

    private void fillUserCell(Cell<PDPage> cell, float firstColWidth, User curUser) throws IOException{
        String curString;

        curString = "Имя: " + curUser.getName() + " " + curUser.getSurname();
        cell.setText(curString + breakLine(curString, firstColWidth));
        if (curUser.getBirthday() != null) {
            curString = "Дата рождения: " + curUser.getBirthday().toString("dd-MM-yyyy");
            cell.setText(cell.getText() + curString
                    + breakLine(curString, firstColWidth));
        }
        curString = "E-mail: " + curUser.getEmail();
        cell.setText(cell.getText() + curString
                + breakLine(curString, firstColWidth));
        if (curUser.getPhoneNumber() != null) {
            curString = "Телефонный номер: " + curUser.getPhoneNumber();
            cell.setText(cell.getText() + breakLine(curString, firstColWidth));
        }
        curHeight -= (cell.getTopPadding() + cell.getBottomPadding()
                + cell.getTopBorder().getWidth()
                + cell.getBottomBorder().getWidth());
    }

    private void fillActivityCell(Table<PDPage> table, float firstColWidthRel,
                                  float secondColWidthRel, ReportUnit unit) throws IOException {
        Map<Club, Integer> shifts = unit.getShifts();
        String curString;
        StringBuilder cellContent;
        Club club;
        long hoursOverall = 0;
        Iterator<Club> iter = shifts.keySet().iterator();
        Row<PDPage> curRow = table.getRows().get(table.getRows().size() - 1);
        Cell<PDPage> userCell = table.getRows().get(table.getRows().size() - 1).getCells().get(0),
                cell = createCell(curRow, "", secondColWidthRel);
        float secondColWidthAbs = cell.getInnerWidth();
        float lineStartY = curHeight;
        curHeight -= (cell.getTopPadding() + cell.getTopBorder().getWidth());

        while (iter.hasNext()){
            cellContent = new StringBuilder();
            club = iter.next();
            curString = "Название клуба: " + club.getTitle();
            cellContent.append(curString + breakLine(curString, secondColWidthAbs));
            if (club.getAddress() != null) {
                curString = "Адрес клуба: " + club.getAddress();
                cellContent.append(curString + breakLine(curString, secondColWidthAbs));
            }
            curString = "Количество отработанных смен: " + shifts.get(club);
            cellContent.append(curString + breakLine(curString, secondColWidthAbs));
            curString = "Количество отработанных часов: "
                    + (hoursOverall += shifts.get(club) * club.getShiftDuration());
            cellContent.append(curString + breakLine(curString, secondColWidthAbs));
            cellContent.append("" + breakLine("", secondColWidthAbs));
            if (!iter.hasNext()) {
                curString = "Всего отработано смен: " + shifts.values().stream().reduce((a, b) -> a + b).get();
                cellContent.append(curString + breakLine(curString, secondColWidthAbs));
                curString = "Всего отработано часов: " + hoursOverall;
                cellContent.append(curString + breakLine(curString, secondColWidthAbs));
                curHeight = Math.min(curHeight, lineStartY - userCell.getHeight()
                        + userCell.getBottomPadding() + userCell.getBottomBorder().getWidth());
            }
            if ((curHeight - cell.getBottomPadding() - cell.getBottomBorder().getWidth())
                    < table.getMargin()) {
                lineStartY = table.getCurrentPage().getMediaBox().getHeight()
                        - MARGIN - table.getRows().get(0).getHeight();
                curHeight = lineStartY - cell.getTopBorder().getWidth() - cell.getTopPadding();
                if (cell.getText() != "") {
                    cell.setText(cell.getText().trim());
                    curRow = table.createRow(1);
                    userCell = createCell(curRow, userCell.getText(), firstColWidthRel);
                    cell = createCell(curRow, cellContent.toString(), secondColWidthRel);
                } else {
                        cell.setText(cellContent.toString());
                    }
                curHeight -= cell.getTextHeight();
            } else {
                    cell.setText(cell.getText() + cellContent.toString());
                }
            }
        curHeight -= (cell.getBottomPadding() + cell.getBottomBorder().getWidth());
    }

    private String breakLine(String str, float cellWidth) throws IOException {
        float lastLineWidth = getLastLineWidth(str, cellWidth);
        int amount = (int)((cellWidth - lastLineWidth) / getSpaceWidth(TABLE_FONT_SIZE));
        char[] spaces = new char[amount];

        Arrays.fill(spaces, ' ');
        return new String(spaces);
    }

    private float getLastLineWidth(String str, float cellWidth) throws IOException{
        float strWidth = getStringWidth(str, TABLE_FONT_SIZE);
        float lastLineWidth = 0;
        float curWidth;
        String curToken, curSymbol;

        if (strWidth > cellWidth) {
            Deque<String> tokens = new LinkedList<>(Arrays.asList(WRAPPING_FUNCTION.getLines(str)));
            while (!tokens.isEmpty()) {
                curToken = tokens.pollFirst();
                curWidth = getStringWidth(curToken, TABLE_FONT_SIZE);
                if (curWidth > cellWidth) {
                    if (lastLineWidth != 0) {
                        lastLineWidth -= getSpaceWidth(TABLE_FONT_SIZE);
                    }
                    Deque<String> symbols
                            = new LinkedList<>(Arrays.stream(curToken.split(""))
                            .collect(Collectors.toList()));
                    while (!symbols.isEmpty()) {
                        curSymbol = symbols.pollFirst();
                        lastLineWidth += getStringWidth(curSymbol, TABLE_FONT_SIZE);
                        if (lastLineWidth > cellWidth) {
                            lastLineWidth = getStringWidth(curSymbol, TABLE_FONT_SIZE);
                            curHeight -= (FontUtils.getHeight(font, TABLE_FONT_SIZE) * tableLineSpacing);
                        }
                    }
                } else if ((lastLineWidth += curWidth) > cellWidth) {
                    lastLineWidth = curWidth;
                    curHeight -= FontUtils.getHeight(font, TABLE_FONT_SIZE) * tableLineSpacing;
                }
            }
        } else {
            lastLineWidth = strWidth;
        }
        curHeight -= FontUtils.getHeight(font, TABLE_FONT_SIZE) * tableLineSpacing;
        return lastLineWidth;
    }

    private Cell<PDPage> createHeaderCell(Row<PDPage> row, String value, float width) {
        Cell<PDPage> toReturn = createCell(row, value, width);
        toReturn.setValign(VerticalAlignment.MIDDLE);
        toReturn.setAlign(HorizontalAlignment.CENTER);
        toReturn.setFont(fontBold);
        return toReturn;
    }

    private Cell<PDPage> createCell(Row<PDPage> row, String value, float width) {
        Cell<PDPage> toReturn = row.createCell(width, value);
        toReturn.setFont(font);
        toReturn.setFontBold(fontBold);
        toReturn.setFontSize(TABLE_FONT_SIZE);
        toReturn.setWrappingFunction(WRAPPING_FUNCTION);
        return toReturn;
    }
}
