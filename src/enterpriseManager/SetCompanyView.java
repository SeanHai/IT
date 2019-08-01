package enterpriseManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class SetCompanyView
 */
@WebServlet("/SetCompanyView")
public class SetCompanyView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetCompanyView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuffer buffer=new StringBuffer();
		String line="";
		String result="";
		request.setCharacterEncoding("UTF-8");
		BufferedReader reader=request.getReader();
		while((line=reader.readLine())!=null)
		{
			buffer.append(line);
		}
		try {
			JSONObject view=new JSONObject(buffer.toString());
			CompanyViewDB demo=new CompanyViewDB();
			result=demo.Creat(view);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setContentType("text/html; charset=GBK");
        PrintWriter out = response.getWriter();
        out.println(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
