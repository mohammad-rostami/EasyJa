package ir.EasyJa;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;


public class Webservice {
    EditText txt;
    TextView part1;
    TextView part2;
    String data;
    String[] separated;
    private ProgressDialog progress;

    public static class sessionConnection extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String oneSignalUserId = urls[1];
            String oneSignalRegistrationId = urls[2];
            String uniqueId = urls[3];
            String osVersion = urls[4];
            String appVersion = urls[5];
            String deviceName = urls[6];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("platform", G.CONTEXT.getResources().getString(R.string.platform));
                params1.put("oneSignalUserID", oneSignalUserId);
                params1.put("oneSignalRegistrationID", oneSignalRegistrationId);
                params1.put("uniqueID", uniqueId);
                params1.put("osVersion", osVersion);
                params1.put("appVersion", appVersion);
                params1.put("deviceName", deviceName);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);


                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
            return post_result;
        }


    }

    public static class getOptions extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String lat = urls[1];
            String lng = urls[2];
            String estateTypeID = urls[3];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("lat", lat);
                params1.put("lng", lng);
                params1.put("EstateTypeId", estateTypeID);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);
                String urlParameters = x.toString();
//                String urlParameters = postData.toString();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                String tokenType = G.TOKEN_TYPE.getString("TOKEN_TYPE", "");
                connection.setRequestProperty("Authorization", "bearer " + token);
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);


                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
            return post_result;
        }


    }

    public static class checkPayment extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String authority = urls[1];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("authority", authority);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                connection.setRequestProperty("Authorization", "bearer " + token);
                connection.setDoOutput(true);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);


                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
            return post_result;
        }


    }

    public static class getAds extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String mobile = urls[1];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("Mobile", mobile);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                connection.setRequestProperty("Authorization", "bearer " + token);
                connection.setDoOutput(true);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);


                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
            return post_result;
        }


    }

    public static class getFavoriteAds extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String userId = urls[1];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("UserID", userId);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                connection.setRequestProperty("Authorization", "bearer " + token);
                connection.setDoOutput(true);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);


                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
            return post_result;
        }


    }

    public static class registerByMobile extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
//            String sessionId = urls[1];
//            String token = urls[2];
            String mobile = urls[1];
            String email = urls[2];
            String address = urls[3];
            String phone = urls[4];
            String academyName = urls[5];
            String lat = urls[6];
            String lng = urls[7];
            String token = urls[8];

            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
//                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("Mobile", mobile);
                params1.put("Email", email);
                params1.put("Address", address);
                params1.put("PhoneNumber", phone);
                params1.put("Name", academyName);
                params1.put("Lat", lat);
                params1.put("Lng", lng);
                params1.put("Token", token);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("AHS_SessionId", sessionId);
//                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);

                    RegisterActivity.showToast(response);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class addAccount extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
//            String sessionId = urls[1];
//            String token = urls[2];
            String mobile = urls[1];
            String account = urls[2];


            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
//                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("Mobile", mobile);
                params1.put("AccountNumber", account);


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                connection.setRequestProperty("Authorization", "bearer " + token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);

                    RegisterActivity.showToast(response);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class getFavorite extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
//            String sessionId = urls[1];
//            String token = urls[2];
            String userId = urls[1];
//            String adsId = urls[2];

            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
//                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("UserID", userId);
//                params1.put("AdsID", adsId);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("AHS_SessionId", sessionId);
//                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);

//                    RegisterActivity.showToast(response);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class setAsFavorite extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
//            String sessionId = urls[1];
//            String token = urls[2];
            String userId = urls[1];
            String adsId = urls[2];

            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
//                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("UserID", userId);
                params1.put("AdsID", adsId);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                String tokenType = G.TOKEN_TYPE.getString("TOKEN_TYPE", "");
                connection.setRequestProperty("Authorization", "bearer " + token);
//                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
//                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("AHS_SessionId", sessionId);
//                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);

//                    RegisterActivity.showToast(response);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }


    public static class rentAds extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
