package com.example.easynaukri;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easynaukri.ui.InternetConnection;
import com.example.easynaukri.ui.slideshow.SlideshowFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

//import android.support.v7.app.AppCompatActivity;

/**
 * Created by kamal_bunkar on 02-02-2018.
 */

public class checksum extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    FirebaseAuth firebaseAuth;
    // InternetConnection connection=new InternetConnection();
    String custid="", orderId="", mid="";
    String status="Pending";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //initOrderId();
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent = getIntent();
        orderId = intent.getExtras().getString("orderid");
        custid = intent.getExtras().getString("custid");

        mid = "WXXCMA51547144336444"; /// your marchant key
        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
// vollye , retrofit, asynch

    }

    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(checksum.this);

        //private String orderId , mid, custid, amt;
        String url ="http://easynaukrimirza.000webhostapp.com/paytm/generateChecksum.php";
       // String varifyurl = "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
        String varifyurl    ="https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+orderId;
        String CHECKSUMHASH ="";

                @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        protected String doInBackground(ArrayList<String>... alldata) {
            JSONParser jsonParser = new JSONParser(checksum.this);
            String param=
                    "MID="+mid+
                    "&ORDER_ID=" + orderId+
                    "&CUST_ID="+custid+
                    "&CHANNEL_ID=WAP&TXN_AMOUNT=299&WEBSITE=WEBSTAGING"+
                            "&CALLBACK_URL="+ varifyurl+"&INDUSTRY_TYPE_ID=Retail";

            JSONObject jsonObject = jsonParser.makeHttpRequest(url,"POST",param);
            // yaha per checksum ke saht order id or status receive hoga..
            Log.e("CheckSum result >>",jsonObject.toString());
            if(jsonObject != null){
                Log.e("CheckSum result >>",jsonObject.toString());
                try {

                    CHECKSUMHASH=jsonObject.has("CHECKSUMHASH")?jsonObject.getString("CHECKSUMHASH"):"";
                    Log.e("CheckSum result >>",CHECKSUMHASH);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ","  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

           // PaytmPGService Service = PaytmPGService.getStagingService();
           // when app is ready to publish use production service
             PaytmPGService Service = PaytmPGService.getProductionService();

            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
            HashMap<String, String> paramMap = new HashMap<String, String>();
            //these are mandatory parameters
            paramMap.put("MID", mid); //MID provided by paytm
            paramMap.put("ORDER_ID", orderId);
            paramMap.put("CUST_ID", custid);
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT", "299");
            paramMap.put("WEBSITE", "WEBSTAGING");
            paramMap.put("CALLBACK_URL" ,varifyurl);
            //paramMap.put( "EMAIL" , "abc@gmail.com");   // no need
           // paramMap.put( "MOBILE_NO" , "9144040888");  // no need
            paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
            //paramMap.put("PAYMENT_TYPE_ID" ,"CC");    // no need
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");

            PaytmOrder Order = new PaytmOrder(paramMap);
            Log.e("checksum ", "param "+ paramMap.toString());
            Service.initialize(Order,null);
            // start payment service call here
            Service.startPaymentTransaction(checksum.this, true, true,
                    checksum.this  );


        }

    }


    @Override
    public void onTransactionResponse(Bundle bundle) {
        Log.e("checksum ", " respon true " + bundle.toString());
        //Toast.makeText(this,"Transcation Success",Toast.LENGTH_LONG).show();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String useruid = firebaseUser.getUid();
        DatabaseReference myRef = database.getReference().child(useruid).child("PaymentDetails");
        HashMap<String,Object> result=new HashMap<String,Object>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Objects.equals(bundle.getString("STATUS"), "TXN_SUCCESS")) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdfdate = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdftime = new SimpleDateFormat(" HH:mm:ss");
                String strDate = sdfdate.format(c.getTime());
                String strTime = sdftime.format(c.getTime());
                String payment="Success";
                String paymentmethod="Paytm";
                String cancel="";
                status="Success";
                PaymentDetails details=new PaymentDetails("299",orderId,strDate,strTime,paymentmethod,cancel,payment,status);
                myRef.setValue(details);
                Toast.makeText(this,"Success",Toast.LENGTH_LONG).show();
                DatabaseReference databaseReference=database.getReference().child("TotalPayment").child(useruid);
                Random r=new Random();
                int generated=r.nextInt(1000);
                HashMap<String,String> res=new HashMap<>();
                res.put(generated+strDate+strTime,"Paid");
                databaseReference.setValue(res);
                finish();
            } else if (!bundle.getBoolean("STATUS")) {
                //    Payment Failed
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdfdate = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdftime = new SimpleDateFormat(" HH:mm:ss");
                String strDate = sdfdate.format(c.getTime());
                String strTime = sdftime.format(c.getTime());
                String payment="Failed";
                String paymentmethod="All In One Pay";
                String cancel="Yes";
                PaymentDetails details=new PaymentDetails("299",orderId,strDate,strTime,paymentmethod,cancel,payment,status);
                myRef.setValue(details);
                Toast.makeText(this,"Failed",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public void networkNotAvailable() {
         Toast.makeText(this,"No Network",Toast.LENGTH_LONG).show();
    }

    @Override
    public void clientAuthenticationFailed(String s) {
        Toast.makeText(this,"Mid or key is wrong",Toast.LENGTH_LONG).show();
    }

    @Override
    public void someUIErrorOccurred(String s) {
        Log.e("checksum ", " ui fail respon  "+ s );
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Log.e("checksum ", " error loading pagerespon true "+ s + "  s1 " + s1);
        Toast.makeText(this,"Error loading page response",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Log.e("checksum ", " cancel call back respon  " );
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String useruid = firebaseUser.getUid();
        DatabaseReference myRef = database.getReference().child(useruid).child("PaymentDetails");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdfdate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdftime = new SimpleDateFormat(" HH:mm:ss");
        String strDate = sdfdate.format(c.getTime());
        String strTime = sdftime.format(c.getTime());
        String payment="failed";
        String paymentmethod="All In One Pay";
        String cancel="Canceled By User";
        PaymentDetails details=new PaymentDetails("299",orderId,strDate,strTime,paymentmethod,cancel,payment,status);
        myRef.setValue(details);
        finish();
        Toast.makeText(this,"Transcation Canceled",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        //Log.e("checksum ", "  transaction cancel " );
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String useruid = firebaseUser.getUid();
        DatabaseReference myRef = database.getReference().child(useruid).child("PaymentDetails");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdfdate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdftime = new SimpleDateFormat(" HH:mm:ss");
        String strDate = sdfdate.format(c.getTime());
        String strTime = sdftime.format(c.getTime());
        String payment="failed";
        String paymentmethod="All In One Pay";
        String cancel="Canceled By User";
        PaymentDetails details=new PaymentDetails("299",orderId,strDate,strTime,paymentmethod,cancel,payment,status);
        myRef.setValue(details);
        finish();
        Toast.makeText(this,"Transcation Canceled",Toast.LENGTH_LONG).show();
    }


}
