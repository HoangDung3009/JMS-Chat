/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmsmessage;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import jakarta.jms.Topic;
import jakarta.jms.TopicConnection;
import jakarta.jms.TopicConnectionFactory;
import jakarta.jms.TopicPublisher;
import jakarta.jms.TopicSession;
import jakarta.jms.TopicSubscriber;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.naming.InitialContext;

/**
 *
 * @author LENOVO
 */
public class ChatRoom2 extends javax.swing.JFrame implements MessageListener {

    /**
     * Creates new form ChatRoom
     */
    private TopicConnectionFactory topicConnectionFactory;
    private TopicConnection topicConnection;
    private TopicSession pubSession, subSession;
    private TopicPublisher topicPublisher;
    private TopicSubscriber topicSubscriber;

    public ChatRoom2() {
        initComponents();
        this.setTitle("Bot B");
        try {
            InitialContext context = new InitialContext();
            topicConnectionFactory = (TopicConnectionFactory) context.lookup("HoangDung11");
            topicConnection = topicConnectionFactory.createTopicConnection();
            pubSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            subSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = (Topic) context.lookup("HoangDung12");
            topicPublisher = pubSession.createPublisher(topic);
            topicSubscriber = subSession.createSubscriber(topic);
            topicSubscriber.setMessageListener(this);
            set(topicConnectionFactory, topicConnection, pubSession, subSession, topicPublisher, topicSubscriber);
            topicConnection.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        taChat = new javax.swing.JTextArea();
        txtInput = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        taChat.setColumns(20);
        taChat.setRows(5);
        jScrollPane1.setViewportView(taChat);

        txtInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInputActionPerformed(evt);
            }
        });

        btnSend.setText("Send");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtInput, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtInput)
                    .addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInputActionPerformed
        // TODO add your handling code here:
        try {
            String text = txtInput.getText().trim();
            if (!text.equalsIgnoreCase("exit")) {
                DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                String date = dateFormat.format(new Date());
                String message = getTitle() + " [" + date + "]: " + text;
                sendMessage(message);
                txtInput.setText("");
            } else {
                 close();
                 System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_txtInputActionPerformed

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        try {
            String text = txtInput.getText().trim();
            if (!text.equalsIgnoreCase("exit")) {
                DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                String date = dateFormat.format(new Date());
                String message = getTitle() + " [" + date + "]: " + text;
                sendMessage(message);
                txtInput.setText("");
            } else {
                 close();
                 System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnSendActionPerformed

    /**
     * @param args the command line arguments
     */
    public void set(TopicConnectionFactory topicConnectionFactory, TopicConnection topicConnection, TopicSession pubSession, TopicSession subSession, TopicPublisher topicPublisher, TopicSubscriber topicSubscriber) {

        this.topicConnectionFactory = topicConnectionFactory;
        this.topicConnection = topicConnection;
        this.pubSession = pubSession;
        this.subSession = subSession;
        this.topicPublisher = topicPublisher;
        this.topicSubscriber = topicSubscriber;
    }

    private void close() {
        try {
            topicConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String s) {
        try {
            TextMessage txt = pubSession.createTextMessage(s);
            topicPublisher.publish(txt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
        * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatRoom2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatRoom2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatRoom2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatRoom2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatRoom2().setVisible(true);
            }
        });
    }

    @Override
    public void onMessage(Message msg) {
        try {
            TextMessage message = (TextMessage) msg;
            taChat.append(message.getText() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea taChat;
    private javax.swing.JTextField txtInput;
    // End of variables declaration//GEN-END:variables
}
