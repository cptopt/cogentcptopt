package com.cogent.harikrishna.cogentcptopt.Activities;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.Toast;

import com.cogent.harikrishna.cogentcptopt.R;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by madhu on 20-Aug-16.
 */
public class Fab extends Activity {
    FloatingActionButton fab;
    CircleImageView profilePic;
    Button uploadPdf, downloadFile, upload;
    private static final int IMAGE_REQUEST_CODE = 1;
    private Bitmap bitmap;
    String pictureDirectoryPath, picturePath;
    Uri uri;
    String uploadedFileName,mail,fileName;
    StringTokenizer tokens;
    String first;
    String file_1;
    File file1;
    FileBody fileBody1;
    String response,empid;
    private JSONObject jsonObject1 = null;
    String errmsg,resumeerr;
    //String myUrl="http://10.80.15.119:8080/OptnCpt/rest/service/downloadResume";
    String myUrl="http://10.80.15.119:8088/RestFulJerseyWebserviceApplication/RestFulJersey/webservice/pdf";
    private static final String TAG = Fab.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fab);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Fab.this,EditDetails.class);
                startActivity(i);
            }
        });
        profilePic = (CircleImageView) findViewById(R.id.profilepic);
        upload = (Button) findViewById(R.id.upload);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mail  = prefs.getString("EMAILID","");
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        empid = prefs.getString("EMPID","");
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                File parentDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                fileName = parentDirectory.getName();
                pictureDirectoryPath = parentDirectory.getPath();
                uri = Uri.parse(pictureDirectoryPath);
                intent.setDataAndType(uri, "image/*");
                startActivityForResult(intent, IMAGE_REQUEST_CODE);

            }
        });
        uploadPdf = (Button) findViewById(R.id.uploadresume);
        uploadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try{
                    startActivityForResult(Intent.createChooser(intent,"Select file to upload"),2);
                } catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(), "Please install File Manager", Toast.LENGTH_SHORT).show();
                }
//                showFileChooser();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UploadpdfTask().execute("http://10.80.15.119:8080/OptnCpt/rest/service/employeeResumeUpload");
            }
        });
        downloadFile = (Button) findViewById(R.id.downloadResume);
        downloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(myUrl));
                // DownloadManager.Request request=new DownloadManager()
                request.setTitle("File Download");
                request.setDescription("file is being downloaded....");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                String nameOfTheFile = URLUtil.guessFileName(myUrl, null, MimeTypeMap.getFileExtensionFromUrl(myUrl));
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nameOfTheFile);
                DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                manager.enqueue(request);
            }
        });
//



//        fab = (FloatingActionButton) findViewById(R.id.profile_edit_fab);
//        fab.setOnClickListener( View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent editProfile = new Intent(Myprofile.this,EditDetails.class);
//                startActivity(editProfile);
//
//
//            }
//      });
    }
