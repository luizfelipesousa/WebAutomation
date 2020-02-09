//TODO: Criar um método que se conecte ao Azure e obtenha as mensagens numa queue
//package automation.core.api;
//
//import java.io.IOException;
//import java.net.URL;
//import java.time.Instant;
//import java.time.OffsetDateTime;
//import java.util.Base64;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
//import java.util.Properties;
//
//import com.microsoft.azure.storage.queue.MessagesURL;
//import com.microsoft.azure.storage.queue.QueueURL;
//import com.microsoft.azure.storage.queue.ServiceURL;
//import com.microsoft.azure.storage.queue.SharedKeyCredentials;
//import com.microsoft.azure.storage.queue.StorageURL;
//import com.microsoft.azure.storage.queue.models.QueueCreateResponse;
//import com.microsoft.rest.v2.http.HttpPipeline;
//
//import io.reactivex.Single;
//
//public class StorageQueue {
//
//	static String token = null;
//
//	@SuppressWarnings("deprecation")
//	public static String getToken(int times) throws InterruptedException, IOException {
//
//		Properties p = Setup.getProp();
//
//		String accountName = p.getProperty("accountName");
//		String accountKey = p.getProperty("accountKey");
//		SharedKeyCredentials credential = new SharedKeyCredentials(accountName, accountKey);
//
//		HttpPipeline pipeline = StorageURL.createPipeline(credential);
//
//		URL u = new URL(String.format(Locale.ROOT, "https://%s.queue.core.windows.net", accountName));
//
//		QueueURL qu = new ServiceURL(u, pipeline).createQueueUrl("xpi-us-bambu-2fa");
//
//		Single<QueueCreateResponse> queueCreateSingle = qu.create();
//
//		Date now = Calendar.getInstance().getTime();
//
//		MessagesURL mu = qu.createMessagesUrl();
//
//		Instant timeoutStart = Instant.now().plusSeconds(60 * times);
//
//		while (token == null) {
//			if (Instant.now().isBefore(timeoutStart)) {
//				queueCreateSingle.flatMap(response -> mu.dequeue(1, 60)).doOnSuccess(messageDequeueResponse -> {
//
//					if (messageDequeueResponse.body().size() > 0) {
//						String string = messageDequeueResponse.body().get(0).messageText();
//						byte[] byteArray = Base64.getDecoder().decode(string);
//						String message = new String(byteArray);
//
//						OffsetDateTime it = messageDequeueResponse.body().get(0).insertionTime();
//						if (it.getHour() >= now.getHours()) {
//							if (it.getMinute() >= now.getMinutes()) {
//								string = messageDequeueResponse.body().get(0).messageText();
//								byteArray = Base64.getDecoder().decode(string);
//								message = new String(byteArray);
//								token = message;
//							}
//						}
//					}
//				}).blockingGet();
//
//			} else {
//				System.out.println("Token Was not founded!");
//				break;
//			}
//		}
//
//		queueCreateSingle.flatMap(response -> mu.clear()).doOnSuccess(onSuccess -> {
//			System.out.println("Queue cleaned!");
//		}).blockingGet();
//
//		return token;
//	}
//}
