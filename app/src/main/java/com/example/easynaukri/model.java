package com.example.easynaukri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.easynaukri.ui.homeData;
import com.example.easynaukri.ui.tools.RecyclerAdapter;

import java.util.ArrayList;

public class model extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    ArrayList<homeData> list;
    LinearLayout layout;
    String title,softtitle,salestitle,accounttitle;
     color_management color_theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);
        recyclerView=findViewById(R.id.modelrecycler);
        color_theme=new color_management(this);
        layout=findViewById(R.id.linear_jobbackmodel);
         title=getIntent().getStringExtra("title");
         softtitle="SOFTWARE FIELD";
         salestitle="SALES AND MARKETING";
         accounttitle="ACCOUNT AND FINANCE";
        String imageid=getIntent().getStringExtra("image");
       // String passcolor=getIntent().getStringExtra("passcolor");
       // String descp=getIntent().getStringExtra("descp");
        getSupportActionBar().setTitle("Jobs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar));
         checkThemeColor();
        //layout.setBackgroundResource(R.drawable.brown_grid_back);
        //setSupportActionBar(toolbar);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        list=new ArrayList<homeData>();
        ;//to get data class
                JobData jobData=new JobData();
                if (title.equals(softtitle)) {
                    list.add(new homeData(jobData.webtitle, R.drawable.webdevleopment, jobData.webdeveloper, jobData.webskill1, jobData.webskill2, jobData.webskill3, jobData.webskill4, jobData.webskill5, jobData.webamount));
                    list.add(new homeData(jobData.apptitle, R.drawable.appdevelopment, jobData.appdeveloper, jobData.appskill1, jobData.appskill2, jobData.appskill3, "", "", jobData.appamount));
                    list.add(new homeData(jobData.graphictitle, R.drawable.graphicdesign, jobData.graphicdesign, jobData.graphicskill1, jobData.getGraphicskill2, jobData.gamedevleoperskill3, "", "", jobData.graphicamount));
                    list.add(new homeData(jobData.uiuxtitle, R.drawable.uiux, jobData.uiux, jobData.uiuxskill1, jobData.getUiuxskill2, jobData.uiuxskill3, jobData.uiuxskill4, jobData.uiuxskill5, jobData.uiuxamount));
                    list.add(new homeData(jobData.softwaretitle, R.drawable.softwaredeveloper, jobData.softwaredeveloper, jobData.softwareskill1, jobData.softwareskill2, jobData.softwareskill3, jobData.softwareskill4, jobData.softwareskill5, jobData.softwareamount));
                    list.add(new homeData(jobData.databasetitle, R.drawable.databaseadministrator, jobData.databaseadmin, jobData.databaseskill1, jobData.databaseskill2, "", "", "", jobData.databaseamount));
                    list.add(new homeData(jobData.devopstitle, R.drawable.devops, jobData.devops, jobData.devopsskill1, jobData.devopsskill2, jobData.devopskill3, "", "", jobData.devopsamount));
                    list.add(new homeData(jobData.gamedevelopertitle, R.drawable.gamedeveloper, jobData.gamedeveloper, jobData.gamedevleoperskill1, jobData.gamedevleoperskill2, jobData.gamedevleoperskill3, "", "", jobData.gamedeveloperamount));
                    list.add(new homeData(jobData.websitetitle, R.drawable.websitecontentwriter, jobData.websitewriter, jobData.websiteskill1, jobData.websiteskill2, jobData.websiteskill3, jobData.websiteskill4, jobData.websiteskill5, jobData.websiteamount));
                    list.add(new homeData(jobData.backendoperationstitle, R.drawable.backendoperations, jobData.backendoperations, jobData.backendoperationsskill1, jobData.backendoperationsskill2, jobData.backendoperationsskill3, "", "", jobData.backendamount));
                    list.add(new homeData(jobData.softwaretestingtitle, R.drawable.softwaretesting, jobData.softwaretesting, jobData.softwaretestingskill1, jobData.softwaretestingskill2, jobData.softwaretestingskill3, jobData.softwaretestingskill4, jobData.softwaretestingskill5, jobData.softwaretestingamount));
                    list.add(new homeData(jobData.cybersecuritytitle, R.drawable.cybersecurity, jobData.cybersecurity, jobData.cybersecurityskill1, jobData.cybersecurityskill2, jobData.cybersecurityskill3, jobData.cybersecurityskill4, jobData.cybersecurityskill5, jobData.cybersecurityamount));
                    list.add(new homeData(jobData.backenddevelopertitle, R.drawable.backenddeveloper, jobData.backenddeveloper, jobData.backenddeveloperskill1, jobData.backenddeveloperskill2, jobData.backenddeveoperskill3, jobData.backenddeveloperskill4, jobData.backenddeveloperskill5, jobData.backenddeveloperamount));
                } else if (title.equals(salestitle)) {
                    list.add(new homeData(jobData.salestitle, R.drawable.salesexecutive, jobData.salesexe, jobData.salesskill1, jobData.salesskill2, jobData.salesskill3, jobData.salesskill4, jobData.salesskill5, jobData.salesamount));
                    list.add(new homeData(jobData.digitaltitle, R.drawable.digitalmarket, jobData.digitalmarket, jobData.digitalskill1, jobData.digitalskill2, jobData.digitalskill3, jobData.digitalskill4, jobData.digitalskill5, jobData.digitalamount));
                    list.add(new homeData(jobData.assistanttitle, R.drawable.assistanatsalesmanager, jobData.assistantmanager, jobData.assistantskill1, jobData.assistantskill2, jobData.assistantskill3, "", "", jobData.assistantamount));
                    list.add(new homeData(jobData.operationaltitle, R.drawable.operationalmanager, jobData.operationalmanager, jobData.operationalskill1, jobData.operationalskill2, jobData.operationalskill3, jobData.operationalskill4, "", jobData.operationalamount));
                    list.add(new homeData(jobData.documentationexecutivetitle, R.drawable.documentationexecutive, jobData.documentationexecutive, jobData.documentationexecutiveskill1, jobData.documentationexecutiveskill2, jobData.documentationexecutiveskill3, jobData.documentationexecutiveskill4, "", jobData.documentationexecutiveamount));
                    list.add(new homeData(jobData.fieldsupporttitle, R.drawable.fieldsupportexecutive, jobData.fieldsupportexe, jobData.fieldsupportskill1, jobData.fieldsupportskill2, jobData.fieldsupportskill3, jobData.fieldsupportskill4, jobData.fieldsupportskill5, jobData.fieldsupportamount));
                } else if (title.equals(accounttitle)) {
                    list.add(new homeData(jobData.fixedincometitle, R.drawable.fixedincomesales, jobData.fixedincome, jobData.fixedincomeskill1, jobData.fixedincomeskill2, jobData.fixedincomeskill3, "", "", jobData.fixedincomeamount));
                    list.add(new homeData(jobData.financetitle, R.drawable.financemanager, jobData.financemanager, jobData.financeskill1, jobData.financeskill2, jobData.financeskill3, jobData.financeskill4, jobData.financeskill5, jobData.financeamount));
                    list.add(new homeData(jobData.bussinesstitle, R.drawable.bussinessanalyst, jobData.bussinessanalyst, jobData.bussinessanalystskill1, jobData.bussinessanalystskill2, jobData.bussinessanalystskill3, jobData.bussinessanalystskill4, jobData.bussinessanalystskill5, jobData.bussinessamount));
                    list.add(new homeData(jobData.accounttitle, R.drawable.senioraccountmanager, jobData.accountmanager, jobData.accountskill1, jobData.accountskill2, jobData.accountskill3, jobData.accountskill4, jobData.accountskill5, jobData.accountamount));
                }

        adapter=new RecyclerAdapter(this,list);
        recyclerView.setAdapter(adapter);
        //Toast.makeText(this,title+passcolor,Toast.LENGTH_LONG).show();
    }
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void checkThemeColor(){
        String blue="blue";
        String red="red";
        String green="green";
        String orange="orange";
        String yellow="yellow";
        String dark="dark";
        String brown="brown";
        String color=color_theme.getColor();
        if(color.equals(blue)){
            //linearLayout1.setBackgroundResource(R.drawable.gradient_navheader);
            //linearLayout2.setBackgroundResource(R.drawable.gradient_navheader);
            //linearLayout3.setBackgroundResource(R.drawable.gradient_navheader);
            //linearLayout4.setBackgroundResource(R.drawable.gradient_navheader);

            layout.setBackgroundResource(R.drawable.blue_grid_back);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.blue_grid_back));
        }
        else if(color.equals(red)){
            layout.setBackgroundResource(R.drawable.red_grid_back);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar));
        }
        else if(color.equals(green)){
            layout.setBackgroundResource(R.drawable.green_grid_back);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.green_gradient_themea));
        }
        else if(color.equals(orange)){

            layout.setBackgroundResource(R.drawable.orange_grid_back);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.orange_gradient_themea));

        }
        else if(color.equals(yellow)){

            layout.setBackgroundResource(R.drawable.yellow_grid_back);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.yellow_gradient_themea));
        }
        else if(color.equals(dark)){

            layout.setBackgroundResource(R.drawable.dark_grid_back);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.dark_gradient_themea));

        }
        else if(color.equals(brown)){

            layout.setBackgroundResource(R.drawable.brown_grid_back);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.brown_gradient_themea));

        }
    }
}