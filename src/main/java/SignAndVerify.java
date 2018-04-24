import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class SignAndVerify {
	public static void signFile(File fileToSign, PrivateKey key, File signValueFile,String algorithmName1,String algorithmName) throws Exception {
		// try-with-resource��䴴����������Ҫ�ֶ��ر�
		try (FileInputStream fis = new FileInputStream(fileToSign);
				FileOutputStream fos = new FileOutputStream(signValueFile)) {
			//��������ǩ������
			Signature signature = Signature.getInstance(algorithmName1);
			//��˽Կ��ʼ������ǩ������������ǩ�����ɹ���
			signature.initSign(key);
			String runData=SignRunData.RunModeData1(algorithmName, algorithmName1);
			fos.write(runData.getBytes());
			// ���ļ����ݼ��ص�����ǩ��������
			byte[] buffer = new byte[1024];
			byte[] signaturValue;
			int n = 0;
			while ((n = fis.read(buffer)) != -1) {
				signature.update(buffer, 0, n);
			}
			signaturValue = signature.sign();
			fos.write(signaturValue);
		}
	}

	
	public static boolean verifyFile(File fileToVerify, PublicKey key, File signValueFile) throws Exception {
		// try-with-resource��䴴����������Ҫ�ֶ��ر�
		try (FileInputStream fisFileToVerify = new FileInputStream(fileToVerify);
				FileInputStream fisSignValueFile = new FileInputStream(signValueFile)) 
		{
			// ��������ǩ������
			byte []al=new byte[2];
			fisSignValueFile.read(al);
			
			SignRunData.RunModeData0(new String(al));
			Signature signature = Signature.getInstance(SignRunData.algorithmName);
			// �ù�Կ��ʼ������+ǩ������������ǩ����֤����
			signature.initVerify(key);
			// ���ļ����ݼ��ص�����ǩ��������
			byte[] buffer = new byte[1024];
			int n = 0;
			while ((n = fisFileToVerify.read(buffer)) != -1) {
				signature.update(buffer, 0, n);
			}
			// ��ȡ����ǩ��ֵ
			byte[] signatureValue = new byte[fisSignValueFile.available()];
			fisSignValueFile.read(signatureValue);
			// ��֤����ǩ����������֤���
			return signature.verify(signatureValue);
		}
	}


}
