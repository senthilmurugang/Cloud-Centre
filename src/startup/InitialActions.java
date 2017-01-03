package startup;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Index;
import com.google.appengine.api.datastore.Index.IndexState;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

@Controller
@RequestMapping("/startup")
public class InitialActions {
	public static int count = 0;
	public static Map<Index, IndexState> list = null;
	
	@RequestMapping("/getUserDetails.do")
	@ResponseBody
	public static String getUserDetails(@RequestParam("userId") String userId){
		System.out.println("UserId is : "+userId);
		return "Testing";
	}
	@RequestMapping("/signup.do")
	public static ModelAndView signUp(){
		ModelAndView signup = new ModelAndView("signup");
		return signup;
	}
	@RequestMapping("/pushqueue.do")
	@ResponseBody
	public static String pushingTaskToQueue(@RequestParam("times") int times){
		Queue queue = QueueFactory.getDefaultQueue();
		int i = 0;
		while(i < times){
			queue.add(TaskOptions.Builder.withUrl("/startup/pushingtodatastore.do").param("count", ""+count++));
			i++;
		}
		return "Pushed Task To queue";
	}
	
	@RequestMapping("/pushingtodatastore.do")
	@ResponseBody
	public static String pushingToDatastore(@RequestParam("count") String counting){
		Entity entity = new Entity("Counter");
		entity.setProperty("count", counting);
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		try{
			ds.put(entity);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("Counting : "+counting);
		return "success";
	}
	
	@RequestMapping("/deletefromdatastore.do")
	@ResponseBody
	public static String deleteFromDatastore(@RequestBody int counter){
		System.out.println("Error here");
//		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
//		try{
//			list = ds.getIndexes();
//			System.out.println(list.containsValue(counter));
//			Key key = KeyFactory.createKey("Counter", );
//			ds.delete(key);
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
		return "Deleting";
	}
}