//            String sessionId = urls[1];
//            String token = urls[2];
            String mobile = urls[1];
            int id = Integer.parseInt(urls[2]);

            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
//                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("RenterMobile", mobile);
                params1.put("AdsShiftId", id);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                String tokenType = G.TOKEN_TYPE.getString("TOKEN_TYPE", "");
                connection.setRequestProperty("Authorization", "bearer " + token);
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("AHS_SessionId", sessionId);
//                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);

                    RegisterActivity.showToast(response);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class unrentAds extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
//            String sessionId = urls[1];
//            String token = urls[2];
            String mobile = urls[1];
            int id = Integer.parseInt(urls[2]);

            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
//                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
//                params1.put("RenterMobile", mobile);
                params1.put("RentId", id);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                String tokenType = G.TOKEN_TYPE.getString("TOKEN_TYPE", "");
                connection.setRequestProperty("Authorization", "bearer " + token);
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("AHS_SessionId", sessionId);
//                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);

                    RegisterActivity.showToast(response);

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class verifyClass extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
//            String token = urls[2];
            String customerId = urls[3];
            String phone = urls[4];
            String code = urls[5];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("customerId", customerId);
                params1.put("cellPhone", phone);
                params1.put("activationCode", code);


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                String token = G.TOKEN.getString("TOKEN", "");
//                String tokenType = G.TOKEN_TYPE.getString("TOKEN_TYPE", "");
//                connection.setRequestProperty("Authorization", tokenType + " " + token);
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("AHS_SessionId", sessionId);
//                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

//    public static class registerBuyer extends AsyncTask<String, Void, String> {
//
//        private HttpURLConnection connection;
//
//        @Override
//        protected String doInBackground(String... urls) {
//            String linkType = urls[0];
//            String sessionId = urls[1];
//            String token = urls[2];
//            String customerId = urls[3];
//            String name = urls[4];
//            String city = urls[5];
//            String post_result = null;
//
//            try {
//                URL url = new URL(linkType);
//                post_result = "";
//                connection = (HttpURLConnection) url.openConnection();
//
//                Map<String, Object> params1 = new LinkedHashMap<>();
//                params1.put("OwnerId", G.CONTEXT.getResources().getString(R.string.owner_id));
//                params1.put("CustomerId", customerId);
//                params1.put("Name", name);
//                params1.put("CityId", city);
//
//
//                StringBuilder postData = new StringBuilder();
//                for (Map.Entry<String, Object> param : params1.entrySet()) {
//                    if (postData.length() != 0) postData.append('&');
//                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
//                    postData.append('=');
//                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
//                }
//                String urlParameters = postData.toString();
//                connection.setRequestMethod("POST");
//                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
//                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("AHS_SessionId", sessionId);
//                connection.setRequestProperty("AHS_UserToken", token);
//
//                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
//                bw.write(urlParameters.toString());
//                bw.flush();
//                bw.close();
//                int responseCode = connection.getResponseCode();
//
//                System.out.println("\nSending 'POST' request to URL : " + url);
//                System.out.println("Post parameters : " + urlParameters.toString());
//                System.out.println("Response Code : " + responseCode);
//                AddCustomerActivity.addCustomerResult(responseCode);
//                final StringBuilder output = new StringBuilder("Request URL " + url);
//                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
//                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
//                output.append(System.getProperty("line.separator") + "Type " + "POST");
//                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
//                String line = "";
//                StringBuilder responseOutput = new StringBuilder();
//                System.out.println("output===============" + br);
//                while ((line = br.readLine()) != null) {
//                    responseOutput.append(line);
//                    post_result += line;
//                }
//                br.close();
//
//                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());
//
//
//            } catch (MalformedURLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                //Print Error Body
//                InputStream _is;
//                try {
//                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
//                        _is = connection.getInputStream();
//                    } else {
//                    /* error from server */
//                        _is = connection.getErrorStream();
//                    }
//                    String response = "";
//                    String line1;
//                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
//                    while ((line1 = br1.readLine()) != null) {
//                        response += line1;
//                    }
//                    Log.d("error", "Response: " + response);
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//            return post_result;
//        }
//
//
//    }

    public static class getDashboard extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String customerId = urls[3];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("customerId", customerId);


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class getUpdates extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String versionName = urls[3];
            String versionCode = urls[4];
            String osType = urls[5];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("versionName", versionName);
                params1.put("versionCode", versionCode);
                params1.put("osType", osType);


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class getCustomer extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String phone = urls[3];
            String memberCode = urls[4];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("cellPhone", phone);
                params1.put("activationCode", memberCode);


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class resend extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String customerId = urls[3];
            String cellPhone = urls[4];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("customerId", customerId);
                params1.put("cellPhone", cellPhone);


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class getNearStates extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
//            double lat = Double.parseDouble(urls[1]);
//            double lng = Double.parseDouble(urls[2]);
            String lat = urls[1];
            String lng = urls[2];
            int typeId = Integer.parseInt(urls[3]);
            int sort = 10;
            try {
                sort = Integer.parseInt(urls[4]);

            } catch (Exception e) {
            }
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
//                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("Lat", null);
                params1.put("Lng", null);
                params1.put("EstateTypeID", typeId);
                if (sort != 10) {
                    params1.put("SortField", sort);
                }


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                String tokenType = G.TOKEN_TYPE.getString("TOKEN_TYPE", "");
                connection.setRequestProperty("Authorization", "bearer " + token);
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("AHS_SessionId", sessionId);
//                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class getAdsDetails extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
//            double lat = Double.parseDouble(urls[1]);
//            double lng = Double.parseDouble(urls[2]);
            String adId = urls[1];
