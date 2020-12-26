package com.ncedu.cheetahtest.service.mail.reportsender;


public interface ReportSender {

    void sendReport(String email, int idTestCase,
                    int idProject, int idHTC, String pathHTML);
}
