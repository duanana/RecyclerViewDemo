package example.givemepass.recyclerviewdemo;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import org.apache.http.client.HttpClient;

import example.givemepass.recyclerviewdemo.HttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;
    static String uri = "http://192.168.0.102:8000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_version = (Button) findViewById(R.id.button_version);

        ArrayList<String> myDataset = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            myDataset.add(Integer.toString(i));
        }
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        btn_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getversion();
//                getnewversion();
                getnewversionname();
            }
        });
        Button page2btn = (Button) findViewById(R.id.button_page2);
        page2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private class httpget extends AsyncTask<String, Void, Void> {
        StringBuilder sb = new StringBuilder();
        @Override
        protected Void doInBackground(String... parmet) {
            URL url;
            HttpURLConnection connection = null;

            try {
                url = new URL(parmet[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
//                connection.setRequestProperty("Content-Type", CONTENT_TYPE_JSON);
                connection.setDoInput(true);
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);

                connection.connect();

                int mResponseCode = connection.getResponseCode();
                Log.d("ggggggggg", "response code = " + mResponseCode);
                if (mResponseCode == 200) {
                    InputStream in = new BufferedInputStream(connection.getInputStream());
                    int len;
                    byte[] bytes = new byte[4096];  // FIXME maybe 4096 bytes is not big enough.
                    len = in.read(bytes);
//                    return new String(bytes).substring(0, len);
                }
            } catch (IOException e) {
                Log.e("ggggg", "get from : " + parmet[0] + " failed", e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void bitmap){
            super.onPostExecute(bitmap);
            Log.e("ffffffff", sb.toString());
        }
    }

    public void getnewversionname() {
        String uriin = uri;
        DownloadManager.Request downloadManager = new DownloadManager.Request(Uri.parse(uriin));
        Toast.makeText(this, "Down Successful", Toast.LENGTH_SHORT).show();
        downloadManager.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏标题
        downloadManager.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        downloadManager.setTitle("下载");
        downloadManager.setDescription("apk正在下载");
        downloadManager.setAllowedOverRoaming(false);
        //设置文件存放目录
        downloadManager.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "mydown");

        startInstall(this, Uri.parse(uriin));
    }

    public void getnewversion() {
        String uriin = uri + "/app-debug.apk";
        DownloadManager.Request downloadManager = new DownloadManager.Request(Uri.parse(uriin));
        Toast.makeText(this, "Down Successful", Toast.LENGTH_SHORT).show();
        downloadManager.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏标题
        downloadManager.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        downloadManager.setTitle("下载");
        downloadManager.setDescription("apk正在下载");
        downloadManager.setAllowedOverRoaming(false);
        //设置文件存放目录
        downloadManager.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "mydown");

        startInstall(this, Uri.parse(uriin));
    }

    public static void startInstall(Context context, Uri uri) {
        Intent install = new Intent(Intent.ACTION_VIEW, uri);
//        install.setDataAndType(uri, "application/vnd.android.package-archive");
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
    }

    public void getversion() {
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            Toast.makeText(this, versionName, Toast.LENGTH_SHORT).show();
            DownloadManager manager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void downloadApk() {
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> mData;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;

            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.info_text);
            }
        }

        public MyAdapter(List<String> data) {
            mData = data;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.mTextView.setText(mData.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Item " + position + " is clicked.", Toast.LENGTH_SHORT).show();
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(MainActivity.this, "Item " + position + " is long clicked.", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
