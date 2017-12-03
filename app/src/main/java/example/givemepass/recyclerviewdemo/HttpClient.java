package example.givemepass.recyclerviewdemo;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Duan on 2017/12/3.
 */

public class HttpClient {

    private static final String TAG = "HttpClient";

    private static final String HTTP_POST = "POST";
    private static final String HTTP_GET = "GET";
    private static final String HTTP_PUT = "PUT";

    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final Integer HTTP_TIMEOUT = 3000;

    private int mResponseCode;

    /**
     * Send a post request with authorization.
     *
     * @param targetUrl server url
     * @param body http body
     * @param authorization authorization string
     * @return response if request success; otherwise, null
     */
    public String post(String targetUrl, String body, String authorization) {
        URL url;
        HttpURLConnection connection = null;

        Log.d(TAG, "post " + targetUrl + " with body: " + body);

        try {
            url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HTTP_POST);
            connection.setRequestProperty("Content-Type", CONTENT_TYPE_JSON);
            connection.setRequestProperty("Authorization", authorization);
            connection.setDoInput(true);
            connection.setConnectTimeout(HTTP_TIMEOUT);
            connection.setReadTimeout(HTTP_TIMEOUT);

            if (body != null) {
                connection.setDoOutput(true);
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                out.write(body.getBytes("UTF-8"));
                out.flush();
                out.close();
            }

            connection.connect();

            mResponseCode = connection.getResponseCode();
            Log.d(TAG, "response code = " + mResponseCode);
            if (mResponseCode == 200) {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                int len;
                byte[] bytes = new byte[4096];
                len = in.read(bytes);
                if (len > 0) {
                    return new String(bytes).substring(0, len);
                } else {
                    return "";
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "post " + body + " to " + targetUrl + " failed", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    public String put(String targetUrl, String body, String authorization) {
        URL url;
        HttpURLConnection connection = null;

        Log.d(TAG, "put " + targetUrl + " with body: " + body);

        try {
            url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HTTP_PUT);
            connection.setRequestProperty("Content-Type", CONTENT_TYPE_JSON);
            connection.setRequestProperty("Authorization", authorization);
            connection.setDoInput(true);
            connection.setConnectTimeout(HTTP_TIMEOUT);
            connection.setReadTimeout(HTTP_TIMEOUT);

            if (body != null) {
                connection.setDoOutput(true);
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                out.write(body.getBytes("UTF-8"));
                out.flush();
                out.close();
            }

            connection.connect();

            mResponseCode = connection.getResponseCode();
            Log.d(TAG, "response code = " + mResponseCode);
            if (mResponseCode == 200) {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                int len;
                byte[] bytes = new byte[4096];
                len = in.read(bytes);
                return new String(bytes).substring(0, len);
            }
        } catch (IOException e) {
            Log.e(TAG, "post " + body + " to " + targetUrl + " failed", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    /**
     * Send a post request without authorization.
     *
     * @param targetUrl server url
     * @param body http body
     * @return response if request success; otherwise, null
     */
    public String post(String targetUrl, String body) {
        URL url;
        HttpURLConnection connection = null;

        Log.e(TAG, "post to " + targetUrl + " with " + body);

        try {
            url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HTTP_POST);
            connection.setRequestProperty("Content-Type", CONTENT_TYPE_JSON);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(HTTP_TIMEOUT);
            connection.setReadTimeout(HTTP_TIMEOUT);

            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            out.write(body.getBytes("UTF-8"));
            out.flush();
            out.close();

            connection.connect();

            mResponseCode = connection.getResponseCode();
            Log.d(TAG, "response code = " + mResponseCode);
            if (mResponseCode == 200) {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                int len;
                byte[] bytes = new byte[4096];
                len = in.read(bytes);
                return new String(bytes).substring(0, len);
            }
        } catch (IOException e) {
            Log.e(TAG, "post " + body + " to " + targetUrl + " failed", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    /**
     * Send a get request with authorization.
     *
     * @param targetUrl server url
     * @param authorization authorization string
     * @return response if request success; otherwise, null
     */
    public String get(String targetUrl, String authorization) {
        URL url;
        HttpURLConnection connection = null;

        Log.e(TAG, "url: " + targetUrl);

        try {
            url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HTTP_GET);
            connection.setRequestProperty("Content-Type", CONTENT_TYPE_JSON);
            connection.setRequestProperty("Authorization", authorization);
            connection.setDoInput(true);
            connection.setConnectTimeout(HTTP_TIMEOUT);
            connection.setReadTimeout(HTTP_TIMEOUT);

            connection.connect();

            mResponseCode = connection.getResponseCode();
            Log.d(TAG, "response code = " + mResponseCode);
            if (mResponseCode == 200) {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                int len;
                byte[] bytes = new byte[4096];
                len = in.read(bytes);
                return new String(bytes).substring(0, len);
            }
        } catch (IOException e) {
            Log.e(TAG, "get from : " + targetUrl + " failed", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    public String get(String targetUrl) {
        URL url;
        HttpURLConnection connection = null;

        try {
            url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HTTP_GET);
            connection.setRequestProperty("Content-Type", CONTENT_TYPE_JSON);
            connection.setDoInput(true);
            connection.setConnectTimeout(HTTP_TIMEOUT);
            connection.setReadTimeout(HTTP_TIMEOUT);

            connection.connect();

            mResponseCode = connection.getResponseCode();
            Log.d(TAG, "response code = " + mResponseCode);
            if (mResponseCode == 200) {
                InputStream in = new BufferedInputStream(connection.getInputStream());
                int len;
                byte[] bytes = new byte[4096];  // FIXME maybe 4096 bytes is not big enough.
                len = in.read(bytes);
                return new String(bytes).substring(0, len);
            }
        } catch (IOException e) {
            Log.e(TAG, "get from : " + targetUrl + " failed", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return null;
    }

    /**
     * @return response code of the request
     */
    public int getResponseCode() {
        return mResponseCode;
    }

}
