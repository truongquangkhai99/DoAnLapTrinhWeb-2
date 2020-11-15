package ute.webapplication.controller.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ute.webapplication.DAO.web.UserDAO;
import ute.webapplication.model.web.AccountModel;
import ute.webapplication.services.web.*;
import ute.webapplication.utils.web.MyUtils;
/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegistrationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userName= request.getParameter("username").trim();
		String yourName = request.getParameter("yourname").trim();
		String phoneNumber = request.getParameter("phonenumber").trim();
		String email = request.getParameter("email").trim();
		String address = request.getParameter("address").trim();
		String password = request.getParameter("password").trim();		
		String confirmPassword = request.getParameter("confirmpassword").trim();
		String gender = request.getParameter("gender");
		String birthDateDay = request.getParameter("birthdateDay");
		String birthDateMonth = request.getParameter("birthdateMonth");
		String birthDateYear = request.getParameter("birthdateYear");
		Date birthDate = Date.valueOf(birthDateYear+"-"+birthDateMonth+"-"+birthDateDay);
		//java.sql.Date birthDate = new java.sql.Date(util_BirthDate.get);
		
		String url= "";
		boolean hasError=false;
		if (UserAccount.checkUserName(userName) == false) {
			hasError=true;
			request.setAttribute("userNameError", "You have to enter user name");
		}
		if (UserAccount.checkName(yourName) == false) {
			hasError=true;
			request.setAttribute("yourNameError", "You have to enter your name");
		}
		if (UserAccount.checkPhoneNumber(phoneNumber) == false) {
			hasError=true;
			request.setAttribute("phoneumberError", "Phone number have 10 digits");
		}
		if (UserAccount.checkEmail(email) == false) {
			hasError=true;
			request.setAttribute("emailError", "Incorrect! Example: abc@xyz");
		}
		if (UserAccount.checkPhoneNumber(phoneNumber) == false) {
			hasError=true;
			request.setAttribute("phoneNumberError", "Phone number have 10 digits");
		}
		if (UserAccount.checkAddress(address) == false) {
			hasError=true;
			request.setAttribute("addressError", "You have to enter your address");
		}
		if (UserAccount.checkPassword(password) == false) {
			hasError=true;
			request.setAttribute("passwordError", "You have to enter your password");
		}
		if (UserAccount.checkConfirmPassword(confirmPassword) == false) {
			hasError=true;
			request.setAttribute("passwordError", "You have to enter password again");
		}
		if (UserAccount.checkTheSamePassword(password, confirmPassword) == false) {
			hasError=true;
			request.setAttribute("passwordError", "Password and confirmPassword are diffrences");
		}
		if (hasError) {
			url="/views/web/register.jsp";
		}
		else {
			Connection conn = MyUtils.getStoredConnection(request);
			AccountModel user = new AccountModel();
			user.setTenTaiKhoan(userName);
			user.setMatKhau(password);
			user.setTenKhachHang(yourName);
			user.setGioitinh(gender);
			user.setSoDienThoai(phoneNumber);
			user.seteMail(email);
			user.setNgaySinh(birthDate);
			user.setDiaChi(address);
			user.setSoLuotMua(0);
			user.setVaiTro("user");
			UserDAO userDAO =  new UserDAO();
			if (userDAO.insert(conn, user)) {
				System.out.print("Insert user successfully");
				url="/views/web/login.jsp";
			}
			else {
				url="/views/web/register.jsp";
			}
			
		}
		
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
