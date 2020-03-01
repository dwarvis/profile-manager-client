package edu.cis.clientapp.Controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import edu.cis.clientapp.Model.CISBookAdapter;
import edu.cis.clientapp.Model.Constants;
import edu.cis.clientapp.Model.Request;
import edu.cis.clientapp.Model.Row;
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
    TextView testTextView;
    RecyclerView recView;
    CISBookAdapter adapter;
    ArrayList<String> nameList;
    ArrayList<String> statusList;
    ArrayList<Bitmap> imgList;
    ArrayList<Row> rowList;
    Bitmap bitmap;



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
        testTextView = findViewById(R.id.displayNameText);
        nameList = new ArrayList<>();
        statusList = new ArrayList<>();
        rowList = new ArrayList<>();

        /**
         * https://www.androidauthority.com/how-to-use-recycler-views-836053/
         * https://stackoverflow.com/questions/9445661/how-to-get-the-context-from-anywhere
        **/
        nameList = new ArrayList();
        statusList = new ArrayList();
        imgList = new ArrayList();
        rowList = new ArrayList();

        recView = findViewById(R.id.recView);
        adapter = new CISBookAdapter(rowList);
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(this));




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
        deleteProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String userInput = inputNameField.getText().toString();
                deleteProf(userInput);
            }
        });

        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
////                startActivityForResult(new Intent(Intent.ACTION_PICK,
////                                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
////                        GET_FROM_GALLERY);

                String userInput = inputNameField.getText().toString();
                addProf(userInput);
            }
        });
    }

    private void addProf(String name)
    {
        try
        {
            Request ap = new Request("addProfile");
            ap.addParam("name", name);

            // We are ready to send our request
            String result = SimpleClient.makeRequest(Constants.HOST, ap);

            //set image
            startActivityForResult(new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                    Constants.GET_FROM_GALLERY);

            adapter.notifyDataSetChanged();


            //Toast Code:
            // https://developer.android.com/guide/topics/ui/notifiers/toasts
            Toast.makeText(this, "added profile: \"" + name + "\" to server.", Toast.LENGTH_LONG).show();
        }
        catch (IOException e)
        {
            Toast.makeText(this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
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
            for(int e = 0; e <= rowList.size()-1; e++)
            {
                if (rowList.get(e).getName().equals(name)) {rowList.remove(e);}
            }

            adapter.notifyDataSetChanged();

            Toast.makeText(this,
                    "removed profile:" + name + " from server.",
                    Toast.LENGTH_LONG).show();
        }
        catch (IOException e)
        {
            Toast.makeText(this,
                    e.getMessage(),
                    Toast.LENGTH_LONG).show();
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
            testTextView.setText("result is: " + result);
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void addImage(String name, String image)
    {
        try
        {
            Request example = new Request("setImage");
            example.addParam("name", name);
            example.addParam("imageString", image);
            String result = SimpleClient.makeRequest(Constants.HOST, example);
            Toast.makeText(this, "Image adding: " + result, Toast.LENGTH_LONG).show();
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public String getImage(String name)
    {
//        get the string back via request
        try
        {

            Request example = new Request("getImage");
            example.addParam("name", name);
            System.out.println("THIS IS A MESSAGE WHEN IT STARTS");
            String result = SimpleClient.makeRequest(Constants.HOST, example);
            System.out.println("THIS IS A MESSAGE WHEN IT WORKS" + result);
            return result;
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //https://stackoverflow.com/questions/13562429/how-many-ways-to-convert-bitmap-to-string-and-vice-versa
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
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

    @Override
    //https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode == Constants.GET_FROM_GALLERY && resultCode == Activity.RESULT_OK)
        {

            bitmap = null;
            Uri selectedImage = data.getData();

            try
            {
                //get bitmap from gallery image
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                //get path of gallery image
                String path = MediaStore.Images.Media.RELATIVE_PATH;

                //get the file at that path
                File f = new File(path);

                //compress the image because its too big
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 1, outputStream);
                byte[] byteArray = outputStream.toByteArray();
                String encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT);

                //get the name and send to server
                String name = inputNameField.getText().toString();
                addImage(name, encodedString);

                //ew
                String temp = getImage(name);
                System.out.println(temp);
                Bitmap image = StringToBitMap(temp);
                System.out.println("DID IT WORK");
                //make a new row and add it to data
                //set name and image
                Row tempRow = new Row();

                tempRow.setImage(image);
                tempRow.setName(name);
                rowList.add(tempRow);

                //update adapter
                adapter.notifyDataSetChanged();

            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
