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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EncryptFile extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldFileSource;
	private JPasswordField passwordField1;
	private JPasswordField passwordField2;
	private File fileSource;
	private File encryptFile;
	private String fillPlan;
	private char[] password1;
	private char[] password2;
	

	
	
	public EncryptFile(String algorithmName,String BitString,String FillString,String RunMode) {
		setTitle("\u52A0\u5BC6\u6587\u4EF6");
		fillPlan=FillString;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 552, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u9009\u62E9\u8981\u52A0\u5BC6\u7684\u6587\u4EF6\uFF1A");
		label.setBounds(10, 46, 157, 15);
		contentPane.add(label);
		
		textFieldFileSource = new JTextField();
		textFieldFileSource.setEditable(false);
		textFieldFileSource.setBounds(177, 43, 168, 21);
		contentPane.add(textFieldFileSource);
		textFieldFileSource.setColumns(10);
		
		JButton btnSelectFile = new JButton("\u6D4F\u89C8");
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser("D:");
				if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					fileSource=fileChooser.getSelectedFile();
					textFieldFileSource.setText(fileSource.getPath());
			}
			}
		});
		btnSelectFile.setBounds(420, 42, 93, 23);
		contentPane.add(btnSelectFile);
		
		JLabel label_1 = new JLabel("\u8F93\u5165\u53E3\u4EE4\uFF1A");
		label_1.setBounds(48, 107, 113, 15);
		contentPane.add(label_1);
		
		passwordField1 = new JPasswordField();
		passwordField1.setBounds(180, 104, 165, 21);
		contentPane.add(passwordField1);
		
		JLabel label_2 = new JLabel("\u518D\u6B21\u8F93\u5165\u53E3\u4EE4\uFF1A");
		label_2.setBounds(22, 155, 145, 15);
		contentPane.add(label_2);
		
		passwordField2 = new JPasswordField();
		passwordField2.setBounds(180, 152, 165, 21);
		contentPane.add(passwordField2);
		
		JLabel label_SaveEncFile = new JLabel("\u9009\u62E9\u5BC6\u6587\u6587\u4EF6\u4FDD\u5B58\u4F4D\u7F6E");
		
		
		label_SaveEncFile.setForeground(new Color(0, 0, 0));
		label_SaveEncFile.setBounds(187, 221, 176, 15);
		contentPane.add(label_SaveEncFile);
		
		JButton btnEncryptOperation = new JButton("\u6267\u884C\u52A0\u5BC6");
		btnEncryptOperation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				password1= passwordField1.getPassword();
				password2 = passwordField2.getPassword();
				if (encryptFile!=null&&CheckEncryptCondition()) {
					
					MyFileEncryptor.encryptFile( fileSource, encryptFile,new String(password1), BitString,algorithmName, RunMode,FillString );
					JOptionPane.showMessageDialog(null, "���ܳɹ�");
					
				}else {
					JOptionPane.showMessageDialog(null, "���½������ļ�������");
					JFileChooser dlg = new JFileChooser("D:");
					FileNameExtensionFilter filter = new FileNameExtensionFilter(  
				            "�����ļ�(*.enc)", "enc");
					dlg.setFileFilter(filter);
					dlg.setDialogTitle("�����ļ�");
					int result = dlg.showSaveDialog(null); // ��"���ļ�"�Ի���
					if (result == JFileChooser.APPROVE_OPTION) {
						//�����ʱ������ѡ���ļ���֮���ڽ��������ļ�ʱ��ֱ��Ĩ��ѡ����ļ������ݣ�һ��ѡ���Զ����ļ����֣�ϵͳ���Զ���������ļ�
					encryptFile = dlg.getSelectedFile();
				}
			}
			}
		});
		btnEncryptOperation.setBounds(28, 188, 93, 60);
		contentPane.add(btnEncryptOperation);
		
		JButton buttonHere = new JButton("\u8FD9\u91CC");
		buttonHere.setForeground(new Color(255, 0, 0));
		buttonHere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (CheckEncryptCondition()) {
				   encryptFile=null;
					String fileName;
					JFileChooser dlg = new JFileChooser("D:");
					FileNameExtensionFilter filter = new FileNameExtensionFilter(  
				            "�����ļ�(*.enc)", "enc");
					dlg.setFileFilter(filter);
					dlg.setDialogTitle("�����ļ�");
					int result = dlg.showSaveDialog(null); // ��"���ļ�"�Ի���
					if (result == JFileChooser.APPROVE_OPTION) {
						//�����ʱ������ѡ���ļ���֮���ڽ��������ļ�ʱ��ֱ��Ĩ��ѡ����ļ������ݣ�һ��ѡ���Զ����ļ����֣�ϵͳ���Զ���������ļ�
					encryptFile = dlg.getSelectedFile();
					String fname = dlg.getName(encryptFile);   //���ļ���������л�ȡ�ļ���  
					
			        //�����û���д���ļ������������ƶ��ĺ�׺������ô���Ǹ������Ϻ�׺  
			        if(fname.indexOf(".enc")==-1){  
			            encryptFile=new File(dlg.getCurrentDirectory(),fname+".enc");  
			        }
					
					}
				}
					
					}
				
			});
		buttonHere.setBounds(367, 217, 73, 23);
		contentPane.add(buttonHere);
	}
public boolean CheckEncryptCondition() {
	boolean []ifApproveCondition={false,false,false,false};
	
	
	if (textFieldFileSource.getText().toString().equals("")) {
		JOptionPane.showMessageDialog(null, "��ѡ��������ļ�");
	}
	else {
		ifApproveCondition[1]=true;
		if (fileSource.length()%8!=0&&fillPlan.equals("NoPadding")) {
			
			JOptionPane.showMessageDialog(null, "���ļ����ֽ�������8��������������ʹ��NoPadding��䷽�����뷵���л�");
		}
		else {
			ifApproveCondition[0]=true;
		}
	}
	if (passwordField1.getPassword().length<8) {
		JOptionPane.showMessageDialog(null, "�������볤�Ȳ���С��8λ");
	}
	else{
		ifApproveCondition[2]=true;
	}
	if (Arrays.equals(password1, password2)) {
		ifApproveCondition[3]=true;
	}else{
		JOptionPane.showMessageDialog(null, "�����������벻һ��");
	}
	if (ifApproveCondition[0]==true&&ifApproveCondition[1]==true&&ifApproveCondition[2]==true&&ifApproveCondition[3]==true) {
		return true;
	}
	return false;
}
}
