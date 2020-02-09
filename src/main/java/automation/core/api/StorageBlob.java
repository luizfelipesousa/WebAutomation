package automation.core.api;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import org.apache.log4j.Logger;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import automation.core.utils.Utils;
import automation.logging.log4j.Log4JSetup;

public class StorageBlob {

	private static Logger log = Log4JSetup.getLogger(StorageBlob.class);

	/**
	 * Responsável por Enviar um arquivo como evidencia para um storage no cloud da
	 * Azure.
	 * 
	 * @param file
	 */
	public static void uploadEvidenceToAzureStorageBlob(String file) {

		File sourceFile = new File(file);

		String accountName = Utils.getProp("cloud.azure.accountName");
		String accountKey = Utils.getProp("cloud.azure.accountKey");
		String cloudContainerReference = Utils.getProp("cloud.azure.blob.container.reference");

		String storageConnectionString = String.format(
				"DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net",
				accountName, accountKey);

		try {
			// Parse the connection string and create a blob client to interact with Blob
			// storage
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
			CloudBlobContainer container = blobClient.getContainerReference(cloudContainerReference);

			// Create the container if it does not exist with public access.
			container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(),
					new OperationContext());

			// Getting a blob reference
			CloudBlockBlob blob = container.getBlockBlobReference(sourceFile.getName());

			// Creating blob and uploading file to it
			blob.uploadFromFile(sourceFile.getAbsolutePath());

		} catch (InvalidKeyException | URISyntaxException e) {
			log.error("Erro ao realizar a conexão ao cloud. Verifque as credenciais informadas ou a url de conexão.");
			e.printStackTrace();
		} catch (StorageException e) {
			log.error("Erro ao tentar realizar upload do arquivo para o Storage. Verifique a referencia do Storage.");
			e.printStackTrace();
		} catch (IOException e) {
			log.error("Falha ao gerenciar o arquivo informado. Verifique se o nome do arquivo está correto.");
			e.printStackTrace();
		}

	}
}
