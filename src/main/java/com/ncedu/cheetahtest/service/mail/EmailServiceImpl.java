package com.ncedu.cheetahtest.service.mail;

import com.ncedu.cheetahtest.dao.action.ActionDao;
import com.ncedu.cheetahtest.dao.actscenario.ActScenarioDao;
import com.ncedu.cheetahtest.dao.project.ProjectDao;
import com.ncedu.cheetahtest.dao.testcase.TestCaseDao;
import com.ncedu.cheetahtest.dao.user.UserDao;
import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.actscenario.ActScenario;
import com.ncedu.cheetahtest.entity.mail.SpecificReport;
import com.ncedu.cheetahtest.entity.project.Project;
import com.ncedu.cheetahtest.entity.testcase.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService{

    public static final String NET_CRACKER_USERNAME = "spring.mail.username";
    public static final String LOG_SEND_EMAIL = "Email has been sent to ";
    public static final String CANNT_SAVE_MESSAG = "Couldn't send message: %s";
    public static final String HTML_NOT_FOUND = "Couldn't find html file by given path: %s";
    public static final String BR = "<br>\n";
    public static final String HTML_TAB = "&emsp;&emsp;&emsp;&emsp;&emsp;";
    public static final String PATH_TO_XLS = "src/main/resources/excel/mail.xls";

    private final JavaMailSender emailSender;
    private final HtmlMail htmlMail;
    private final Environment environment;
    @Value("${frontend.ulr}")
    private String FRONT_URL;
    private UserDao userDao;
    private TestCaseDao testCaseDao;
    private ProjectDao projectDao;
    private ActScenarioDao actScenarioDao;
    private ActionDao actionDao;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender, HtmlMail htmlMail, Environment environment, UserDao userDao,
                            TestCaseDao testCaseDao, ProjectDao projectDao, ActScenarioDao actScenarioDao, ActionDao actionDao) {
        this.emailSender = emailSender;
        this.htmlMail = htmlMail;
        this.environment = environment;
        this.userDao = userDao;
        this.testCaseDao = testCaseDao;
        this.projectDao = projectDao;
        this.actScenarioDao = actScenarioDao;
        this.actionDao = actionDao;
    }

    @Override
    public void sendSimpleMessage(String to, String text, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Objects.requireNonNull(environment.getProperty(NET_CRACKER_USERNAME)));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachment(String to, String text, String subject, String htmlPath) {

        MimeMessage message = emailSender.createMimeMessage();
        try {

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(Objects.requireNonNull(environment.getProperty(NET_CRACKER_USERNAME)));
            helper.setTo(to);
            helper.setSubject(subject);

            String htmlString = htmlMail.getHtmlWithStringInside(text, htmlPath)
                    .orElseThrow(FileNotFoundException::new);

            helper.setText("", htmlString);
            emailSender.send(message);

            log.info(LOG_SEND_EMAIL + to);

        } catch (MessagingException e) {
            log.error(String.format(CANNT_SAVE_MESSAG, e.getMessage()));
        } catch (FileNotFoundException e) {
            log.error(String.format(HTML_NOT_FOUND, e.getMessage()));
        }
    }

    @Override
    public void sendTestCaseReportToAddresses(List<String> emails, int idTestCase, int idProject) {
        String url = FRONT_URL+"/projects/"+idProject+"/test-cases/"+idTestCase;
        String message = "Hi, testcase "+idTestCase+" was completed. \nTo see details, please follow the link:\n"+url;
        log.info(message);
        for ( String email: emails){
            sendSimpleMessage(email,message,"TestCaseInfo");
        }
    }

    @Override
    public void sendGenerateTestCaseReportToAddresses(List<String> emails, int idTestCase, int idProject, String pathHTML) {

        for (String email: emails) {


            MimeMessage message = emailSender.createMimeMessage();


            try (Workbook workbook = new XSSFWorkbook()) {

                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setFrom(Objects.requireNonNull(environment.getProperty(NET_CRACKER_USERNAME)));

                helper.setTo(email);

                boolean isRepeatable = testCaseDao.getTestCaseRepeatable(idTestCase);



                List<String> strs = new ArrayList<>();
                strs.add((isRepeatable ? "Repeatable " : "One-time ") + "test case");
                strs.add(userDao.findUserByEmail(email).getName());
                strs.add(testCaseDao.findTestCaseByProjectIdAndTestCaseId(idProject, idProject).getTitle());
                strs.add(projectDao.findByProjectId(idProject).getTitle());

                int lineCount = 4;

                String titleEmail = isRepeatable ? "Recurrent test case  " :
                        "One-time test case -> [" + strs.get(1) + "] " +
                                " Project -> [" + strs.get(2) + "]  ";

                message.setSubject(titleEmail);

                List<ActScenario> actScenarioList = actScenarioDao.getAllByTestCaseId(idTestCase);

                Sheet sheet = workbook.createSheet(strs.get(1));

                sheet.setColumnWidth(2, 5000);

                Row header = sheet.createRow(0);

                CellStyle headerStyle = workbook.createCellStyle();
                headerStyle.setFillForegroundColor(IndexedColors.TAN.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                XSSFFont font = ((XSSFWorkbook) workbook).createFont();
                font.setFontName("Arial");
                font.setFontHeightInPoints((short) 16);
                font.setBold(true);
                headerStyle.setFont(font);

                Cell headerCell = header.createCell(0);
                headerCell.setCellValue("Title");
                headerCell.setCellStyle(headerStyle);

                headerCell = header.createCell(1);
                headerCell.setCellValue("Type");
                headerCell.setCellStyle(headerStyle);

                headerCell = header.createCell(2);
                headerCell.setCellValue("Description");
                headerCell.setCellStyle(headerStyle);

                headerStyle = workbook.createCellStyle();
                headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                font.setBold(false);
                headerStyle.setFont(font);

                List<Action> actionList = new ArrayList<>();

                int rowCountExcel = 1;
                StringBuilder stringBuilder = new StringBuilder();
                for (ActScenario actScenario : actScenarioList) {
                    lineCount++;
                    actionList.add(actionDao.getActionById(actScenario.getActionId()));
                    header = sheet.createRow(rowCountExcel++);
                    headerCell = header.createCell(0);
                    headerCell.setCellValue(actionList.get(actionList.size() - 1).getTitle());
                    headerCell.setCellStyle(headerStyle);

                    headerCell = header.createCell(1);
                    headerCell.setCellValue(actionList.get(actionList.size() - 1).getType());
                    headerCell.setCellStyle(headerStyle);

                    headerCell = header.createCell(2);
                    headerCell.setCellValue(actionList.get(actionList.size() - 1).getDescription());
                    headerCell.setCellStyle(headerStyle);
                    stringBuilder.append("> " + actionList.get(actionList.size() - 1).getTitle()
                            + HTML_TAB + actionList.get(actionList.size() - 1).getType()
                            + HTML_TAB + actionList.get(actionList.size() - 1).getDescription() + BR);
                }

                String actions = stringBuilder.toString();

                if(isRepeatable) {
                    lineCount++;
                    header = sheet.createRow(rowCountExcel);
                    headerCell = header.createCell(0);
                    String executionDate = testCaseDao.getExecutionDateById(idTestCase);
                    headerCell.setCellValue(executionDate);
                    headerCell.setCellStyle(headerStyle);
                    actions += "<p>Execution date:</p>";
                    actions += "> " + executionDate + BR;
                }

                strs.add(actions);

                Collections.reverse(strs);
                strs.add("height: " + (Integer.toString(lineCount * 100)) + "px;");
                Collections.reverse(strs);


                String htmlString = htmlMail.getHtmlWithStrings(strs, pathHTML).orElseThrow(FileNotFoundException::new);


                helper.setText("", htmlString);

                File excelFile = new File(PATH_TO_XLS);

                FileOutputStream outputStream = new FileOutputStream(PATH_TO_XLS);

                workbook.write(outputStream);
                workbook.close();

                helper.addAttachment("one-test-case.xlsx", excelFile);


                helper.addInline("topImageId", new File("src/main/resources/image/mail.png"));

                emailSender.send(message);

                log.info(LOG_SEND_EMAIL + emails);

            } catch (MessagingException e) {
                log.error(String.format(CANNT_SAVE_MESSAG, e.getMessage()));
            } catch (FileNotFoundException e) {
                log.error(String.format(HTML_NOT_FOUND, e.getMessage()));
            } catch (IOException e) {
                log.error(String.format("Couldn't write excel file: %s", e.getMessage()));
            }
        }

    }

    @Override
    public void sendSpecificReport(SpecificReport specificReport, String pathHTML) {

        for (String email : specificReport.getEmails()) {

            MimeMessage message = emailSender.createMimeMessage();
            try (Workbook workbook = new XSSFWorkbook()) {

                List<String> strs = new ArrayList<>();

                strs.add(userDao.findUserByEmail(email).getName());

                List<Project> projectList = userDao.getProjectsByUserId(userDao.findUserByEmail(email).getId());
                List<TestCase> testCaseList = new ArrayList<>();

                Sheet sheet = workbook.createSheet("Specific");

                message.setSubject("Specific report");


                sheet.setColumnWidth(0, 7000);
                sheet.setColumnWidth(1, 7000);



                int i = 0;
                int iRow = 0;
                Row header = sheet.createRow(iRow++);
                CellStyle headerStyleDarkGray = workbook.createCellStyle();
                CellStyle headerStyleGray = workbook.createCellStyle();
                CellStyle headerStyleTan = workbook.createCellStyle();
                Cell headerCell = header.createCell(0);
                headerCell.setCellValue("Cheetah-test");

                XSSFFont font = ((XSSFWorkbook) workbook).createFont();
                font.setFontName("Arial");
                font.setFontHeightInPoints((short) 16);
                font.setBold(true);

                headerStyleDarkGray.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                headerStyleDarkGray.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                headerStyleDarkGray.setFont(font);
                headerStyleGray.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                headerStyleGray.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                headerStyleGray.setFont(font);
                headerStyleTan.setFillForegroundColor(IndexedColors.TAN.getIndex());
                headerStyleTan.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                headerStyleTan.setFont(font);
                headerCell.setCellStyle(headerStyleTan);
                StringBuilder stringBuilder = new StringBuilder();
                if (specificReport.getSendProjectUse() == true){

                    for (Project project: projectList){
                        stringBuilder.append("<h2>Project" + project.getTitle() + "</h2>\n");
                        header = sheet.createRow(iRow++);


                        headerCell = header.createCell(0);
                        headerCell.setCellValue(project.getTitle());

                        headerCell.setCellStyle(headerStyleDarkGray);
                        int size = testCaseDao.getAmountActiveElementsByProjectId(project.getId());
                        testCaseList = testCaseDao.
                                getActiveTestCasesPaginatedByProjectId(1, size, project.getId());
                        for (TestCase testCase: testCaseList){

                            header = sheet.createRow(iRow++);
                            headerCell = header.createCell(1);
                            headerCell.setCellValue(testCase.getTitle());

                            headerCell.setCellStyle(headerStyleGray);
                            stringBuilder.append(testCase.getTitle() + BR);
                            i++;
                        }
                    }
                }
                strs.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
                if(specificReport.getSendTestCaseAllDone() == true) {
                    stringBuilder.append("<h2>All  test cases</h2>\n");
                    header = sheet.createRow(iRow++);
                    headerCell = header.createCell(0);
                    headerCell.setCellValue("All  test cases");
                    headerCell.setCellStyle(headerStyleDarkGray);
                    for (TestCase testCase: testCaseList) {
                        stringBuilder.append(testCase.getTitle() + BR);
                        header = sheet.createRow(iRow++);
                        headerCell = header.createCell(1);
                        headerCell.setCellValue(testCase.getTitle());
                        headerCell.setCellStyle(headerStyleGray);
                        i++;
                    }
                }
                else if (!specificReport.getSendSelectTestCaseId().isEmpty()) {
                    stringBuilder.append("<h2>Selected completed test cases</h2>\n");
                    header = sheet.createRow(iRow++);
                    headerCell = header.createCell(0);
                    headerCell.setCellValue("Selected completed test cases");
                    headerCell.setCellStyle(headerStyleDarkGray);
                    for(Project project: projectList) {
                        for (TestCase testCase : testCaseList) {
                            if(testCase.getProjectId() == project.getId()) {
                                stringBuilder.append(testCase.getTitle() + BR);
                                stringBuilder.append(testCase.getTitle() + BR);
                                header = sheet.createRow(iRow++);
                                headerCell = header.createCell(1);
                                headerCell.setCellValue(testCase.getTitle());
                                headerCell.setCellStyle(headerStyleGray);
                                i++;
                            }
                        }
                    }
                }
                strs.add(stringBuilder.toString());
                Collections.reverse(strs);
                strs.add("height: " + (Integer.toString(i * 100)) + "px;");
                Collections.reverse(strs);
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setFrom(Objects.requireNonNull(environment.getProperty(NET_CRACKER_USERNAME)));
                helper.setTo(email);



                String htmlString = htmlMail.getHtmlWithStrings(strs, pathHTML).orElseThrow(FileNotFoundException::new);



                helper.setText("", htmlString);
                helper.addInline("topImageId", new File("src/main/resources/image/mail.png"));

                FileOutputStream outputStream = new FileOutputStream(PATH_TO_XLS);

                workbook.write(outputStream);
                workbook.close();

                File excelFile = new File(PATH_TO_XLS);

                helper.addAttachment("special.xlsx", excelFile);

                emailSender.send(message);

                log.info(LOG_SEND_EMAIL + email);

            } catch (MessagingException e) {
                log.error(String.format(CANNT_SAVE_MESSAG, e.getMessage()));
            } catch (FileNotFoundException e) {
                log.error(String.format(HTML_NOT_FOUND, e.getMessage()));
            } catch (IOException e) {
                log.error(String.format("Couldn't write excel file: %s", e.getMessage()));
            }
        }
    }
}