//            String lng = urls[2];
//            int typeId = Integer.parseInt(urls[3]);
//            String cellPhone = urls[4];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
//                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("AdsID", adId);
//                params1.put("Lng", lng);
//                params1.put("EstateTypeID", typeId);


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                String tokenType = G.TOKEN_TYPE.getString("TOKEN_TYPE", "");
                connection.setRequestProperty("Authorization", "bearer " + token);
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("AHS_SessionId", sessionId);
//                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class payRequest extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
//            double lat = Double.parseDouble(urls[1]);
//            double lng = Double.parseDouble(urls[2]);
            String Amount = urls[1];
            String Description = urls[2];
            String Mobile = urls[3];
            String Email = urls[4];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
//                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("Amount", Amount);
                params1.put("Description", Description);
                params1.put("Mobile", Mobile);
                params1.put("Email", Email);


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                String tokenType = G.TOKEN_TYPE.getString("TOKEN_TYPE", "");
                connection.setRequestProperty("Authorization", "bearer " + token);
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("AHS_SessionId", sessionId);
//                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class getRentList extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
//            double lat = Double.parseDouble(urls[1]);
//            double lng = Double.parseDouble(urls[2]);
            String cellphone = urls[1];
//            String lng = urls[2];
//            int typeId = Integer.parseInt(urls[3]);
//            String cellPhone = urls[4];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
//                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
//                params1.put("Lat", lat);
//                params1.put("Lng", lng);
//                params1.put("EstateTypeID", typeId);
                params1.put("Mobile", cellphone);


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);
                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                String tokenType = G.TOKEN_TYPE.getString("TOKEN_TYPE", "");
                connection.setRequestProperty("Authorization", "bearer " + token);
