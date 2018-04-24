import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class DecryptFile extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldDecryptFile;
	private JPasswordField passwordDecrypt;
	private File decryptFile;
	private File plainTextFile;
	private char []dePassword={};

	

	
	public DecryptFile() {
		setTitle("\u89E3\u5BC6\u6587\u4EF6");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u5F85\u89E3\u5BC6\u7684\u6587\u4EF6");
		label.setBounds(25, 51, 101, 15);
		contentPane.add(label);
		
		textFieldDecryptFile = new JTextField();
		textFieldDecryptFile.setEditable(false);
		textFieldDecryptFile.setBounds(121, 48, 180, 21);
		contentPane.add(textFieldDecryptFile);
		textFieldDecryptFile.setColumns(10);
		
		JButton btnSelectDecryptFile = new JButton("\u6D4F\u89C8");
		btnSelectDecryptFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser dlg = new JFileChooser("D:");
				FileNameExtensionFilter filter = new FileNameExtensionFilter(  
			            "�����ļ�(*.enc)", "enc");
				dlg.setFileFilter(filter);
				int result = dlg.showOpenDialog(null); // ��"���ļ�"�Ի���
				if (result == JFileChooser.APPROVE_OPTION) {
					//�����ʱ������ѡ���ļ���֮���ڽ��������ļ�ʱ��ֱ��Ĩ��ѡ����ļ������ݣ�һ��ѡ���Զ����ļ����֣�ϵͳ���Զ���������ļ�
				decryptFile = dlg.getSelectedFile();
				textFieldDecryptFile.setText(decryptFile.getPath());
			}
			}
		});
		btnSelectDecryptFile.setBounds(309, 47, 93, 23);
		contentPane.add(btnSelectDecryptFile);
		
		JLabel label_1 = new JLabel("\u89E3\u5BC6\u53E3\u4EE4");
		label_1.setBounds(27, 105, 54, 15);
		contentPane.add(label_1);
		
		passwordDecrypt = new JPasswordField();
		passwordDecrypt.setBounds(121, 102, 186, 21);
		contentPane.add(passwordDecrypt);
		
		JButton btnDecrypt = new JButton("\u6267\u884C\u89E3\u5BC6");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dePassword=passwordDecrypt.getPassword();
			    if (decryptFile==null) {
			    	JOptionPane.showMessageDialog(null, "��ѡ�������ļ�");
					JFileChooser dlg = new JFileChooser("D:");
					FileNameExtensionFilter filter = new FileNameExtensionFilter(  
				            "�����ļ�(*.enc)", "enc");
					dlg.setFileFilter(filter);
					dlg.setDialogTitle("���ļ�");
					int result = dlg.showOpenDialog(null); // ��"���ļ�"�Ի���
					if (result == JFileChooser.APPROVE_OPTION) {
						//�����ʱ������ѡ���ļ���֮���ڽ��������ļ�ʱ��ֱ��Ĩ��ѡ����ļ������ݣ�һ��ѡ���Զ����ļ����֣�ϵͳ���Զ���������ļ�
					decryptFile = dlg.getSelectedFile();
					textFieldDecryptFile.setText(decryptFile.getPath());
				}
			    }
			    else if (dePassword.length==0) {
			    	JOptionPane.showMessageDialog(null, "��������ܿ���");
				}
			    else if (plainTextFile==null) {
			    	JOptionPane.showMessageDialog(null, "��ѡ�������ļ�");
			    	JFileChooser dlg = new JFileChooser("D:");
					int result = dlg.showSaveDialog(null); // ��"���ļ�"�Ի���
					dlg.setDialogTitle("�����ļ�");
					if (result == JFileChooser.APPROVE_OPTION) {
						//�����ʱ������ѡ���ļ���֮���ڽ��������ļ�ʱ��ֱ��Ĩ��ѡ����ļ������ݣ�һ��ѡ���Զ����ļ����֣�ϵͳ���Զ���������ļ�
					plainTextFile = dlg.getSelectedFile();
					}
					}
			    else {
					
					MyFileEncryptor.decryptFile(decryptFile, plainTextFile, new String(dePassword));
					JOptionPane.showMessageDialog(null, "���ܳɹ�");
				}
			}
		});
		btnDecrypt.setBounds(25, 164, 93, 60);
		contentPane.add(btnDecrypt);
		
		JButton btnSavePlainTextFile = new JButton("\u660E\u6587\u6587\u4EF6\u53E6\u5B58\u4E3A");
		btnSavePlainTextFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser dlg = new JFileChooser("D:");
				int result = dlg.showSaveDialog(null); // ��"���ļ�"�Ի���
				dlg.setDialogTitle("�����ļ�");
				if (result == JFileChooser.APPROVE_OPTION) {
					//�����ʱ������ѡ���ļ���֮���ڽ��������ļ�ʱ��ֱ��Ĩ��ѡ����ļ������ݣ�һ��ѡ���Զ����ļ����֣�ϵͳ���Զ���������ļ�
				plainTextFile = dlg.getSelectedFile();
			}
			}
		});
		btnSavePlainTextFile.setBounds(156, 183, 126, 23);
		contentPane.add(btnSavePlainTextFile);
	}
}
