package Version1.Jira_API;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseClass extends InputData {
	public static VelocityContext context;
	public static Template templates;
	public static Template templates1;

	public static void init_vm(String filepath) {
		VelocityEngine tems = new VelocityEngine();
		tems.init();
		templates = tems.getTemplate(filepath);
		context = new VelocityContext();

	}

	public static RequestSpecification formparams(RequestSpecification request, String key, String value) {
		return request.formParam(key, value);
	}

	public static String BaseURL(String URL) {
		return RestAssured.baseURI = URL;
	}

	public static String createAuth(String Username, String APIKey) {
		String value = Username + ":" + APIKey;
		return "Basic " + Base64.getEncoder().encodeToString(value.getBytes());
	}

	public static List<String> getlistfromResponse(Response response, String query) {
		return JsonPath.from(response.asString()).getList(query);

	}

	public static String getdatafromresponse(Response response, String query) {
		return JsonPath.from(response.asString()).get(query);
	}

	public static void generateHtmlas(String htmlContent, String outputFile) {

		FileOutputStream fop = null;
		File file;
		try {
			file = new File(outputFile);
			fop = new FileOutputStream(file);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// get the content in bytes
			byte[] contentInBytes = htmlContent.getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void VelocityTemplate(String input, String value, String htmlpath) {

		context.put(input, value);
		StringWriter writer = new StringWriter();
		templates.merge(context, writer);
		String htmlReportes = writer.toString();
		generateHtmlas(htmlReportes, htmlpath);

	}

	public static String endDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat formate = new SimpleDateFormat("YYYY-MM-dd");
		return formate.format(calendar.getTime());
	}

	public static String startDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -5);
		SimpleDateFormat formate = new SimpleDateFormat("YYYY-MM-dd");
		return formate.format(calendar.getTime());
	}

	public static String keyword_URL(String input) {
		if (input.contains(" ")) {
			return input.replaceAll(" ", "%20");
		} else {
			return input;
		}
	}

	public static String projname(String code) {
		switch (code) {
		case "CBQ":
			return "CLOUDBLM";

		case "BGC":
			return "GOOGLE";

		default:
			return code;

		}
	}

	public static void main(String[] args) {
		System.out.println(createAuth(InputData.Username, InputData.APIKey));
	}

}