//                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
//                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("AHS_SessionId", sessionId);
//                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }


    public static class getMyAds extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String cellphone = urls[1];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("UserMobile", cellphone);


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);


                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                connection.setRequestProperty("Authorization", "bearer " + token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class getNews extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String date = urls[3];
            String time = urls[4];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("lastLocalDate", date);
                params1.put("lastLocalTime", time);
                if (G.IS_REGISTERED.getBoolean("IS_REGISTERED", false)) {
                    String customerId = G.CUSTOMER_ID.getString("CUSTOMER_ID", "x");
                    params1.put("customerId", customerId);
                }


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class getBuyers extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String customerId = urls[3];
            String status = urls[4];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("CustomerId", customerId);
                params1.put("Status", status);


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class getInfo extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class aboutUs extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class getProductGroup extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String latitude = urls[3];
            String longtitude = urls[4];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("locationLat", latitude);
                params1.put("locationLong", longtitude);
                if (G.IS_REGISTERED.getBoolean("IS_REGISTERED", false)) {
                    String customerId = G.CUSTOMER_ID.getString("CUSTOMER_ID", "x");
                    params1.put("customerId", customerId);
                }


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);
//                connection.setDoOutput(true);
//                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
//                dStream.writeBytes(urlParameters.toString());
//                dStream.flush();
//                dStream.close();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class getTargetGroup extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String latitude = urls[3];
            String longtitude = urls[4];
            String year = urls[5];
            String month = urls[6];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("locationLat", latitude);
                params1.put("locationLong", longtitude);
                params1.put("targetYear", year);
                params1.put("targetMonth", month);
                if (G.IS_REGISTERED.getBoolean("IS_REGISTERED", false)) {
                    String customerId = G.CUSTOMER_ID.getString("CUSTOMER_ID", "x");
                    params1.put("customerId", customerId);
                }


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);
//                connection.setDoOutput(true);
//                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
//                dStream.writeBytes(urlParameters.toString());
//                dStream.flush();
//                dStream.close();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class registerNewClass extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String ID = urls[1];
            String Address = urls[2];
            String Name = urls[3];
            String Lat = urls[4];
            String Lng = urls[5];
            String Capacity = urls[6];
            String Location = urls[7];
            String CreateTime = urls[8];
            String ClassSize = urls[9];
            String Email = urls[10];
            String PhoneNumber = urls[11];
            String EstateTypeID = urls[12];
            String UserID = urls[13];
            JSONArray AdsOption = null;
            try {
                AdsOption = new JSONArray(urls[14]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray AdsShift = null;
            try {
                AdsShift = new JSONArray(urls[15]);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ID", ID);
                params1.put("Address", Address);
                params1.put("Name", Name);
                params1.put("Lat", Lat);
                params1.put("Lng", Lng);
                params1.put("Capacity", Capacity);
                params1.put("Location", Location);
                params1.put("CreateTime", CreateTime);
                params1.put("ClassSize", ClassSize);
                params1.put("Email", Email);
                params1.put("PhoneNumber", PhoneNumber);
                params1.put("RenterMobile", PhoneNumber);
                params1.put("EstateTypeID", EstateTypeID);
                params1.put("UserID", UserID);
                params1.put("AdsOption", AdsOption);
                params1.put("AdsShift", AdsShift);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                String tokenType = G.TOKEN_TYPE.getString("TOKEN_TYPE", "");
                connection.setRequestProperty("Authorization","bearer  " + token);
//                connection.setDoOutput(true);
//                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
//                dStream.writeBytes(urlParameters.toString());
//                dStream.flush();
//                dStream.close();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class getCustomerDetail extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String customerID = urls[3];
            String ID = urls[4];

            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("customerId", customerID);
                params1.put("buyerId", ID);
//                params1.put("orderComment", orderComment);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);
//                connection.setDoOutput(true);
//                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
//                dStream.writeBytes(urlParameters.toString());
//                dStream.flush();
//                dStream.close();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class sendCode extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String cellPhone = urls[1];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("Mobile", cellPhone);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                connection.setRequestProperty("AHS_SessionId", sessionId);
//                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }
    public static class getCredit extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String cellPhone = urls[1];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("Mobile", cellPhone);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                String tokenType = G.TOKEN_TYPE.getString("TOKEN_TYPE", "");
                connection.setRequestProperty("Authorization", tokenType + " " + token);
                //                connection.setRequestProperty("AHS_SessionId", sessionId);
//                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }


    public static class userDetail extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String cellPhone = urls[1];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("Mobile", cellPhone);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                String token = G.TOKEN.getString("TOKEN", "");
                connection.setRequestProperty("Authorization", "bearer " + token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class checkCode extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String cellPhone = urls[1];
            String ActivationCode = urls[2];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("Mobile", cellPhone);
                params1.put("ActivationCode", ActivationCode);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                connection.setRequestProperty("AHS_SessionId", sessionId);
//                connection.setRequestProperty("AHS_UserToken", token);

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class token extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String grant_type = urls[1];
            String username = urls[2];
            String password = urls[3];

            String post_result = null;
            try {
                URL url = new URL(linkType);
                post_result = "";

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("grant_type", grant_type);
                params1.put("username", username);
                params1.put("password", password);


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();

                connection = (HttpURLConnection) url.openConnection();

                connection.setDoOutput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("charset", "utf-8");
                connection.setUseCaches(false);

                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write(urlParameters);
                osw.flush();
                osw.close();
                System.err.println(connection.getResponseCode());

                int responseCode = connection.getResponseCode();
                String body = connection.getResponseMessage();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);
                System.out.println("Response message : " + body);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                String response = "";

                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                        /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                return response;
            }
            return post_result;
        }


    }

    public static class sendMessage extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String customerID = urls[3];
            String title = urls[4];
            String message = urls[5];
            String state = urls[6];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("customerId", customerID);
                if (state.equals("title")) {
                    params1.put("title", title);
                } else {
                    params1.put("conversationId", title);
                }
                params1.put("message", message);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);
