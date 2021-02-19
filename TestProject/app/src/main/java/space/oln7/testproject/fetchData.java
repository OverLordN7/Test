package space.oln7.testproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class fetchData extends AsyncTask<Void,Void,Void> {

    /*Переменные для работы с текстовыми данными*/
    String data ="";
    String dataParsed = "";
    String singleParse = "";


    /*Переменные для работы с URL фотографий*/
    public static ArrayList<String> arrayURL = new ArrayList<>(); //сбор url с анкет
    public static ArrayList<Bitmap> arrayBitmap = new ArrayList<>(); // конвертированные bitmap с url


    public Bitmap getBitmapFromURL(String src)  {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //connection.setDoInput(true);
            //connection.connect();
            InputStream input = connection.getInputStream();

            Log.i("InputStream",""+ input.available());

            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            input.close();
            connection.disconnect();
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("GetImage","Something went wrong with image url");
            return null;
        }
    }







    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://dating.mts.by/android/search/ByLiked?page=1");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            //Проверка есть ли пустные строчки в буфере
            while(line != null){
                line = bufferedReader.readLine();

                if(line != null){
                    data = data + line;}

            }

            JSONObject JO = new JSONObject(data);
            JSONArray JA =  JO.getJSONArray("users");


            for(int i=0;i<JA.length();i++){

                /*Достаем анкеты из JSON*/
                singleParse =  "Name: " + JA.getJSONObject(i).getString("name")+"\n"+
                        "Age: "+JA.getJSONObject(i).getString("age")+"\n"+
                        "City: "+JA.getJSONObject(i).getString("city")+"\n"+
                        "Last visit: "+JA.getJSONObject(i).getString("lastVisit")+"\n";
                        if(JA.getJSONObject(i).getInt("sex")==1){
                            singleParse = singleParse + "Sex: Male\n\n";
                        }
                        else{
                            singleParse = singleParse +"Sex: Female\n\n";
                        }
                dataParsed = dataParsed + singleParse;
                /*######################*/

                /*Достаем URL фотографий из JSON*/
                arrayURL.add("http://dating.mts.by" + JA.getJSONObject(i).getString("iurl_200"));

                Log.i("arrayURL", arrayURL.get(i));


                /*######################*/



                /*
               singleParse =  "Name: " + JA.getJSONObject(i).getString("name") + "\n"+
                                            JA.getJSONObject(i).getString("iurl_200")+"\n";
                                            */




            }




            //Передаем arrayURL в функцию getBitmapFromURL для конвертации в Bitmap

            /*
            for(int i=0;i<arrayURL.size();i++) {

                arrayBitmap.add(getBitmapFromURL(arrayURL.get(i).toString()));


                if (arrayBitmap.get(i) == null) {
                    Log.i("arrayBitmap", "пусто");
                } else {
                    Log.i("arrayBitmap", arrayBitmap.get(i).toString());
                }
            }
            */

            /*
            String test = "https://cherepah.ru/wp-content/uploads/1/9/a/19af89f11e207ecf70387e8f67304892.jpeg";
            arrayBitmap.add(getBitmapFromURL(test));
            */







        }catch (MalformedURLException e){
            e.printStackTrace();
        }


        catch (IOException e) {
            e.printStackTrace();
        }
        catch(JSONException e){
            e.printStackTrace();
        }

        return null;


    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        MainActivity.data.setText(this.dataParsed);
            //MainActivity.image.setImageBitmap(this.arrayBitmap.get(0));
            //MainActivity.image1.setImageBitmap(this.arrayBitmap.get(1));




    }


}
