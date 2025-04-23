package pennywise.TLMHDBWP;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PennyWise {
    public static void main(String[] args) {
        PennyWiseForm obj = new PennyWiseForm();
        obj.setVisible(true);

try {
    FileReader reader = new FileReader("PennyWise.txt");
    BufferedReader br = new BufferedReader(reader);
    
    obj.textArea.setText(""); // Clear previous content

    String line;
    while ((line = br.readLine()) != null) {
        obj.textArea.append(line + "\n");
    }
    br.close();
} catch (FileNotFoundException ex) {
    Logger.getLogger(PennyWise.class.getName()).log(Level.SEVERE, null, ex);
} catch (IOException ex) {
    Logger.getLogger(PennyWise.class.getName()).log(Level.SEVERE, null, ex);
}
    PennyWiseForm.textArea.setEditable(false);


    }
}
