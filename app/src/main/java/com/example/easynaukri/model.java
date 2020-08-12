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
    String title,softtitle,salestitle,accounttitle,constructiontitle,computertitle;
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
         constructiontitle="CONSTRUCTION";
         computertitle="COMPUTER WORK";
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
                else if(title.equals(constructiontitle)){
                    list.add(new homeData(jobData.pipingtitle,R.drawable.pipingwork,jobData.piping,jobData.pipingskill1,jobData.pipingskill2,jobData.pipingskill3,jobData.pipingskill4,jobData.pipingskill5,jobData.pipingamount));
                    list.add(new homeData(jobData.roofertitle,R.drawable.roofwork,jobData.roofer,jobData.rooferskill1,jobData.rooferskill2,jobData.rooferskill3,jobData.rooferskill4,"",jobData.rooferamount));
                    list.add(new homeData(jobData.electrictitle,R.drawable.electricwork,jobData.electric,jobData.electricskill1,jobData.electricskill2,jobData.electricskill3,jobData.electricskill4,jobData.electricskill5,jobData.electricamount));
                    list.add(new homeData(jobData.civiltitle,R.drawable.civilengineering,jobData.civilengineering,jobData.civilskill1,jobData.civilskill2,jobData.civilskill3,jobData.civilskill4,jobData.civilskill5,jobData.civilamount));
                    list.add(new homeData(jobData.plastetitle,R.drawable.plasterwork,jobData.plasterwork,jobData.plasterskill1,jobData.plasterskill2,jobData.plasterskill3,jobData.plasterskill4,"",jobData.plasteramount));
                    list.add(new homeData(jobData.carpentrytitle,R.drawable.carpentry,jobData.carpentry,jobData.carpentryskill1,jobData.carpentryskill2,jobData.carpentryskill3,jobData.carpentryskill4,jobData.carpentryskill5,jobData.carpentryamount));
                    list.add(new homeData(jobData.steeltitle,R.drawable.steelconstruction,jobData.steelconstruction,jobData.steelskill1,jobData.steelskill2,jobData.steelskill3,jobData.steelskill4,jobData.steelskill5,jobData.steelamount));
                    list.add(new homeData(jobData.glasstitle,R.drawable.glasswork,jobData.glasswork,jobData.glasskill1,jobData.glasskill2,jobData.glasskill3,jobData.glasskill4,jobData.glasskill5,jobData.glassamount));
                    list.add(new homeData(jobData.interiortitle,R.drawable.interiorfinishing,jobData.interirorfinishing,jobData.interiorskill1,jobData.interiorskill2,jobData.interiorskill3,jobData.interiorskill4,"",jobData.interioramount));
                    list.add(new homeData(jobData.waterproofingtitle,R.drawable.waterproofing,jobData.waterproofing,jobData.waterproofingskill1,jobData.waterproofingskill2,jobData.waterproofingskill3,jobData.waterproofingskill4,"",jobData.waterproofingamount));
                    list.add(new homeData(jobData.coatingtitle,R.drawable.coatingwork,jobData.coatingwork,jobData.coatingworkskill1,jobData.coatingworkskill2,jobData.coatingworkskill3,jobData.coatingworkskill4,jobData.coatingworkskill5,jobData.coatingworkamount));

                }
                else if(title.equals(computertitle)){
                    list.add(new homeData(jobData.dataentrytitle,R.drawable.dataentry,jobData.dataentry,jobData.dataentryskill1,jobData.dataentryskill2,jobData.dataentryskill3,jobData.dataentryskill4,jobData.dataentryskill5,jobData.dataentryamount));
                    list.add(new homeData(jobData.computerhardwaretitle,R.drawable.hardwareengineer,jobData.computerhardwareengineer,jobData.computerhardwareskill1,jobData.computerhardwareskill2,jobData.computerhardwareskill3,jobData.computerhardwareskill4,jobData.computerhardwareskill5,jobData.hardwareamount));
                    list.add(new homeData(jobData.computeroperatortitle,R.drawable.computeroperator,jobData.computeroperator,jobData.computeroperatorskill1,jobData.computeroperatorskill2,jobData.computeroperatorskill3,jobData.computeroperatorskill4,jobData.computeroperatorskill5,jobData.computeroperatoramount));
                    list.add(new homeData(jobData.transcriptiontitle,R.drawable.transcriptioneditor,jobData.transcriptioneditor,jobData.transcriptionskill1,jobData.transcriptionskill2,jobData.transcriptionskill3,jobData.transcriptionskill4,jobData.transcriptionskill5,jobData.transcriptionamount));
                    list.add(new homeData(jobData.technicalsupporttitle,R.drawable.technicalsupport,jobData.technicalsupport,jobData.technicalsupportskill1,jobData.technicalsupportskill2,jobData.technicalsupportskill3,jobData.technicalsupportskill4,jobData.technicalsupportskill5,jobData.technicalsupportamount));
                    list.add(new homeData(jobData.desktopsupporttitle,R.drawable.desktopsupport,jobData.desktopsupport,jobData.desktopsupportskill1,jobData.desktopsupportskill2,jobData.desktopsupportskill3,jobData.desktopsupportskill4,jobData.desktopsupportskill5,jobData.desktopsupportamount));

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