//
//    public static boolean isHostReachable(String url, int portAddress, int connectTimeOut){
//        boolean connected = false;
//        Socket socket;
//        try{
//            socket = new Socket();
//            SocketAddress address = new InetSocketAddress(url,portAddress);
//            socket.connect(address,connectTimeOut);
//            if (socket.isConnected()){
//                connected = true;
//                socket.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            socket = null;
//        }
//        return connected;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){

            if(requestCode == IMAGE_REQUEST_CODE){

                Uri selectedImage = data.getData();
                picturePath = getPath(selectedImage);

                //textView.setText(picturePath.replace(picturePath,"BCD"));
                System.out.println(picturePath.length());
                Log.v("************:","image" + selectedImage);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                picturePath = cursor.getString(columnIndex);
                cursor.close();
                bitmap = BitmapFactory.decodeFile(picturePath);

                // profilePic.setImageBitmap(bitmap);
                //  System.out.println("pic is setting");
                new UploadImageTask().execute();
            } else{
                try {
                    if (requestCode == 2) {
                        Uri selectdFileUri = data.getData();
                        file1 = new File(selectdFileUri.getPath());
                        //   Log.e(TAG, "File: " + file1.getName());
                        uploadedFileName = file1.getName();
                        //  Log.e(TAG, "FILE NAME: " + uploadedFileName);
                        tokens = new StringTokenizer(uploadedFileName, ":");
                        first = tokens.nextToken();
                        file_1 = tokens.nextToken().trim();
                        // Log.e(TAG, "FILE 1: " + file_1);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

        }

    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    //    private void showFileChooser() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/*");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        try{
//            startActivityForResult(Intent.createChooser(intent,"Select file to upload"),2);
//        } catch (ActivityNotFoundException e){
//            Toast.makeText(getApplicationContext(), "Please install File Manager", Toast.LENGTH_SHORT).show();
//        }
//    }
    private class UploadImageTask extends AsyncTask<Void, Void, String> {

        private String reqUrl = "http://10.80.15.119:8080/OptnCpt/rest/service/employeeImgUpload";
        private ProgressDialog dialog = new ProgressDialog(Fab.this);
        ByteArrayBody bab;
        String response;

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Uploading Image");
            dialog.setMessage("Processing...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL(reqUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //connection.connect();

                String reqHead = "Accept:application/json";
                connection.setRequestMethod("POST");
                connection.setRequestProperty("connection","Keep-Alive"+reqHead);
                //  connection.setRequestProperty("mail",""+mail);
                connection.setRequestProperty("employeeId",""+empid);
                // connection.setRequestProperty("mail",mail);
                //Header header = new Header();

                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                byte[] data = baos.toByteArray();

                bab = new ByteArrayBody(data,""+fileName);

                entity.addPart("image",bab);
                entity.addPart("Some other String to send",new StringBody("something"));

                connection.addRequestProperty("content-length",entity.getContentLength()+"");
                connection.addRequestProperty(entity.getContentType().getName(),entity.getContentType().getValue());

                OutputStream os = connection.getOutputStream();
                entity.writeTo(connection.getOutputStream());
                os.close();
                connection.connect();

                if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                    return readStream(connection.getInputStream());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String readStream(InputStream inputStream) {

            BufferedReader reader;
            StringBuilder builder = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            try {
                while ((line = reader.readLine())!=null){
                    builder.append(line);
                }
                response = builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {

            dialog.dismiss();
            if (response!=null) {
                //  profilePic.setImageBitmap(bitmap);
                JSONObject jobj = null;
                try {
                    jobj = new JSONObject(response);
                    String stat=jobj.getString("status");
                    if (stat.equals("true")){
                        String msg=jobj.getString("msg");
                        Toast.makeText(Fab.this,msg,Toast.LENGTH_LONG).show();
                        String imggggg=jobj.getString("Image");
                        byte[] byteArray =  Base64.decode(jobj.getString("Image"), Base64.DEFAULT) ;
                        Log.e(TAG, "Response Json: " + imggggg);
                        bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
                        //   Log.e(TAG, "Response Json: " + "decoded");
                        profilePic.setImageBitmap(bitmap);
                        Log.e(TAG, "Response Json: " + "imagesetted");
                        Toast.makeText(Fab.this,msg,Toast.LENGTH_LONG).show();
                    }else
                        errmsg=jobj.getString("err_msg");
                    Toast.makeText(Fab.this,errmsg,Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e(TAG, "Response Json: " + jobj);

                // Toast.makeText(getApplicationContext(), "Files Uploaded..", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "Server Failed...", Toast.LENGTH_SHORT).show();
        }
    }
    public class UploadpdfTask extends AsyncTask<String,String,String>{

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Fab.this);
            dialog.setTitle("Uploading");
            dialog.setMessage("Please Wait...");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                String reqHead = "Accept:application/json";
                connection.setRequestMethod("POST");
                connection.setRequestProperty("connection","Keep-Alive"+reqHead);
                // connection.setRequestProperty("mail",""+mail);
                connection.setRequestProperty("employeeId",""+empid);

                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//                file1 = new File(Environment.getExternalStorageDirectory(),file_1);
                fileBody1 = new FileBody(file1);
                reqEntity.addPart("image",fileBody1);
                connection.addRequestProperty("content-length",reqEntity.getContentLength()+"");
                connection.addRequestProperty(reqEntity.getContentType().getName(),reqEntity.getContentType().getValue());
                OutputStream os = connection.getOutputStream();
                reqEntity.writeTo(connection.getOutputStream());
                os.close();
                connection.connect();

                if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                    return readStream(connection.getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String readStream(InputStream inputStream) {

            BufferedReader reader;
            StringBuilder builder = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            try {
                while ((line = reader.readLine())!=null){
                    builder.append(line);
                }
                response = builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (response!=null) {

                JSONObject jobj;
                try {
                    jobj = new JSONObject(response);
                    Log.e(TAG, "Response Json: " + response);
                    String stat=jobj.getString("status");
                    if (stat.equals("true")){
                        String msg=jobj.getString("msg");
                        Toast.makeText(Fab.this,msg,Toast.LENGTH_LONG).show();
                    }else {
                        resumeerr = jobj.getString("err_msg");
                        Toast.makeText(Fab.this, resumeerr, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                dialog.dismiss();
                // Toast.makeText(getApplicationContext(), "Files Uploaded..", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "Server Failed...", Toast.LENGTH_SHORT).show();
        }
    }
    public class FileDwnloadAsyn extends AsyncTask<Void,Void,Void> {

        private JSONObject jsonObject1;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(myUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                //   connection.setRequestProperty("employeeId",empid);
                jsonObject1 = new JSONObject();
                jsonObject1.put("employeeId", ""+ empid);
                String jsonObj = jsonObject1.toString();
                Log.e(TAG, "doInBackground: " + jsonObj);
                //Header
                connection.setRequestProperty("resumeDetails", "" + jsonObj);
                connection.connect();

                File rootDirectory = new File(Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DOWNLOADS), "My Downloads");
                if (!rootDirectory.exists()) {
                    rootDirectory.mkdirs();
                }

                String nameOfTheFile = URLUtil.guessFileName(myUrl, null, MimeTypeMap.getFileExtensionFromUrl(myUrl));
                File file = new File(rootDirectory, nameOfTheFile);
                file.createNewFile();

                InputStream stream = connection.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int bytecount = 0;
                while ((bytecount = stream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, bytecount);
                }
                outputStream.close();
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(file));
                sendBroadcast(intent);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_SHORT).show();
            super.onPostExecute(result);
        }
    }
}
