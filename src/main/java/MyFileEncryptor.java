

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import org.junit.validator.PublicClassValidator;
import org.omg.CORBA.PUBLIC_MEMBER;

public class MyFileEncryptor {
	public static void encryptFile(File sourceFile, 
			File encryptedFile, String password,String bitString,String algorithmName,String runMode,String fillPlan) {
		try {
			
			FileInputStream fis = new FileInputStream(sourceFile);
			FileOutputStream fos = new FileOutputStream(encryptedFile);
			int byteLength=Integer.parseInt(bitString)/8;
			//������Կ
			MessageDigest md = MessageDigest.getInstance("SHA3-512");
			byte[] hashValue = md.digest(password.getBytes());
			
			String runModeString=algorithmName+"/"+runMode+"/"+fillPlan;
			
			if (algorithmName.equals("AES")) {
				SecretKeySpec key = new SecretKeySpec(hashValue, 0, byteLength, algorithmName);
				if (!runMode.equals("ECB")) {
					byte[] ivValue = new byte[16];
					Random random = new Random(System.currentTimeMillis());
					random.nextBytes(ivValue);
					IvParameterSpec iv = new IvParameterSpec(ivValue);
					fos.write("MyFileEncryptor1".getBytes());//16���ֽڼ���У��
					fos.write(RunModeData.RunModeData1(bitString, algorithmName, runMode, fillPlan).getBytes());
					fos.write(hashValue); //64���ֽڵ������ϣֵ
					
					fos.write(ivValue);  //16���ֽڵ�������� ��Ϊ�Ρ�AES 16���ֽ� DES 8���ֽ�
					//����������Cipher�������ڼ���
					Cipher cipher = Cipher.getInstance(runModeString);
					cipher.init(Cipher.ENCRYPT_MODE, key, iv);
					//��������������
					CipherInputStream cis = new CipherInputStream(fis, cipher);
					byte[] buffer = new byte[64];
					int n = 0;
					while((n = cis.read(buffer)) != -1) {
						fos.write(buffer, 0, n);
					}
					fos.close();
					cis.close();
				}
				else
				{
					fos.write("MyFileEncryptor1".getBytes());//16���ֽڼ���У��
					fos.write(RunModeData.RunModeData1(bitString, algorithmName, runMode, fillPlan).getBytes());
					fos.write(hashValue); //64���ֽڵ������ϣֵ
					Cipher cipher = Cipher.getInstance(runModeString);
					cipher.init(Cipher.ENCRYPT_MODE, key);
					//��������������
					CipherInputStream cis = new CipherInputStream(fis, cipher);
					byte[] buffer = new byte[64];
					int n = 0;
					while((n = cis.read(buffer)) != -1) {
						fos.write(buffer, 0, n);
					}
					fos.close();
					cis.close();
				}
				}
			else {               //DES���ܴӴ˿�ʼ
				SecretKeySpec key = new SecretKeySpec(hashValue, 0, byteLength, algorithmName);
				if (!runMode.equals("ECB")) {
					byte[] ivValue = new byte[8];
					Random random = new Random(System.currentTimeMillis());
					random.nextBytes(ivValue);
					IvParameterSpec iv = new IvParameterSpec(ivValue);
					fos.write("MyFileEncryptor1".getBytes());//16���ֽڼ���У��
					fos.write(RunModeData.RunModeData1(bitString, algorithmName, runMode, fillPlan).getBytes());
					fos.write(hashValue); //64���ֽڵ������ϣֵ
					fos.write(ivValue);  //16���ֽڵ�������� ��Ϊ�Ρ�AES 16���ֽ� DES 8���ֽ�
					//����������Cipher�������ڼ���
					Cipher cipher = Cipher.getInstance(runModeString);
					cipher.init(Cipher.ENCRYPT_MODE, key, iv);
					//��������������
					CipherInputStream cis = new CipherInputStream(fis, cipher);
					byte[] buffer = new byte[64];
					int n = 0;
					while((n = cis.read(buffer)) != -1) {
						fos.write(buffer, 0, n);
					}
					fos.close();
					cis.close();
				}
				else
				{
					fos.write("MyFileEncryptor1".getBytes());//16���ֽڼ���У��
					fos.write(RunModeData.RunModeData1(bitString, algorithmName, runMode, fillPlan).getBytes());
					fos.write(hashValue); //64���ֽڵ������ϣֵ
					Cipher cipher = Cipher.getInstance(runModeString);
					cipher.init(Cipher.ENCRYPT_MODE, key);
					//��������������
					CipherInputStream cis = new CipherInputStream(fis, cipher);
					byte[] buffer = new byte[64];
					int n = 0;
					while((n = cis.read(buffer)) != -1) {
						fos.write(buffer, 0, n);
					}
					fos.close();
					cis.close();
				}
				
			}
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void decryptFile(File encryptedFile,
			File decryptedFile, String password) {
		try {
			FileInputStream fis = new FileInputStream(encryptedFile);
			FileOutputStream fos = new FileOutputStream(decryptedFile);
			MessageDigest md = MessageDigest.getInstance("SHA3-512");
			byte[] hashValue = md.digest(password.getBytes());
			
			byte[] fileIdentifier = new byte[16];
			fis.read(fileIdentifier);   //���ȶ�ȡ�ļ�ʶ����
			byte[] runModeData=new byte[4];    //��һλ��¼���ܳ��ȣ��ڶ�λ��¼�㷨������λ��¼����ģʽ������λ��¼��䷽��
			fis.read(runModeData);    //��ζ�ȡ�㷨����������
			
			RunModeData.RunModeData0(new String(runModeData));
			String runModeString=RunModeData.algorithmName+"/"+RunModeData.runMode+"/"+RunModeData.fillPlan;
			int decryptLength=Integer.parseInt(RunModeData.bitLength)/8;
			
			byte[] savedHashValue=new byte[64];
			fis.read(savedHashValue);    //��ȡ��ϣֵ
			

			if (fileIdentifier.toString().equals("MyFileEncryptor1")) {
				JOptionPane.showMessageDialog(null, "�ļ������Ҽ��ܵģ�����˭��˭ȥ��");
			}
			else if (!Arrays.equals(hashValue, savedHashValue)) {
				JOptionPane.showMessageDialog(null, "�����������");
			}
			else {
			//	1.�ж��㷨��AES,����DES���Լ����Ƿ����iv��iv�ĳ���
				//2.������Կ����ִ�н���
				SecretKeySpec key = new SecretKeySpec(hashValue, 0, decryptLength, RunModeData.algorithmName);
				Cipher cipher = Cipher.getInstance(runModeString);
				
				if (runModeData[1]=='A') {
					if (runModeData[2]=='A') {
						//AES�µ�ECB����
						cipher.init(Cipher.DECRYPT_MODE, key);
						//���ļ�������fis��װΪ�����ļ�������
						CipherInputStream cis = new CipherInputStream(fis, cipher);
						byte[] buffer = new byte[64];
						int n = 0;
						while((n = cis.read(buffer)) != -1) {
							fos.write(buffer, 0, n);
						}
						fos.close();
						cis.close();
					} else {        //AES�µ���������ģʽ����
						byte[] ivValue = new byte[16];
						fis.read(ivValue);
						IvParameterSpec iv = new IvParameterSpec(ivValue);
						
						//����������Cipher�������ڽ���
						cipher.init(Cipher.DECRYPT_MODE, key, iv);
						//���ļ�������fis��װΪ�����ļ�������
						CipherInputStream cis = new CipherInputStream(fis, cipher);
						byte[] buffer = new byte[64];
						int n = 0;
						while((n = cis.read(buffer)) != -1) {
							fos.write(buffer, 0, n);
						}
						
						fos.close();
						cis.close();

					}
					
				} else {                    //DES����
					
					if (runModeData[2]=='A') {
						//DES�µ�ECB����
						cipher.init(Cipher.DECRYPT_MODE, key);
						//���ļ�������fis��װΪ�����ļ�������
						CipherInputStream cis = new CipherInputStream(fis, cipher);
						byte[] buffer = new byte[64];
						int n = 0;
						while((n = cis.read(buffer)) != -1) {
							fos.write(buffer, 0, n);
						}
						fos.close();
						cis.close();
						
					} else {        //DES�µ���������ģʽ����
						byte[] ivValue = new byte[8];
						fis.read(ivValue);
						IvParameterSpec iv = new IvParameterSpec(ivValue);
						
						//����������Cipher�������ڽ���
						cipher.init(Cipher.DECRYPT_MODE, key, iv);
						//���ļ�������fis��װΪ�����ļ�������
						CipherInputStream cis = new CipherInputStream(fis, cipher);
						byte[] buffer = new byte[64];
						int n = 0;
						while((n = cis.read(buffer)) != -1) {
							fos.write(buffer, 0, n);
						}
						
						fos.close();
						cis.close();

					}

				}
			}
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
}














