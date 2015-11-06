package com.test.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.test.constant.Properties;
import com.test.exception.resolver.StackTrace;

/**
 * FTP客户端
 * @author gaopengbd
 *
 */
public class FtpUtil {
	
	protected static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FtpUtil.class);
	
	/**
	 * 单实例的ftp客户端
	 * @return
	 */
	public static FTPClient getClient(String host, int port, String userName, String password) {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(host, port);
			ftpClient.login(userName, password);
			// 文件类型,默认是ASCII  
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
			ftpClient.setControlEncoding("UTF-8");
			 // 设置被动模式  
			ftpClient.enterLocalPassiveMode(); 
			ftpClient.setConnectTimeout(2000);  
			ftpClient.setBufferSize(1024);  
			ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			// 响应信息  
            int replyCode = ftpClient.getReplyCode();  
            if ((!FTPReply.isPositiveCompletion(replyCode))) {  
                // 关闭Ftp连接  
                closeClient(ftpClient);  
                // 释放空间  
                ftpClient = null;  
                logger.error("登录FTP服务器失败,请检查![Server:" + host + ":" + port 
                		+ "、User:" + userName + "、" + "Password:" + password);  
            } else {  
                return ftpClient;  
            }  
		} catch (SocketException e) {
			logger.equals(StackTrace.getExceptionTrace(e));
		} catch (Exception e) {
			logger.equals(StackTrace.getExceptionTrace(e));
		}
		return ftpClient;
	}
	
	/**
	 * 根据url的配置生成多个ftp客户端
	 * @return
	 */
	public static List<FTPClient> getMultiClient() {
		List<FTPClient> clients = null;
		if(Properties.getFtpClientsUrl() != null) {
			String[] urlArr = Properties.getFtpClientsUrl().split(",");
			if(urlArr != null && urlArr.length >0){
				clients = new ArrayList<FTPClient>();
				for(String url : urlArr){
					try {
						String[] temp = url.split(":");
						clients.add(getClient(temp[0],Integer.parseInt(temp[1]),temp[2],temp[3]));
					}catch(Exception e){
						logger.error(StackTrace.getExceptionTrace(e));
					}
				}
			}
		}
		return clients;
	}
    
    /**
     * 以流的方式上传到制定目录
     * @param input
     * @param remoteFoldPath
     * @param fileName
     * @return
     */
    public static Boolean uploadFile(FTPClient ftpClient, InputStream input, String remoteFoldPath, String fileName) {  
        boolean success = false;  
        try {  
            // 改变当前路径到指定路径  
            if (!changeDirectory(ftpClient,remoteFoldPath)) {  
                logger.error(String.format("remoteFoldPath = %s, not existed!!", remoteFoldPath));  
                return false;  
            } 
            return ftpClient.storeFile(fileName, input);  
        }catch (Exception e){
        	logger.error(StackTrace.getExceptionTrace(e));
        } finally {  
            if (input != null) {  
                try {
					input.close();
					input = null;
				} catch (IOException e) {
					logger.error(StackTrace.getExceptionTrace(e));
				}  
            }  
           try {
        	   if(ftpClient != null) {
        		   ftpClient.logout();
   					ftpClient = null; 
        	   }
			} catch (IOException e) {
				logger.error(StackTrace.getExceptionTrace(e));
			}
        }  
        return success;  
    }
    
    /**
     * 以流的方式上传到制定目录,多ftp客户端上传
     * @param input
     * @param remoteFoldPath
     * @param fileName
     * @return
     */
    public static int uploadFileByMulti(InputStream input, String remoteFoldPath, String fileName) {  
        int res = 0;
        List<FTPClient> ftpClients = getMultiClient();
        if(ftpClients != null && ftpClients.size() > 0){
        	for(FTPClient client : ftpClients){
        		if(uploadFile(client, input, remoteFoldPath, fileName)){
        			res++ ;
        		}
        	}
        }
        
        return res;  
    }  
    
    /** 
     * 改变FTP服务器工作路径  
     *  
     * @param remoteFoldPath 
     */  
    public static Boolean changeDirectory(FTPClient ftpClient, String remoteFoldPath) throws Exception {  
    	
        return ftpClient.changeWorkingDirectory(remoteFoldPath);  
    } 
    
    /** 
     * 检查本地路径是否存在 
     *  
     * @param filePath 
     * @return 
     * @throws Exception 
     */  
    public static boolean checkFileExist(String filePath) throws Exception {  
        boolean flag = false;  
        File file = new File(filePath);  
        if (!file.exists()) {  
            throw new Exception("本地路径不存在,请检查!");  
        } else {  
            flag = true;  
        }  
        return flag;  
    }  
	
	 /** 
     * 关闭FTP连接 
     *  
     * @param ftp 
     * @throws Exception 
     */  
    public static void closeClient(FTPClient ftp) throws Exception {  
        try {  
            if (ftp != null && ftp.isConnected()){
            	ftp.logout();  
            	ftp.disconnect();  
            }
        } catch (Exception e) {  
            throw new Exception("关闭FTP服务出错!");  
        }  finally {
        	ftp = null;
        }
    } 
    
    /** 
     * 关闭多个FTP连接 
     *  
     * @param ftp 
     * @throws Exception 
     */  
    public static void closeClients(List<FTPClient> clients) throws Exception {  
        if(clients != null) {
        	for(FTPClient client : clients){
        		closeClient(client);
        	}
        }
    } 
    
}
