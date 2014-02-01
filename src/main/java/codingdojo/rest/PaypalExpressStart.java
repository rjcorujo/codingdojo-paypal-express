package codingdojo.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 * Servlet implementation class Paypal
 */
public class PaypalExpressStart extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaypalExpressStart() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     *     https://developer.paypal.com/webapps/developer/docs/classic/express-checkout/integration-guide/ECGettingStarted/
     *     https://developer.paypal.com/webapps/developer/docs/classic/express-checkout/gs_expresscheckout/    
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ref = request.getParameter("ref");
        Content content = Request.Post("https://api-3t.sandbox.paypal.com/nvp")
                .bodyForm(Form.form().add("METHOD",    "SetExpressCheckout")
                        .add("VERSION", "109")
                        .add("USER",      "sdk-three_api1.sdk.com")
                        .add("PWD",       "QFZCWN5HZM8VBG7Q")
                        .add("SIGNATURE", "A-IzJhZZjhg29XQ2qnhapuwxIDzyAZQ92FRP5dqBzVesOkzbdUONzmOU")
                        .add("PAYMENTREQUEST_0_AMT", "12.00")
                        .add("PAYMENTREQUEST_0_CURRENCYCODE", "EUR")
                        .add("PAYMENTREQUEST_0_PAYMENTACTION", "Sale")
                        .add("RETURNURL", "http://example_url?ref=" + ref)
                        .add("CANCELURL", "http://example_url?ref=" + ref)
                        .build()).execute().returnContent();
        String contentBody = content.asString();
        String[] parts = contentBody.split("&");
        String token = "";
        for (String part : parts){
            if (part.startsWith("TOKEN")){
                token = part.split("=")[1];
                break;
            }
        }
        if (StringUtils.isBlank(token))
            response.getWriter().print("En este momento tenemos problemas para conectar con Paypal. Por favor intentelo mas tarde. Disculpe las molestias");
        else
            response.sendRedirect("https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + token);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

}