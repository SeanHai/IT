package enterpriseManager;

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
 * Servlet implementation class CompanyView
 */
@WebServlet("/CompanyView")
public class CompanyView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String e_Name=request.getParameter("e_Name");
			CompanyViewDB companyView=new CompanyViewDB();
			companyView.Find(e_Name);
			String result="";
			if(companyView.e_Name.equals("")&&companyView.property.equals("")&&companyView.scope.equals("")&&companyView.address.equals("")
					&&companyView.companyInfo.equals("")&&companyView.headPicture.equals(""))
			{
				result="null";
				response.setContentType("text/html; charset=GBK");
		        PrintWriter out = response.getWriter();
		        out.println(result);
			}
			else
			{
				JSONObject view=new JSONObject();
				try {
					view.put("e_Name", companyView.e_Name);
					view.put("property", companyView.property);
					view.put("scope", companyView.scope);
					view.put("address", companyView.address);
					view.put("companyInfo", companyView.companyInfo);
					view.put("headPicture",  companyView.headPicture);
					result=view.toString();
					response.getOutputStream().write(result.getBytes("UTF-8"));
					response.setContentType("text/json; charset=UTF-8");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				
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