//                connection.setDoOutput(true);
//                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
//                dStream.writeBytes(urlParameters.toString());
//                dStream.flush();
//                dStream.close();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class getConversation extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String customerID = urls[3];
            String conversationId = urls[4];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("customerId", customerID);
                params1.put("conversationId", conversationId);


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);
//                connection.setDoOutput(true);
//                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
//                dStream.writeBytes(urlParameters.toString());
//                dStream.flush();
//                dStream.close();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class getProjectDetail extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String customerID = urls[3];
            String ProjectId = urls[4];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("customerId", customerID);
                params1.put("ProjectId", ProjectId);


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);
//                connection.setDoOutput(true);
//                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
//                dStream.writeBytes(urlParameters.toString());
//                dStream.flush();
//                dStream.close();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class getConversationList extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String customerID = urls[3];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("customerId", customerID);


                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);
//                connection.setDoOutput(true);
//                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
//                dStream.writeBytes(urlParameters.toString());
//                dStream.flush();
//                dStream.close();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    public static class logOutSession extends AsyncTask<String, Void, String> {

        private HttpURLConnection connection;

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String sessionId = urls[1];
            String token = urls[2];
            String customerID = urls[3];
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
                params1.put("customerId", customerID);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                JSONObject x = new JSONObject(params1);

                String urlParameters = x.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestProperty("AHS_SessionId", sessionId);
                connection.setRequestProperty("AHS_UserToken", token);
//                connection.setDoOutput(true);
//                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
//                dStream.writeBytes(urlParameters.toString());
//                dStream.flush();
//                dStream.close();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Print Error Body
                InputStream _is;
                try {
                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                        _is = connection.getInputStream();
                    } else {
                    /* error from server */
                        _is = connection.getErrorStream();
                    }
                    String response = "";
                    String line1;
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
                    while ((line1 = br1.readLine()) != null) {
                        response += line1;
                    }
                    Log.d("error", "Response: " + response);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return post_result;
        }


    }

    static class Notification extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String linkType = urls[0];
            String firstPart = urls[1];
            String secondPart = urls[2];
//            String secondPart = urls[2];
//            String thirdPart = urls[3];
//            String Url = null;
//            if (firstPart.equals("notif")) {
//                Url = "http://www.20decor.com/index.php?route=apiv1/notification";
//            } else if (firstPart != "notif") {
////                    Url = Splash_Screen.routs.getString(linkType);
//            }
//            String link = linkType + firstPart;


