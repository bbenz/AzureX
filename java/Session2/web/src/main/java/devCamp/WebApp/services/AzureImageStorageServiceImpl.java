package devCamp.WebApp.services;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import devCamp.WebApp.properties.AzureStorageAccountProperties;


import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.*;
import com.microsoft.azure.storage.queue.CloudQueue;
import com.microsoft.azure.storage.queue.CloudQueueClient;
import com.microsoft.azure.storage.queue.CloudQueueMessage;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

@Primary
@Service
public class AzureImageStorageServiceImpl implements ImageStorageService {
    private static final Logger LOG = LoggerFactory.getLogger(AzureImageStorageServiceImpl.class);

    @Autowired
    private AzureStorageAccountProperties azureStorageProperties;
        
    private byte[] downloadImage(CloudBlockBlob imgBlob){
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	try {
			imgBlob.download(bos);
		} catch (StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return bos.toByteArray();
    }
    
    public byte[] getImageAsArray(String imagefilename) {
    	byte[] b = null;
    	
        CloudBlobClient serviceClient = cloudStorageAccount.createCloudBlobClient();
        // Container name must be lower case.
        CloudBlobContainer container;
		try {
			container = serviceClient.getContainerReference(azureStorageProperties.getBlobContainer());
	        CloudBlockBlob imgBlob = container.getBlockBlobReference(imagefilename);
	        b = downloadImage(imgBlob);
		} catch (URISyntaxException | StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //return result
        return b;    	
    }
        
    @Autowired
    private CloudStorageAccount cloudStorageAccount;

    public String storeImage(String IncidentId, String fileName, String contentType, byte[] fileBuffer) {
        CloudBlobClient serviceClient = cloudStorageAccount.createCloudBlobClient();
        String imageUriString = null;
        // Container name must be lower case.
        CloudBlobContainer container;
		try {
			container = serviceClient.getContainerReference(azureStorageProperties.getBlobContainer());
			container.createIfNotExists();
	        // Set anonymous access on the container.
	        BlobContainerPermissions containerPermissions = new BlobContainerPermissions();
	        containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);
	        container.uploadPermissions(containerPermissions);
	        String incidentBlob = getIncidentBlobFilename(IncidentId,fileName);
	        CloudBlockBlob imgBlob = container.getBlockBlobReference(incidentBlob);
	        imgBlob.getProperties().setContentType(contentType);
	        imgBlob.uploadFromByteArray(fileBuffer, 0, fileBuffer.length);
	        imageUriString = incidentBlob;
		} catch (URISyntaxException | StorageException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //return result
        return imageUriString;
    	
    }    

    private String getIncidentBlobFilename(String IncidentId,String FileName) {
        String fileExt = FilenameUtils.getExtension(FileName);
        return String.format("%s.%s", IncidentId,fileExt);
    }
    
}

