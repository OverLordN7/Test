package space.oln7.testproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button click;
    public static TextView data;
    public static ImageView image;
    public static ImageView image1;


    String urls="http://komotoz.ru/photo/zhivotnye/images/ryabchik/ryabchik_05.jpg";
    ArrayList<String> arrayURLS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        click = (Button) findViewById(R.id.button);
        data = (TextView) findViewById(R.id.data);
        image = (ImageView) findViewById(R.id.image);
        image1 = (ImageView) findViewById(R.id.image1);


        arrayURLS.add(urls);





        click.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                fetchData process = new fetchData();
                process.execute();
                new GetImage(image,arrayURLS.get(0)).execute();

            }
        });
    }





    public class GetImage extends AsyncTask<String,Void, Bitmap>  {

        ImageView imgView;
        Bitmap bitmap;
        String parse;

        public GetImage(ImageView imgV, String tmp){
            this.imgView=imgV;
            this.parse = tmp;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            bitmap = null;

            try {
                InputStream str = new java.net.URL(parse).openStream();
                bitmap= BitmapFactory.decodeStream(str);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            imgView.setImageBitmap(bitmap);
        }
    }
}