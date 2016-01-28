/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wagwaan.stocks;

import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Helmut
 */
public class Tester {
    public static void main(String[] args) {
        JXTable table=new JXTable(){

            @Override
            public boolean isCellEditable(int row, int column) {
                return super.isCellEditable(row, column); //To change body of generated methods, choose Tools | Templates.
            }
            
        };
    }
}
