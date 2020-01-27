package edu.cis.clientapp.Controller;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import edu.cis.clientapp.Model.Constants;
import edu.cis.clientapp.Model.RAdapter;
import edu.cis.clientapp.Model.Request;
import edu.cis.clientapp.Model.SimpleClient;
import edu.cis.clientapp.R;

public class CISbookClientActivity extends AppCompatActivity
{
    /**
     * The address of the server that should be contacted when sending
     * any Requests.
     */

    Button lookupButton;
    Button deleteProfileButton;
    Button addProfileButton;
    EditText inputNameField;
    TextView displayNameText;
    ArrayList<String> nameList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Linking all the instance variables to the GUI
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        lookupButton = findViewById(R.id.lookupButton);
        addProfileButton = findViewById(R.id.addProfileButton);
        deleteProfileButton = findViewById(R.id.deleteProfileButton);
        inputNameField = findViewById(R.id.inputNameTextField);
        displayNameText = findViewById(R.id.displayNameText);
        /**
         * three lines below came from:
         * https://www.androidauthority.com/how-to-use-recycler-views-836053/
         * https://stackoverflow.com/questions/9445661/how-to-get-the-context-from-anywhere
        **/
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RView);
        RAdapter radapter = new RAdapter(MyApp.getContext(), nameList);
        recyclerView.setAdapter(radapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApp.getContext()));

        setSupportActionBar(toolbar);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        System.out.println("about to ping!");
        pingTheServer();

        setUpButtons();
    }

    private void setUpButtons()
    {
        lookupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String userInput = inputNameField.getText().toString();
                System.out.println("bttn clicked");

            }
        });

        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = inputNameField.getText().toString();
                System.out.println(userInput);
                addProf(userInput);
            }
        });
    }

    private void addProf(String name)
    {
        try
        {
            Request example = new Request("addProfile");
            example.addParam("name", name);
            // We are ready to send our request
            String result = SimpleClient.makeRequest(Constants.HOST, example);
            displayNameText.setText("added profile: \"" + result + "\" to server.");
        }
        catch (IOException e)
        {
            displayNameText.setText(e.getMessage());
        }
    }

    private void deleteProf(String name)
    {
        try
        {
            Request example = new Request("deleteProfile");
            example.addParam("name", name);
            // We are ready to send our request
            String result = SimpleClient.makeRequest(Constants.HOST, example);
            displayNameText.setText("remove profile: \"" + result + "\" to server.");
            nameList.add(result);
        }
        catch (IOException e)
        {
            displayNameText.setText(e.getMessage());
        }
    }


    private void pingTheServer()
    {
        try
        {
            // Lets prepare ourselves a new request with command "ping".
            Request example = new Request("ping");
            // We are ready to send our request
            String result = SimpleClient.makeRequest(Constants.HOST, example);
            System.out.println("ping!");
            System.out.println(("result is: " + result));
            displayNameText.setText("result is: " + result);
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