//            Integer thirdConvert = Integer.parseInt(thirdPart);
            String post_result = null;

            try {
                URL url = new URL(linkType);
                post_result = "";
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                params1.put("fcm_token", firstPart);
                params1.put("device_id", secondPart);

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
//                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
//                dStream.writeBytes(urlParameters.toString());
//                dStream.flush();
//                dStream.close();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return post_result;
        }


    }

    static class FilterPostClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String link = urls[0];
            String strLocation = urls[1];
            String strSaleType = urls[2];
            String strHouseType = urls[3];
            String strHouseTools = urls[4];
            String strColdTools = urls[5];
            String strHeatTools = urls[6];
            String strPrice = urls[7];
            String strSize = urls[8];

            String post_result = null;

            try {
                URL url = new URL(link);
                post_result = "";
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                try {
                    if (strLocation.length() > 0) {
                        if (!strLocation.equals("null")) {
                            params1.put("ads[location_area]", strLocation);
                        }
                    }
                } catch (Exception v) {
                }
                try {
                    if (strSaleType.length() > 0) {
                        if (!strSaleType.equals("null")) {
                            params1.put("ads[house_sale_type]", strSaleType);
                        }
                    }
                } catch (Exception v) {
                }
                try {
                    if (strHouseType.length() > 0) {
                        if (!strHouseType.equals("null")) {
                            params1.put("ads[house_type]", strHouseType);
                        }
                    }
                } catch (Exception v) {
                }
                try {
                    if (strHouseTools.length() > 0) {
                        if (!strHouseTools.equals("null")) {
                            params1.put("ads[house_tools]", strHouseTools);
                        }
                    }
                } catch (Exception v) {
                }
                try {
                    if (strColdTools.length() > 0) {
                        if (!strColdTools.equals("null")) {
                            params1.put("filter[][house_temperature_cold]", strColdTools);
                        }
                    }
                } catch (Exception v) {
                }
                try {
                    if (strHeatTools.length() > 0) {
                        if (!strHeatTools.equals("null")) {
                            params1.put("filter[][house_temperature_heat]", strHeatTools);
                        }
                    }
                } catch (Exception v) {
                }
                try {
                    if (strPrice.length() > 0) {
                        params1.put("ads[price]", strPrice);
                    }
                } catch (Exception v) {
                }
                try {
                    if (strSize.length() > 0) {
                        params1.put("ads[house_total_size]", strSize);
                    }
                } catch (Exception v) {
                }
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
//                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
//                dStream.writeBytes(urlParameters.toString());
//                dStream.flush();
//                dStream.close();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return post_result;
        }
    }

    static class TourFilterPostClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String link = urls[0];
            String strSource = urls[1];
            String strTarget = urls[2];
            String strHouseType = urls[3];
            String strHouseTools = urls[4];
            String strColdTools = urls[5];
            String strHeatTools = urls[6];
            String strPrice = urls[7];
            String strSize = urls[8];

            String post_result = null;

            try {
                URL url = new URL(link);
                post_result = "";
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                Map<String, Object> params1 = new LinkedHashMap<>();
                try {
                    params1.put("ads[ads_type]", "3");
                } catch (Exception v) {
                }
                try {
                    if (strSource.length() > 0) {
                        if (!strSource.equals("null")) {
                            params1.put("ads[tour_source_city]", strSource);
                        }
                    }
                } catch (Exception v) {
                }
                try {
                    if (strTarget.length() > 0) {
                        if (!strTarget.equals("null")) {
                            params1.put("ads[tour_target_city]", strTarget);
                        }
                    }
                } catch (Exception v) {
                }
                try {
                    if (strHouseType.length() > 0) {
                        if (!strHouseType.equals("null")) {
                            params1.put("ads[house_type]", strHouseType);
                        }
                    }
                } catch (Exception v) {
                }
                try {
                    if (strHouseTools.length() > 0) {
                        params1.put("ads[house_tools]", strHouseTools);
                    }
                } catch (Exception v) {
                }
                try {
                    if (strColdTools.length() > 0) {
                        params1.put("filter[][house_temperature_cold]", strColdTools);
                    }
                } catch (Exception v) {
                }
                try {
                    if (strHeatTools.length() > 0) {
                        params1.put("filter[][house_temperature_heat]", strHeatTools);
                    }
                } catch (Exception v) {
                }
                try {
                    if (strPrice.length() > 0) {
                        params1.put("ads[price]", strPrice);
                    }
                } catch (Exception v) {
                }
                try {
                    if (strSize.length() > 0) {
                        params1.put("ads[house_total_size]", strSize);
                    }
                } catch (Exception v) {
                }
                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String, Object> param : params1.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                String urlParameters = postData.toString();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
//                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
//                dStream.writeBytes(urlParameters.toString());
//                dStream.flush();
//                dStream.close();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                bw.write(urlParameters.toString());
                bw.flush();
                bw.close();
                int responseCode = connection.getResponseCode();

                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + urlParameters.toString());
                System.out.println("Response Code : " + responseCode);

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters.toString());
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                    post_result += line;
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return post_result;
        }


    }


