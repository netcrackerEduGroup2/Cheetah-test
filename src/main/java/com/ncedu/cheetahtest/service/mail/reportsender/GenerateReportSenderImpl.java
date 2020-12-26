package com.ncedu.cheetahtest.service.mail.reportsender;

import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.actscenario.ActScenario;
import com.ncedu.cheetahtest.entity.historyacrion.HistoryAction;
import com.ncedu.cheetahtest.service.mail.HtmlMail;
import com.ncedu.cheetahtest.service.mail.generatefile.ExcelGenerateFileImpl;
import com.ncedu.cheetahtest.service.mail.reportinformation.ReportInformation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.ncedu.cheetahtest.service.mail.MailConsts.*;

@Slf4j
@Component
public class GenerateReportSenderImpl implements ReportSender {

    private final JavaMailSender emailSender;
    private final Environment environment;
    private final HtmlMail htmlMail;
    private ExcelGenerateFileImpl excelGenerateFile;
    private ReportInformation reportInformation;
    @Value("${frontend.ulr}")
    private String FRONT_URL;

    @Autowired
    public GenerateReportSenderImpl(JavaMailSender emailSender, HtmlMail htmlMail, Environment environment,
                                    ExcelGenerateFileImpl excelGenerateFile, ReportInformation reportInformation){
        this.emailSender = emailSender;
        this.htmlMail = htmlMail;
        this.environment = environment;
        this.excelGenerateFile = excelGenerateFile;
        this.reportInformation = reportInformation;
    }

    private void fillRowInTable(List<Action> actionList){
        excelGenerateFile.createCell(0, actionList.get(actionList.size() - 1).getTitle());
        excelGenerateFile.createCell(1, actionList.get(actionList.size() - 1).getType());
        excelGenerateFile.createCell(2, actionList.get(actionList.size() - 1).getDescription());
    }

    private String createStringToHTML(List<Action> actionList, String result){
        return "> " + actionList.get(actionList.size() - 1).getTitle()
                + HTML_TAB + actionList.get(actionList.size() - 1).getType()
                + HTML_TAB + actionList.get(actionList.size() - 1).getDescription()
                + HTML_TAB + result + BR;
    }

    private String createTitleEmail(String username, String project, boolean isRepeatable){
        return (isRepeatable ? "Recurrent test case-- " :
                "One-time test case ->") +
                " [" + username + "] " +
                " Project -> [" + project+ "]  ";
    }

    private List<String> createTitleHTML(String email, int idTestCase, int idProject,
                                         boolean isRepeatable){
        List<String> strs = new ArrayList<>();
        strs.add((isRepeatable ? "Repeatable " : "One-time ") + "test case");
        strs.add(reportInformation.getUsernameByEmail(email));
        strs.add(reportInformation.getTitleByProjectIdAndTestCaseId(idProject, idTestCase));
        strs.add(reportInformation.getProjectTitleById(idProject));
        return strs;
    }

    private String createStringHTML(List<Action> actionList, int rowCountExcel,
                                    List<HistoryAction> historyActions, int idxHistoryActions){
        excelGenerateFile.createRow(rowCountExcel);
        fillRowInTable(actionList);
        String result = (historyActions.size() > idxHistoryActions) ?
                historyActions.get(idxHistoryActions).getResult(): "";
        excelGenerateFile.createCell(3, result);
        return createStringToHTML(actionList, result);
    }

    private List<String> reverseListAndAddHeigthCSS(List<String> strs, int rowCountExcel){
        Collections.reverse(strs);
        strs.add("height: " + (Integer.toString(rowCountExcel * 120)) + "px;");
        Collections.reverse(strs);
        return strs;
    }

    private void sendEmailAndAddXLS(MimeMessage message, MimeMessageHelper helper)
            throws IOException, MessagingException {
        excelGenerateFile.createFile(PATH_TO_XLS);
        helper.addAttachment("one-test-case.xlsx", new File(PATH_TO_XLS));
        emailSender.send(message);
    }

    private MimeMessageHelper setMimeMessageHelperEmail(MimeMessageHelper helper, String email)
            throws MessagingException {
        helper.setFrom(Objects.requireNonNull(environment.getProperty(NET_CRACKER_USERNAME)));
        helper.setTo(email);
        return helper;
    }

    @Override
    public void sendReport(String email, int idTestCase, int idProject, int idHTC, String pathHTML) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper = setMimeMessageHelperEmail(helper, email);
            boolean isRepeatable = reportInformation.isRepeatableByIdTestCase(idTestCase);
            List<String> strs = createTitleHTML(email,idTestCase,idProject,isRepeatable);
            message.setSubject(createTitleEmail(strs.get(1), strs.get(2), isRepeatable));
            List<ActScenario> actScenarioList = reportInformation.getActScenariosByTestCaseId(idTestCase);
            excelGenerateFile.createSheetAndSetRowName(strs.get(1));
            List<HistoryAction> historyActions = reportInformation.getHistoryActionByTestHistoryId(idHTC);
            List<Action> actionList = new ArrayList<>();
            excelGenerateFile.setFillCellColor(IndexedColors.GREY_25_PERCENT.getIndex());
            int rowCountExcel = 0;
            StringBuilder stringBuilder = new StringBuilder();
            for (ActScenario actScenario : actScenarioList) {
                actionList.add(reportInformation.getActionById(actScenario.getActionId()));
                stringBuilder.append(createStringHTML(actionList,++rowCountExcel,historyActions, rowCountExcel));
            }
            if(isRepeatable) {
               stringBuilder.append(excelGenerateFile.createRepeatable(rowCountExcel,
                        reportInformation.getExecutionDateByIdTestCase(idTestCase)));
            }
            strs.add(stringBuilder.toString());
            strs.add(FRONT_URL);
            helper.setText("", htmlMail.getHtmlWithStrings(reverseListAndAddHeigthCSS(strs, rowCountExcel), pathHTML)
                    .orElseThrow(FileNotFoundException::new));
            sendEmailAndAddXLS(message, helper);
            log.info(LOG_SEND_EMAIL + email);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            log.error(String.format(HTML_NOT_FOUND, e.getMessage()));
        } catch (IOException e) {
            log.error(String.format(CANNT_SAVE_MESSAG, e.getMessage()));
        }
    }
}
