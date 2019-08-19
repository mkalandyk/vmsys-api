package com.mikiruki.vendingsystemapi.utils;

import com.mikiruki.vendingsystemapi.models.Mail;
import com.mikiruki.vendingsystemapi.models.VendingMachine;

public class MailTemplates {

    public static Mail machineNeedsAttentionTemplate(VendingMachine machine) {
        Mail mail = new Mail();
        mail.setSubject(String.format("Machine #: %d needs attention.", machine.getMachineId()));
        mail.setMessage(String.format("<html><body><h3>Hi, you have a new message from the VMSystem!</h3>" +
                "<p>Machine #: %d at %s needs attention, as one of the products have run out.</p>" +
                "<br><p>Please do not reply.</p><p>VMSys</p></body></html>", machine.getMachineId(), machine.getAddress()));
        return mail;
    }

}