//    public static class getToken extends AsyncTask<String, Void, String> {
//
//        private HttpURLConnection connection;
//
//        @Override
//        protected String doInBackground(String... urls) {
//            String linkType = urls[0];
////            String sessionId = urls[1];
////            String token = urls[2];
////            String customerID = urls[3];
//            String post_result = null;
//
//            try {
//                URL url = new URL(linkType);
//                post_result = "";
//                connection = (HttpURLConnection) url.openConnection();
//
//                Map<String, Object> params1 = new LinkedHashMap<>();
////                params1.put("ownerId", G.CONTEXT.getResources().getString(R.string.owner_id));
////                params1.put("customerId", customerID);
//
//
//                StringBuilder postData = new StringBuilder();
//                for (Map.Entry<String, Object> param : params1.entrySet()) {
//                    if (postData.length() != 0) postData.append('&');
//                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
//                    postData.append('=');
//                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
//                }
//                JSONObject x = new JSONObject(params1);
//
//                String urlParameters = x.toString();
//                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//                connection.setRequestProperty("AppVersion", "1.0.0");
//
//                String text = "rs_movafaghyat@yahoo.com | Movafaghyat@3";
//                byte[] data = text.getBytes(StandardCharsets.UTF_8);
//                String userName = Base64.encodeToString(data, Base64.DEFAULT);
//
//
//                connection.setRequestProperty("Authorization", "Basic " + userName);
////                connection.setDoOutput(true);
////                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
////                dStream.writeBytes(urlParameters.toString());
////                dStream.flush();
////                dStream.close();
//                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
//                bw.write(urlParameters.toString());
//                bw.flush();
//                bw.close();
//                int responseCode = connection.getResponseCode();
//
//                System.out.println("\nSending 'POST' request to URL : " + url);
//                System.out.println("Post parameters : " + urlParameters.toString());
//                System.out.println("Response Code : " + responseCode);
//
//                final StringBuilder output = new StringBuilder("Request URL " + url);
//                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
//                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
//                output.append(System.getProperty("line.separator") + "Type " + "POST");
//                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
//                String line = "";
//                StringBuilder responseOutput = new StringBuilder();
//                System.out.println("output===============" + br);
//                while ((line = br.readLine()) != null) {
//                    responseOutput.append(line);
//                    post_result += line;
//                }
//                br.close();
//
//                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());
//
//
//            } catch (MalformedURLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                //Print Error Body
//                InputStream _is;
//                try {
//                    if (connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
//                        _is = connection.getInputStream();
//                    } else {
//                    /* error from server */
//                        _is = connection.getErrorStream();
//                    }
//                    String response = "";
//                    String line1;
//                    BufferedReader br1 = new BufferedReader(new InputStreamReader(_is));
//                    while ((line1 = br1.readLine()) != null) {
//                        response += line1;
//                    }
//                    Log.d("error", "Response: " + response);
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//            return post_result;
//        }
//
//
//    }

}
