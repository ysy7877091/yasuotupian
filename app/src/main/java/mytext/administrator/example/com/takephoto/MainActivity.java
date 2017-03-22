package mytext.administrator.example.com.takephoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button choose_image;
    private ImageView choose_bit;
    private String sdPath;
    private String picPath;
    private int REQUEST_ORIGINAL = 0;
    /**
     * SD卡根目录
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //压缩后保存临时文件目录
        sdPath = Environment.getExternalStorageDirectory().getPath();
        picPath = sdPath + "/" + System.currentTimeMillis() + "temp.png";
		/*File tempFile = new File(externalStorageDirectory);
		if(!tempFile.exists()){
			tempFile.mkdirs();
		}*/
        choose_image = (Button) findViewById(R.id.choose_image);
        choose_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ){//如果sd卡可用
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri uri = Uri.fromFile(new File(picPath));        //为拍摄的图片指定一个存储的路径
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent2, REQUEST_ORIGINAL);
                }else{
                    Toast.makeText(getApplicationContext(),"sd卡不可用",Toast.LENGTH_SHORT).show();
                }

            }
        });

        choose_bit = (ImageView) findViewById(R.id.choose_bit);
    }

    //拍照选图片成功回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ORIGINAL){
            FileInputStream fis = null;
            try {  Log.e("sdPath2",picPath);
                //把图片转化为字节流
               fis = new FileInputStream(picPath);
                //把流转化图片
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                //文件大小 picPath目标图片地址
                File file = new File(picPath);
                Log.e("warn",file.length()/1024+"kb");


                choose_bit.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
