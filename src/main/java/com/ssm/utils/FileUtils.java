package com.ssm.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**
 * 阿里云oss上传文件工具类
 * @author Administrator
 *
 */
public class FileUtils {
	
	public static final String bucketName = "halfmi";
	
	public static final String accessKeyId = "bh6RFYbpXlVzXmOk";
     
	public static final String accessKeySecret = "IcUHxlVWbtOLzhWTgJHeiA6OqCBCNH";
    // 以杭州为例
    public static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    
    public static OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);

    public OSSClient getOssClient(){
    	if(client == null)
    		client = new OSSClient(endpoint,accessKeyId, accessKeySecret);
    	return client;
    }
	
    /**
     * 上传object,最大不能超过5GB
     * @param bucketName
     * @param key
     * @param filePath
     * @throws FileNotFoundException
     */
	public static void uploadFile(String bucketName, String key, String filePath) throws FileNotFoundException {

	    // 获取指定文件的输入流
	    File file = new File(filePath);
	    InputStream content = new FileInputStream(file);

	    // 创建上传Object的Metadata
	    ObjectMetadata meta = new ObjectMetadata();

	    // 必须设置ContentLength
	    meta.setContentLength(file.length());

	    // 上传Object.
	    PutObjectResult result = client.putObject(bucketName, key, content, meta);

	    // 打印ETag
	    System.out.println(result.getETag());
	}
	
    /**
     * 上传object,最大不能超过5GB
     * @param bucketName
     * @param key
     * @param filePath
     * @throws IOException 
     * @throws ClientException 
     * @throws OSSException 
     */
	public static void uploadFile(String bucketName, String key, MultipartFile file) throws OSSException, ClientException, IOException {


	    // 创建上传Object的Metadata
	    ObjectMetadata meta = new ObjectMetadata();

	    // 必须设置ContentLength
	    meta.setContentLength(file.getSize());

	    // 上传Object.
	    try{
	    	PutObjectResult result = client.putObject(bucketName, key, file.getInputStream(), meta);
		    // 打印ETag
		    System.out.println(result.getETag());
	    }catch(Exception e){
	    	e.printStackTrace();
	    }

	}

    /**
     * 上传object,最大不能超过5GB
     * @param bucketName
     * @param key
     * @param filePath
     * @throws IOException 
     * @throws ClientException 
     * @throws OSSException 
     */
	public static void uploadFile(String bucketName, String key, byte[] bin) throws OSSException, ClientException, IOException {


	    // 创建上传Object的Metadata
	    ObjectMetadata meta = new ObjectMetadata();

	    // 必须设置ContentLength
	    meta.setContentLength(bin.length);
	    
	    //byte[]转inputStream
	    InputStream in = new ByteArrayInputStream(bin); 

	    // 上传Object.
	    PutObjectResult result = client.putObject(bucketName, key, in, meta);

	    // 打印ETag
	    System.out.println(result.getETag());
	}

	
	
	/**
	 * 创建文件夹
	 * @param bucketName
	 * @param key
	 * @param filePath
	 * @throws IOException 
	 */
	public void createFolder(String bucketName, String key) throws IOException {
		//要创建的文件夹名称,在满足Object命名规则的情况下以"/"结尾
		String objectName = key+"/"; 
		OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);
		ObjectMetadata objectMeta = new ObjectMetadata();
		/*这里的size为0,注意OSS本身没有文件夹的概念,这里创建的文件夹本质上是一个size为0的Object,dataStream仍然可以有数据
		 */
		byte[] buffer = new byte[0];
		ByteArrayInputStream in = new ByteArrayInputStream(buffer);  
		objectMeta.setContentLength(0);
		try {
		    client.putObject(bucketName, objectName, in, objectMeta);
		} finally {
		    in.close();
		}
	}
	
	/**
	 * 列出所有object
	 * @param bucketName
	 */
	public void listObjects(String bucketName) {

	    // 初始化OSSClient
	    OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);

	    // 获取指定bucket下的所有Object信息
	    ObjectListing listing = client.listObjects(bucketName);

	    // 遍历所有Object
	    for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
	        System.out.println(objectSummary.getKey());
	    }
	}
	
	/**
	 * 获取指定的object
	 * @param bucketName
	 * @param key
	 * @throws IOException
	 */
	public void getObject(String bucketName, String key) throws IOException {

	    // 初始化OSSClient
	    OSSClient client = new OSSClient(endpoint,accessKeyId, accessKeySecret);
	    // 获取Object，返回结果为OSSObject对象
	    OSSObject object = client.getObject(bucketName, key);
	    // 获取Object的输入流
	    InputStream objectContent = object.getObjectContent();
	    // 处理Object
	    // 关闭流
	    objectContent.close();
	}
	
	public static void main(String[] args) throws IOException{
		
		String filePath = "main.png";
		
		String key = "folder/main.png";
		
		try {
			FileUtils.uploadFile(bucketName, key, filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
