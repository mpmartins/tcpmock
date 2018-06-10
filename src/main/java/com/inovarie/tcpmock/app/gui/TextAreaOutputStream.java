package com.inovarie.tcpmock.app.gui;

import javax.swing.JTextArea;
import java.io.OutputStream;

public class TextAreaOutputStream extends OutputStream {
    private JTextArea textArea;
     
    public TextAreaOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }
     
    @Override
    public void write(int b) {
        // redirects data to the text area
        textArea.append(String.valueOf((char)b));
        if (b == '\n') {
	        // scrolls the text area to the end of data
	        textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }
}