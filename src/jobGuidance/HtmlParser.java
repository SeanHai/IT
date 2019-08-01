package jobGuidance;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class HtmlParser
 */
@WebServlet("/HtmlParser")
public class HtmlParser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HtmlParser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuffer buffer=new StringBuffer();
		String line="";
		request.setCharacterEncoding("UTF-8");
		BufferedReader reader=request.getReader();
		while((line=reader.readLine())!=null)
		{
			buffer.append(line);
		}
		JSONObject object;
		try {
			object = new JSONObject(buffer.toString());
			HtmlParserDB demo=new HtmlParserDB(object.getString("type"));
			response.getOutputStream().write(demo.getContentDir().toString().getBytes("UTF-8"));
			response.setContentType("text/json; charset=UTF-8");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
