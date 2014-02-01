package codingdojo.rest;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class PaypalConfirm
 */
public class PaypalConfirm extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaypalConfirm() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String payerId = request.getParameter("PayerID");
        String ref = request.getParameter("ref");
        Content content = GetExpressCheckoutDetails(token);
        String contentBody = content.asString();
        SavingExpedientsRepository repo = new SavingExpedientsRepository();
        SavingExpedient expedient = repo.findBy(ref);
        if (isSuccess(contentBody)){
            Content finalContent = DoExpressCheckoutPayment(token, payerId);
            if (isSuccess(finalContent.asString())){
                expedient.setPaid(true);
                repo.saveOrUpdate(expedient);
                response.sendRedirect("./calpot_savingdisplay.jsp?ref=" + ref);
            }
            else{
                expedient.setAdditionalInfo(contentBody);
                repo.saveOrUpdate(expedient);
                response.getWriter().print("En este momento tenemos problemas para conectar con Paypal. Por favor intentelo mas tarde. Disculpe las molestias");
            }
        }
        else{
            expedient.setAdditionalInfo(contentBody);
            repo.saveOrUpdate(expedient);
            response.getWriter().print("En este momento tenemos problemas para conectar con Paypal. Por favor intentelo mas tarde. Disculpe las molestias");
        }
    }

    private boolean isSuccess(String content){
        String[] parts = content.split("&");
        for (String part : parts)
            if (part.startsWith("ACK"))
                if (part.split("=")[1].equals("Success"))
                    return true;
        return false;
    }

    private Content GetExpressCheckoutDetails(String token)
            throws ClientProtocolException, IOException {
        Content content = Request.Post("https://api-3t.sandbox.paypal.com/nvp")
                .bodyForm(Form.form().add("METHOD",   "GetExpressCheckoutDetails")
                        .add("VERSION",  "109")
                        .add("USER",     "sdk-three_api1.sdk.com")
                        .add("PWD",      "QFZCWN5HZM8VBG7Q")
                        .add("SIGNATURE","A-IzJhZZjhg29XQ2qnhapuwxIDzyAZQ92FRP5dqBzVesOkzbdUONzmOU")
                        .add("TOKEN", token)
                        .build()).execute().returnContent();
        return content;
    }

    private Content DoExpressCheckoutPayment(String token, String payerId)
            throws ClientProtocolException, IOException {
        Content content = Request.Post("https://api-3t.sandbox.paypal.com/nvp")
                .bodyForm(Form.form().add("METHOD",   "DoExpressCheckoutPayment")
                        .add("VERSION",  "109")
                        .add("USER",     "sdk-three_api1.sdk.com")
                        .add("PWD",      "QFZCWN5HZM8VBG7Q")
                        .add("SIGNATURE","A-IzJhZZjhg29XQ2qnhapuwxIDzyAZQ92FRP5dqBzVesOkzbdUONzmOU")
                        .add("TOKEN", token)
                        .add("PAYERID", payerId)
                        .add("PAYMENTREQUEST_0_PAYMENTACTION", "Sale")
                        .add("PAYMENTREQUEST_0_AMT", "12.00")
                        .build()).execute().returnContent();
        return content;
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}